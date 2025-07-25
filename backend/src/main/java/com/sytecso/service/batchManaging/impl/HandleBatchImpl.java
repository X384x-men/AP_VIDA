package com.sytecso.service.batchManaging.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.dao.cargasBatch.DAOCargasBatch;
import com.sytecso.dao.usuario.DAOUsuarioAcceso;
import com.sytecso.dto.batchmodel.DTOAsegurado;
import com.sytecso.dto.batchmodel.DTOBatchTransform;
import com.sytecso.dto.batchmodel.DTOCargaBatchControl;
import com.sytecso.dto.batchmodel.DTOCargo;
import com.sytecso.dto.batchmodel.DTOCriterios;
import com.sytecso.dto.batchmodel.DTODetalle;
import com.sytecso.dto.batchmodel.DTOReproceso;
import com.sytecso.dto.batchmodel.DTOResumen;
import com.sytecso.dto.catalogosAP.DTOCatalogoConceptos;
import com.sytecso.dto.catalogosAP.DTOCatalogoDependencias;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.service.batchManaging.HandleBatch;
import com.sytecso.service.usuario.ServiceUsuarioAcceso;
import com.sytecso.dto.usuario.UserAp;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

@Service
public class HandleBatchImpl implements HandleBatch {
	
	@Autowired
	private DAOCargasBatch daoCargasBatch;
	
	@Autowired
	private DAOUsuarioAcceso daoUsuarioAcceso;
	
	@Autowired
	private ServiceUsuarioAcceso serviceUsuarioAcceso;
	
	@Autowired
    private DataSource dataSource;

	@Override
	public DTOCargaBatchControl administraCargas(DTOCargaBatchControl batchControl) throws SQLException {
		boolean processStatus=true;
		if(batchControl.isProcessStatus()) {
			try {
				if(batchControl.getBatchInfo().isTipo()) {
					System.out.println("Comienza el conteo de cifras resumen");
					batchControl.setBatchInfo(getValidosRechazadosResumen(batchControl.getBatchInfo()));
					if(batchControl.getBatchInfo().isStatus()) {
						batchControl.setTipo("Resumen");
						batchControl.setTotalRegistros(batchControl.getBatchInfo().getBatchResumen().size()+batchControl.getBatchInfo().getBatchResumenSinRFC().size());
						batchControl.setRegristrosValidos(batchControl.getBatchInfo().getBatchResumen().size());
						batchControl.setRegistrosRechazados(batchControl.getBatchInfo().getBatchResumenSinRFC().size());
					}
				}else {
					System.out.println("Comienza el conteo de cifras Detalle");
					batchControl.setBatchInfo(getValidosRechazadosDetalle(batchControl.getBatchInfo()));
					if(batchControl.getBatchInfo().isStatus()) {
						batchControl.setTipo("Detalle");
						batchControl.setTotalRegistros(batchControl.getBatchInfo().getBatchDetalle().size()+batchControl.getBatchInfo().getBatchDetalleSinRFC().size());
						batchControl.setRegristrosValidos(batchControl.getBatchInfo().getBatchDetalle().size());
						batchControl.setRegistrosRechazados(batchControl.getBatchInfo().getBatchDetalleSinRFC().size());
					}
				}
				if(batchControl.getBatchInfo().isStatus()) {
					LocalDateTime now = LocalDateTime.now(); 
					batchControl.setFechaCarga(now.toString());
					System.out.println("Se inicia la inserción en controlBatch");
					batchControl.setId(daoCargasBatch.insertControlBatch(batchControl));
					if(batchControl.getId()!=-1L) {
						if(batchControl.getBatchInfo().isTipo()) {
							System.out.println("Comienza la inserción en  tablas de resumen");
							daoCargasBatch.insertResumen(batchControl.getBatchInfo().getBatchResumen(), batchControl.getId());
							System.out.println("termina la inserción en  tablas de resumen");
						}else {
							System.out.println("Comienza la inserción en  tablas de detalle");
							daoCargasBatch.insertDetalle(batchControl.getBatchInfo().getBatchDetalle(), batchControl.getId());
							System.out.println("termina la inserción en  tablas de detalle");
						}
					}
				}else {
					processStatus=false;
					batchControl.setMensaje("Error al separar los registros");
				}
			}catch(Exception e) {
				processStatus=false;
				batchControl.setMensaje("Error al procesar la solicitud");
				System.out.println(e);
			}
			batchControl.setProcessStatus(processStatus);
		}
		return batchControl;
	}

