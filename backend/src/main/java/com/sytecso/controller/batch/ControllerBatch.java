package com.sytecso.controller.batch;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

import com.sytecso.component.EventMessage;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioExistsException;
import com.sytecso.dto.batchmodel.DTOCargaBatchControl;
import com.sytecso.dto.batchmodel.DTOCargo;
import com.sytecso.service.batchManaging.HandleBatch;
import com.sytecso.dto.usuario.UserAp;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import java.nio.charset.StandardCharsets;



@Controller
@RequestMapping(path = "/batch/")
public class ControllerBatch {
	@Autowired
	private HandleBatch handleBatch;
	/**
	 * Carga estados de cuenta
	 * @param file
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@PostMapping(value = "/postBatch")
	public ResponseEntity<DTOCargaBatchControl> inputFile(@RequestParam("file") MultipartFile file) throws SQLException, IOException {
		System.out.println("Entrando en la carga de archivos");
		DTOCargaBatchControl batch=handleBatch.administraCargas(handleBatch.convertMultipartToFile(file));
		if (batch.isProcessStatus()) {
			batch.setMensaje("Operacion exitosa");
			return new ResponseEntity<>(batch, HttpStatus.OK);
		} else {
			if(batch.getMensaje().isEmpty())
				batch.setMensaje("Operacion fallida");
			return new ResponseEntity<>(batch, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping(value = "/processBatch")
	public ResponseEntity<EventMessage> processAccountStatus(@RequestParam("filename") String filename) throws SQLException, IOException {
		System.out.println("Iniciando carga de estados de cuenta");
		return new ResponseEntity<>(new EventMessage("El contenido del archivo estaá disponible al pasar a estado: PROCESADO"), HttpStatus.OK);
	}
	
	/**
	 * Carga resúmenes de estados de cuenta
	 * @param idCarga
	 * @return
	 */
	@GetMapping(value = "/resumenBatch")
	public ResponseEntity<?> getDetailCarga(@RequestParam long idCarga){	
		try {
			DTOCargaBatchControl control =handleBatch.getBatchResumenCarga(idCarga);
			if(control.getMensaje().equals("Operación fallida"))
				return new ResponseEntity<DTOCargaBatchControl>(control, HttpStatus.OK);
			else
				return new ResponseEntity<DTOCargaBatchControl>(control, HttpStatus.OK);
		}catch(Exception e) {
			DTOCargaBatchControl control =  new DTOCargaBatchControl();
			control.setMensaje("Error al procesar la solicitud");
			return new ResponseEntity<DTOCargaBatchControl>(control, HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value="/getComboMesAnio")
	public ResponseEntity<?> getMesAnio(@RequestParam String rfc){
		try {
			return new ResponseEntity<>(handleBatch.getFechaAnio(rfc),HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value="/getMovimientos")
	public ResponseEntity<DTOCargo> getMovimientos(@RequestParam String rfc, @RequestParam String fecha){
		try {
			DTOCargo cargo= handleBatch.getCargoResumen(fecha, rfc);
			if(cargo.getResumenLista().isEmpty()) {
				cargo.setMensaje("Sin datos para el periodo solicitado");
				return new ResponseEntity<>(cargo,HttpStatus.BAD_REQUEST);
			}else {
				cargo.setMensaje("operacion exitosa");
				cargo.setRetenedor("GOBIERNO DEL ESTADO DE MEXICO");
				cargo.setPoliza("12-992");
				return new ResponseEntity<>(cargo,HttpStatus.OK);
			}
			
		}catch(Exception e) {
			DTOCargo cargo = new DTOCargo();
			cargo.setMensaje("Ocurrió un problema al procesar la solicitud");
			return new ResponseEntity<>(cargo, HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Carga estados de cuenta
	 * @param file
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 * Recibe un archivo de carga tip excel con dos columnas  en la que efectua la actualización de los registros tomando de la primera columna el rfc como string y  en la segunda el status como 0 o 1 
	 * este método evade la primera fila que es el encabezado
	 */
	@PostMapping(value = "/postInactivateUsers")
	public ResponseEntity<DTOCargaBatchControl> postInactivateUsers(@RequestParam("file") MultipartFile file) throws SQLException, IOException {
		
		String fileName = file.getOriginalFilename();
		List<UserAp> userApList = new ArrayList<UserAp>();
		Workbook wb = WorkbookFactory.create(file.getInputStream());
		Sheet sh = wb.getSheetAt(0);
		Row row = wb.getSheetAt(0).getRow(1);
		int maxRow=row.getLastCellNum();
		int iRow=0;
		while(iRow<=maxRow+1) {
			UserAp userAP = new UserAp();
			
			userAP.setRfc( row.getCell(0).getStringCellValue());
			//System.out.println(userAP.getRfc()+"rfc");
			
			userAP.setStatus(Integer.parseInt(row.getCell(1).getStringCellValue()));
			//System.out.println(userAP.getStatus()+"status");
			userApList.add(userAP);
		    iRow++;  
		    row = sh.getRow(iRow);
		   // System.out.println(row+" esta es la fila");
		}
		System.out.println("Entrando en la carga de archivos");
		DTOCargaBatchControl batch=handleBatch.updateStatusUser(new DTOCargaBatchControl(), userApList, fileName);
		if (batch.isProcessStatus()) {
			batch.setMensaje("Operacion exitosa");
			return new ResponseEntity<>(batch, HttpStatus.OK);
		} else {
			if(batch.getMensaje()==null)
				batch.setMensaje("Operacion fallida");
			return new ResponseEntity<>(batch, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	/**
	 * Carga masiva de empleados
	 * @param Excel multipart File 
	 * @return ResponseEntity<DTOCargaBatchControl>
	 * @throws SQLException
	 * @throws UsuarioExistsException
	 * @throws IOException 
	 * @throws EncryptedDocumentException 
	 */
	@PostMapping(value = "/carga-empleados")
	public ResponseEntity<DTOCargaBatchControl> uploadEmployees(@RequestParam("file") MultipartFile file) throws SQLException, UsuarioExistsException, EncryptedDocumentException, IOException {
		String fileName = file.getOriginalFilename();
		DTOCargaBatchControl batch=handleBatch.insertEmpleadosAPMasivo(handleBatch.getEmpleadosListFromExcel(file), fileName);
		if (batch.isProcessStatus()) {
			batch.setMensaje("Operacion exitosa");
			return new ResponseEntity<>(batch, HttpStatus.OK);
		} else {
			if(batch.getMensaje()==null)
				batch.setMensaje("Operacion fallida");
			return new ResponseEntity<>(batch, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	 @GetMapping("/descargaSolicitudesCSV")
	    public ResponseEntity<StreamingResponseBody> descargaSolicitudesCSV(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("rfc") String rfc) {
		 HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.parseMediaType("text/csv"));
	        headers.setContentDisposition(
	                ContentDisposition.builder("attachment")
	                        .filename("Solicitudes"+(new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date ((System.currentTimeMillis()/1000)*1000)))+".csv")
	                        .build()
	        );

	        StreamingResponseBody responseBody = outputStream -> {
	            try {
	                // Retrieve CSV data from the service
	                List<List<String>> csvData = handleBatch.getEnrichedCsvData(from, to, rfc);

	                // Write CSV data to the output stream
	                for (List<String> row : csvData) {
	                    String csvLine = String.join(",", row) + "\n";
	                    outputStream.write(csvLine.getBytes(StandardCharsets.UTF_8));
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        };

	        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
	    }
}