	@Override
	public DTOCargaBatchControl convertMultipartToFile(MultipartFile mpFile) throws IOException, SQLException {
		DTOCargaBatchControl control = new DTOCargaBatchControl();
		control.setNombreArchivo(mpFile.getOriginalFilename());
		String fileName= control.getNombreArchivo();
		System.out.println("Convirtiendo archivo multipart");
		if(daoCargasBatch.validateFile(fileName)==0){
			control.setProcessStatus(true);
			DTOBatchTransform batch  = new DTOBatchTransform();
			DTOReproceso reproceso=validaCorrectionFile(fileName);
			if(!reproceso.isStatus()&&reproceso.isError()) {
				System.out.println(reproceso.getErrorMsg());
				control.setProcessStatus(false);
				control.setMensaje(reproceso.getErrorMsg());
			}else {
				if(!reproceso.isError()&&reproceso.isStatus())
					fileName=reproceso.getFileName();
				if(fileName.startsWith("resumen_de_movimientos")) {
					batch=getResumenObject(mpFile);
					batch.setTipo(true);
				}	
				else if (fileName.startsWith("detalle_de_movimientos")) {
						batch=getDetalleObject(mpFile);
						batch.setTipo(false);
					} else {
						control.setProcessStatus(false);
						control.setMensaje("El nombre del archivo no coincide con los prefijos de resumen o detalle ");
					}
				control.setBatchInfo(batch);
				System.out.println("Terminó de convertir a multipart exitosamente");
			}
		}else {
			control.setProcessStatus(false);
			control.setMensaje("El archivo ya ha sido procesado con anterioridad");
		}
		
		return control;
	}
	
	private DTOReproceso validaCorrectionFile(String fileName) throws SQLException {
		DTOReproceso reproceso= new DTOReproceso();
		if(fileName.startsWith("correccion")) {
			if(fileName.startsWith("correccionM")) {
				reproceso.setFileName(getFileName(fileName.split("_"),2));
				reproceso.setIdFile(daoCargasBatch.getIdFromFileName(reproceso.getFileName()));
				processFileUpdateM(reproceso);
			} else if (fileName.startsWith("correccionI")) {
				String[] tokens=fileName.split("_");
				String rfc=tokens[1];
				reproceso.setFileName(getFileName(tokens,3));
				//buscar el id de la  carga por nombre del archivo
				reproceso.setIdFile(daoCargasBatch.getIdFromFileName(reproceso.getFileName()));
				processFileUpdateI(reproceso,rfc);
			}else {
				reproceso.setStatus(false);
				reproceso.setError(true);
				reproceso.setErrorMsg("Tipo de archivo para correcciones no especificado");
			}
				
		}else {
			reproceso.setError(false);
			reproceso.setStatus(false);
		}
		return reproceso;
	}
	private DTOReproceso processFileUpdateM( DTOReproceso reproceso) {
		if(reproceso.getIdFile().isEmpty()) {
			reproceso.setError(true);
			reproceso.setErrorMsg("No se encontró ocurrencia previal del archivo: "+reproceso.getFileName());
		} else {
			ListIterator<Long> fileIterator = reproceso.getIdFile().listIterator();
			while(fileIterator.hasNext()){
				long idFile= fileIterator.next();
				if(reproceso.getFileName().contains("resumen_de_movimientos"))
					daoCargasBatch.updateMassive(idFile, "estadoCuentaResumen");
				else
					daoCargasBatch.updateMassive(idFile, "estadoCuentaDetalle");
			}
			reproceso.setStatus(true);
			reproceso.setError(false);
		}
		return reproceso;
	}
	private DTOReproceso processFileUpdateI( DTOReproceso reproceso,  String rfc) throws SQLException {
		if(reproceso.getIdFile().isEmpty()) {
			reproceso.setError(true);
			reproceso.setErrorMsg("No se encontró ocurrencia previa del archivo: "+reproceso.getFileName());
		} else {
			Connection con=null;
			Statement sentencia=null;
			long idAsegurado=-1L;
			try {
				con = dataSource.getConnection();
				sentencia = con.createStatement();
				idAsegurado=validateRFC(rfc, sentencia);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				sentencia.close();
				con.close();
			}
			if(idAsegurado!=-1L) {
				ListIterator<Long> fileIterator = reproceso.getIdFile().listIterator();
				while(fileIterator.hasNext()) {
					long idFile= fileIterator.next();
					if(reproceso.getFileName().contains("resumen_de_movimientos"))
						daoCargasBatch.updateSingleClient(idAsegurado, idFile, "estadoCuentaResumen");
					else
						daoCargasBatch.updateSingleClient(idAsegurado,idFile, "estadoCuentaDetalle");
				}
				reproceso.setStatus(true);
				reproceso.setError(false);
			
			}else {
				reproceso.setError(true);
				reproceso.setErrorMsg("No se encontró ocurrencia del asegurado: "+reproceso.getFileName());
			}
		}
		return reproceso;
		
	}
	private String getFileName(String[] tokens, int initialIndex) {
		String fileName="";
		for(int j=initialIndex;j<tokens.length;j++) {
			fileName+=tokens[j]+"_";
			
		}
		fileName=fileName.substring(0, fileName.length()-1);
		return fileName;
	}
	
	private DTOBatchTransform getResumenObject(MultipartFile mpFile) throws IOException {
		DTOBatchTransform  resumenStructure = new DTOBatchTransform();
		Set<String> rfcSet = new HashSet<String>();
		List<DTOResumen> resumenLista = new ArrayList<DTOResumen>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(mpFile.getInputStream()));
		while(reader.ready()) {
			DTOResumen lineaResumen = procesaResumenLinea(reader.readLine());
			rfcSet.add(lineaResumen.getCriterios().getAsegurado().getRfc().toUpperCase());
		    resumenLista.add(lineaResumen);
		}
		resumenStructure.setBatchResumen(resumenLista);
		System.out.println("estos son mis RFC "+resumenLista.size());
		resumenStructure.setRfcLista(rfcSet);
		return resumenStructure;
	}

	private DTOBatchTransform getDetalleObject(MultipartFile mpFile) throws IOException {
		DTOBatchTransform detalleStructure = new DTOBatchTransform();
		BufferedReader reader = new BufferedReader(new InputStreamReader(mpFile.getInputStream()));
		List<DTODetalle>  detalleLista = new ArrayList<DTODetalle>();
		Set<String> rfcSet = new HashSet<String>();
		while(reader.ready()) {
			DTODetalle lineaDetalle =procesaDetalleLinea(reader.readLine());
			rfcSet.add(lineaDetalle.getCriterio().getAsegurado().getRfc().toUpperCase());
			detalleLista.add(lineaDetalle);
		}
		detalleStructure.setBatchDetalle(detalleLista);
		detalleStructure.setRfcLista(rfcSet);
		System.out.println("estos son mis RFC "+detalleStructure.getRfcLista().size());
		return detalleStructure;
	}
	
	private DTODetalle procesaDetalleLinea(String linea) {
		DTODetalle detalle = new DTODetalle();
		String[] tokens=linea.split("\\|",-1);
        detalle.setCriterio(new DTOCriterios());
        detalle.getCriterio().setAsegurado(new DTOAsegurado());
        detalle.getCriterio().getAsegurado().setRfc(tokens[0]);
        detalle.getCriterio().setAnio(tokens[1]);
        detalle.getCriterio().setMes(tokens[2]);
        detalle.getCriterio().setFecha(tokens[3]);
        detalle.getCriterio().setConcepto(new DTOCatalogoConceptos());
        detalle.getCriterio().getConcepto().setIdCatalogoConceptos(Long.parseLong(tokens[4]));
        detalle.setDeposito(Float.parseFloat(tokens[5]));
        detalle.setIntereses(Float.parseFloat(tokens[6]));
        detalle.setRetiros(Float.parseFloat(tokens[7]));
        detalle.setSaldo(Float.parseFloat(tokens[8]));
        String homonimia=tokens[9];
        if(homonimia!=null)
        	detalle.getCriterio().setHomoninimia(homonimia);
        else
        	detalle.getCriterio().setHomoninimia("");
        String nombre=tokens[10];
        if(nombre!=null)
        	detalle.getCriterio().setNombre(nombre);
        else
        	detalle.getCriterio().setNombre("");
		return detalle;
	}
	
	private DTOResumen procesaResumenLinea(String linea) {
		DTOResumen resumen = new DTOResumen();
		String[] tokens=linea.split("\\|",-1);
		resumen.setCriterios(new DTOCriterios());
        resumen.getCriterios().setAsegurado(new DTOAsegurado());
        resumen.getCriterios().getAsegurado().setRfc(tokens[0]);
        resumen.getCriterios().setConcepto(new DTOCatalogoConceptos());
        resumen.getCriterios().getConcepto().setIdCatalogoConceptos(Long.parseLong(tokens[1]));
    	resumen.setSaldoInicial(Float.parseFloat(tokens[2]));
    	resumen.setPrimasAportadas(Float.parseFloat(tokens[3]));
    	resumen.setInteresesGanados(Float.parseFloat(tokens[4]));
    	resumen.setRetiros(Float.parseFloat(tokens[5]));
    	resumen.setSaldoFinal(Float.parseFloat(tokens[6]));
    	resumen.getCriterios().setAnio(tokens[7]);
    	resumen.getCriterios().setMes(tokens[8]);
    	resumen.getCriterios().setCatalogoDependencias(new DTOCatalogoDependencias());
    	resumen.getCriterios().getCatalogoDependencias().setIdCatalogo(Long.parseLong(tokens[9]));
    	String homonimia=tokens[10];
    	if(homonimia!=null)
    		resumen.getCriterios().setHomoninimia(homonimia);
    	else
    		resumen.getCriterios().setHomoninimia("");
    	String nombre=tokens[11];
    	if(nombre!=null)
    		resumen.getCriterios().setNombre(nombre);
    	else
    		resumen.getCriterios().setNombre("");
		return resumen;
	}
	
	private long validateRFC(String rfc,Statement st) throws SQLException {
		return daoCargasBatch.getIdAsegurado(rfc,st);
	}

	@Override
	public DTOCargaBatchControl getBatchResumenCarga(long idCarga) {
		return daoCargasBatch.getResumenInsert(idCarga);
	}
	
	private DTOBatchTransform validateRFC(DTOBatchTransform archivo) throws SQLException {
		int contadorLimite=0;
		Iterator<String> rfcTotal= archivo.getRfcLista().iterator();
		Set<String> conjuntoHashSet= new HashSet<String>(archivo.getRfcLista());
		Set<String> Filtrados= new HashSet<String>();
		List<String> cadenas = new ArrayList<String>();
		String cadena="";
		
		while(rfcTotal.hasNext()) {
			
			if(contadorLimite==0) {
				cadena="(";
			}
			
			if(contadorLimite>900) {
				if(cadena.charAt(cadena.length()-1)==',') {
					cadena= cadena.substring(0, cadena.length() - 1);
				}
				cadena=cadena+") ";
				contadorLimite=0;
				cadenas.add(cadena);
				cadena="";
			}else {
				cadena=cadena+"'"+rfcTotal.next()+"',";
				contadorLimite++;
			}
			
		}
		if(cadena.charAt(cadena.length()-1)==',') {
			cadena= cadena.substring(0, cadena.length() - 1);
		}
		cadena=cadena+") ";
		cadenas.add(cadena);
		
		archivo.setAsegurados(validateRFCBatch(cadenas));
		
		Iterator<DTOAsegurado> it = archivo.getAsegurados().iterator();
		while(it.hasNext()) {
			DTOAsegurado asegurado=it.next();
			Filtrados.add(asegurado.getRfc());
		}
		System.out.println("Antes de filtrar"+conjuntoHashSet.toString());
		System.out.println("RFC totales"+conjuntoHashSet.size());
		conjuntoHashSet.removeAll(Filtrados);
		System.out.println("Filtrados "+Filtrados.toString());
		System.out.println("RFC Filtrados"+Filtrados.size());
		System.out.println("Estos son los que no pertenecen"+conjuntoHashSet.toString());
		System.out.println("RFC que no pertenecen"+conjuntoHashSet.size());
		return archivo;
		
	}
	
	private Set<DTOAsegurado> validateRFCBatch(List<String> rfcList) throws SQLException {
		return daoCargasBatch.getRFCValidos(rfcList);
	}

	
	private DTOBatchTransform getValidosRechazadosResumen(DTOBatchTransform resumenes) throws SQLException {
		System.out.println("Comienza la separación de validos e invalidos resumen");
		
		try {	
			Map<String, Long> mapaObjetos = new HashMap<>();
			Set<DTOAsegurado> rfcValidos = validateRFC(resumenes).getAsegurados();

			 List<DTOResumen> resumenesError= new ArrayList<DTOResumen>();
			 resumenesError.addAll(resumenes.getBatchResumen()); 
			 System.out.println("Registros Totales totales "+resumenes.getBatchResumen().size());
			 resumenes.setBatchResumenSinRFC(resumenesError);
			 resumenes.getBatchResumenSinRFC().removeIf(objeto -> {
		            for (DTOAsegurado objetoConValor : rfcValidos) {
		                if (objetoConValor.getRfc().toUpperCase().equals(objeto.getCriterios().getAsegurado().getRfc().toUpperCase())) {
		                    return true;
		                }
		            }
		            return false;
		        });
			 System.out.println( "Registros inválidos "+resumenes.getBatchResumenSinRFC().size());
			 resumenes.getBatchResumen().removeIf(objeto -> {
		            for (DTOAsegurado objetoConValor : rfcValidos) {
		                if (objetoConValor.getRfc().toUpperCase().equals(objeto.getCriterios().getAsegurado().getRfc().toUpperCase())) {
		                    return false;
		                }
		            }
		            return true;
		        });
			 System.out.println( "Registros válidos "+resumenes.getBatchResumen().size());
			 
			 System.out.println("Comienza la validación vs el documento");
			 System.out.println(resumenes.getBatchResumen().size());
			 System.out.println("Acabo de separar los válidos");
			  
	          for (DTOAsegurado objeto : rfcValidos) {
	        	  mapaObjetos.put(objeto.getRfc().toUpperCase(), objeto.getId());
	          }
	          
	          for (DTOResumen objeto : resumenes.getBatchResumen()) {
	              objeto.getCriterios().getAsegurado().setId(mapaObjetos.get(objeto.getCriterios().getAsegurado().getRfc().toUpperCase()));
	              
	          }

			System.out.println("Termina la separación de  validos e invalidos resumen");
			resumenes.setStatus(true);
		}catch(Exception e) {
			System.out.println("Exception en  carga ");
			System.out.println(e);
			resumenes.setStatus(false);
		}
		return resumenes;
	}
	
	private DTOBatchTransform getValidosRechazadosDetalle(DTOBatchTransform detalles) throws SQLException {
		System.out.println("Comienza la separación de  validos e invalidos detalle");
		try {
			
			System.out.println("Tamaño antes de la separación");
			 System.out.println(detalles.getBatchDetalle().size());
			Map<String, Long> mapaObjetos = new HashMap<>();
			Set<DTOAsegurado> rfcValidos = validateRFC(detalles).getAsegurados();
			System.out.println(rfcValidos.size()+" RFC válidos   ");

			List<DTODetalle> detallesError = new ArrayList<DTODetalle>();
			detallesError.addAll(detalles.getBatchDetalle()); 
			 detalles.setBatchDetalleSinRFC(detallesError); 
			 detalles.getBatchDetalleSinRFC().removeIf(objeto -> {
		            for (DTOAsegurado objetoConValor : rfcValidos) {
		                if (objetoConValor.getRfc().toUpperCase().equals(objeto.getCriterio().getAsegurado().getRfc().toUpperCase())) {
		                    return true;
		                }
		            }
		            return false;
		        });
			 detalles.getBatchDetalle().removeIf(objeto -> {
		            for (DTOAsegurado objetoConValor : rfcValidos) {
		                if (objetoConValor.getRfc().toUpperCase().equals(objeto.getCriterio().getAsegurado().getRfc().toUpperCase())) {
		                    return false;
		                }
		            }
		            return true;
		        });
			
			 
			 System.out.println("Comienza la validación vs el documento");
			 System.out.println(detalles.getBatchDetalle().size());
			 System.out.println("Acabo de separar los válidos");
			  
	          for (DTOAsegurado objeto : rfcValidos) {
	        	  mapaObjetos.put(objeto.getRfc().toUpperCase(), objeto.getId());
	          }
	          
	          for (DTODetalle objeto : detalles.getBatchDetalle()) {
	              objeto.getCriterio().getAsegurado().setId(mapaObjetos.get(objeto.getCriterio().getAsegurado().getRfc().toUpperCase()));
	              
	          }
			detalles.setStatus(true);
		}catch(Exception e) {
			System.out.println("Exception en  carga ");
			System.out.println(e);
			detalles.setStatus(false);
		}
		System.out.println("Termina la separación de  validos e invalidos detalle");
		return detalles;
	}

	@Override
	public DTOCargo getCargoResumen(String fecha, String rfc) {
		String[] tokens=fecha.split("-",-1);
		String mes=tokens[0];
		String anio=tokens[1];
		DTOCargo cargo = new DTOCargo();
		cargo.setDetalles(daoCargasBatch.getDetalleLista(anio, mes, rfc));
		cargo.setResumen(daoCargasBatch.getResumenSuma(anio, mes, rfc));
		cargo.setResumenLista(daoCargasBatch.getResumenLista(anio, mes, rfc));
		cargo.setCriterio(new DTOCriterios());
		cargo.getCriterio().setMes(mes);
		cargo.getCriterio().setAnio(anio);
		cargo.getCriterio().setAsegurado(new DTOAsegurado());
		cargo.getCriterio().getAsegurado().setRfc(rfc);
		cargo.getCriterio().setFecha(fecha);
		cargo.getCriterio().setCatalogoDependencias(new DTOCatalogoDependencias());
		cargo.getCriterio().getCatalogoDependencias().setDescripcionCatalogo(daoCargasBatch.getDependencia(rfc));
		return cargo;
	}

	@Override
	public List<String> getFechaAnio(String rfc) {
		return daoCargasBatch.getMesAnioList(rfc);
	}

	@Override
	public DTOCargaBatchControl updateStatusUser(DTOCargaBatchControl batchControl,List<UserAp> userAp, String filename) throws SQLException{
		Connection con= dataSource.getConnection();
		Statement sentencia = con.createStatement();
		ListIterator <UserAp> userApIterator = userAp.listIterator();
		List <UserAp> rfcValido = new ArrayList<UserAp>();
		List <UserAp> rfcInvalido = new ArrayList<UserAp>();
		try {
			while (userApIterator.hasNext()) {
				UserAp userAP = userApIterator.next();
				if(validateRFC(userAP.getRfc(),sentencia) !=-1L) {
					rfcValido.add(userAP);
				}else {
					rfcInvalido.add(userAP);
				}
		
			}
			
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			sentencia.close();
			con.close();
		}
		if(!rfcValido.isEmpty()) {
			daoCargasBatch.updateEmpleadoApBatch(rfcValido);
			batchControl.setProcessStatus(true);
		}
		LocalDateTime now = LocalDateTime.now(); 
		batchControl.setNombreArchivo(filename);
		batchControl.setFechaCarga(now.toString());
		batchControl.setTipo("Actualización de empleados");
		batchControl.setTotalRegistros(userAp.size());
		batchControl.setRegristrosValidos(rfcValido.size());
		batchControl.setRegistrosRechazados(rfcInvalido.size());
		batchControl.setId(daoCargasBatch.insertControlBatch(batchControl));
		return batchControl;
	}

	@Override
	public List<EmpleadoAPDTO> getEmpleadosListFromExcel(MultipartFile file) throws EncryptedDocumentException, IOException {
		

		List<EmpleadoAPDTO> userApList = new ArrayList<EmpleadoAPDTO>();
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		Sheet sh = wb.getSheetAt(0);
		Row row = wb.getSheetAt(0).getRow(1);
		int maxRow=row.getLastCellNum();
		int iRow=0;
		while(iRow<=maxRow+1) {
			EmpleadoAPDTO userAP = new EmpleadoAPDTO();
			userAP.setNombre( row.getCell(0).getStringCellValue());
			userAP.setApellidoPaterno(row.getCell(1).getStringCellValue());
			userAP.setApellidoMaterno(row.getCell(2).getStringCellValue());
			userAP.setFechaNacimiento(row.getCell(3).getStringCellValue());
			userAP.setSexo(row.getCell(4).getStringCellValue());
			userAP.setRfc(row.getCell(5).getStringCellValue());
			userAP.setNoEmpleado(row.getCell(6).getStringCellValue());
			userAP.setIdidDependencia(Integer.valueOf(row.getCell(7).getStringCellValue()));
			userAP.setIdUnidad(Integer.valueOf(row.getCell(8).getStringCellValue()));
			userAP.setMail(row.getCell(9).getStringCellValue());
			userAP.setPsw(row.getCell(10).getStringCellValue());
			userAP.setEstatus(Integer.valueOf(row.getCell(11).getStringCellValue()));

			userApList.add(userAP);
		    iRow++;  
		    row = sh.getRow(iRow);
		}
		return userApList;
	}

	@Override
	public DTOCargaBatchControl insertEmpleadosAPMasivo(List<EmpleadoAPDTO> listaEmpleados, String filename) throws SQLException {
		
		DTOCargaBatchControl batchControl = new DTOCargaBatchControl();
		List<EmpleadoAPDTO> validos = new ArrayList<EmpleadoAPDTO>();
		List<EmpleadoAPDTO> invalidos = new ArrayList<EmpleadoAPDTO>();
		// TODO Auto-generated method stub
		ListIterator<EmpleadoAPDTO> iteratorEmpleados = listaEmpleados.listIterator();
		while(iteratorEmpleados.hasNext()) {
			
			EmpleadoAPDTO empleadoAP= iteratorEmpleados.next();
			if(serviceUsuarioAcceso.usuarioAPExists(empleadoAP.getRfc())) {
				invalidos.add(empleadoAP);
			}else{
				validos.add(empleadoAP);
				daoUsuarioAcceso.saveEmpleadoUsuarioAP(empleadoAP);
			}
		}
		
		LocalDateTime now = LocalDateTime.now(); 
		batchControl.setNombreArchivo(filename);
		batchControl.setFechaCarga(now.toString());
		batchControl.setTipo("Actualización de empleados");
		batchControl.setTotalRegistros(listaEmpleados.size());
		batchControl.setRegristrosValidos(validos.size());
		batchControl.setRegistrosRechazados(invalidos.size());
		batchControl.setProcessStatus(true);
		batchControl.setId(daoCargasBatch.insertControlBatch(batchControl));
		return batchControl;
	}
	
	@Override
	public List<List<String>> getEnrichedCsvData(String from, String to, String rfc) {
		
		String params="";
		
		if(!from.isEmpty()) {
			if(!to.isEmpty()) {
				params+=params+" fechaSolicitud <"+from+ " ";
			}else {
				params+=params+" fechaSolicitud between "+from+" and "+to+" ";
			}
		}
		if(!rfc.isEmpty()) {
			params+=params+"and rfcAsegurado='"+rfc+"' ";
		}
						
		List<List<String>> csvData = daoCargasBatch.getSolicitudesFiltered(params).stream()
	            .map(dto -> {
	                List<String> innerList = new ArrayList<>();
	                Class<?> dtoClass = dto.getClass();
	                Field[] fields = dtoClass.getDeclaredFields();

	                Arrays.stream(fields)
	                      .map(field -> {
	                          try {
	                              field.setAccessible(true);
	                              Object value = field.get(dto);
	                              return (value != null) ? value.toString() : null;
	                          } catch (IllegalAccessException e) {
	                              e.printStackTrace();
	                              return null;
	                          }
	                      })
	                      .forEach(innerList::add);

	                return innerList;
	            })
	            .collect(Collectors.toList());
        return csvData;
    }
	

	


}