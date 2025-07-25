package com.sytecso.service.solicitud.impl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sytecso.component.utility.SessionEmail;
import com.sytecso.component.utility.TemplateEmail;
import com.sytecso.dao.solicitud.DAOSolicitud;
import com.sytecso.dao.usuario.DAOUsuarioAcceso;
import com.sytecso.dto.EventoSolicitudDTO;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.dto.solicitud.CalculoActuariaDTO;
import com.sytecso.dto.solicitud.CalculoActuariaHasSolicDTO;
import com.sytecso.dto.solicitud.ObservacionDTO;
import com.sytecso.dto.solicitud.OrdenPagoDTO;
import com.sytecso.dto.solicitud.OrdenPagoHasSolicitudDTO;
import com.sytecso.dto.solicitud.SolicitudAPDTO;
import com.sytecso.dto.usuarioacceso.CatalogoAseguradosDTO;
import com.sytecso.service.solicitud.ServiceSolicitud;

@Service

public class ServiceSolicitudImpl implements ServiceSolicitud {

	
	@Autowired
	private DAOSolicitud daoSolicitud;
	
	@Autowired
	private DAOUsuarioAcceso daoUsuarioAcceso;
	
	@Override
	public SolicitudAPDTO crearSolicitud(SolicitudAPDTO solicitud) throws Exception {
		boolean status = false;
		if(solicitud.getIdSolicitud() > 0) {
			return daoSolicitud.actualizarSolicitud(solicitud) ? solicitud : null;
		}else {
			solicitud.setIdEmpleado(daoUsuarioAcceso.getEmpleadoAP(solicitud.getRfcAsegurado()).getIdEmpleado());
			solicitud.setIdSolicitud(daoSolicitud.crearSolicitud(solicitud));
			solicitud.setNumeroRegistro(getSolicitud(solicitud.getIdSolicitud()).getNumeroRegistro());
			status = envioCorreoNuevaSolicitud(solicitud);
			return status ? solicitud : null;			
		}
	}
	
	@Override
	public long subirDocumento(MultipartFile file, String fechaCreacion, int tipoDocumento, long idSolicitud, int tipoAccion, long idDocumento, int tipoArchivo) {
		long insertDocumento = 0L;
		try {
			if(tipoAccion == 1) {
				insertDocumento = daoSolicitud.subirDocumento(file, fechaCreacion);
				if(insertDocumento > 0L) {
					daoSolicitud.crearSolicitudHasDocumento(insertDocumento, idSolicitud, tipoDocumento, tipoArchivo);				
				}				
			}else {
				insertDocumento = daoSolicitud.actualizarDocumento(file, idDocumento);
				daoSolicitud.actualizarSolicitudHasDocumento(insertDocumento, idSolicitud, tipoArchivo);
			}
			
		} catch (Exception e) {
		}
		
		return insertDocumento;
	}
	
	@Override
	public List<SolicitudAPDTO> getSolicitudesByIdEmpleado(String rfc) throws Exception {
		return daoSolicitud.getSolicitudesByIdEmpleado(daoUsuarioAcceso.getEmpleadoAP(rfc).getIdEmpleado());
	}
	
	
	@Override
	public boolean updateEstatusSolicitud(SolicitudAPDTO solicitud) throws Exception {
		return daoSolicitud.updateEstatusSolicitud(solicitud);
	}
	
	@Override
	public SolicitudAPDTO getSolicitud(long idSolicitud) throws Exception {
		return daoSolicitud.getSolicitud(idSolicitud);
	}
	
	
	@Override
	public SolicitudAPDTO findDocumentoSolicitud(Long id) {
		return daoSolicitud.findDocumentoSolicitud(id);
	}
	
	@Override
	public List<SolicitudAPDTO> getSolicitudesAnalistas(String rfc,String nombre, String tramite, String status) throws Exception {
		String params="";
		String paramsSol="";
		boolean isEmpleado=false;
		if((rfc!=null)&&(!rfc.equals(""))) {
			params= params+" and epap.rfc='"+rfc+"' ";
			isEmpleado=true;
		}
		if((nombre!=null)&&(!nombre.equals(""))) {
			params= params+" and concat(epap.nombre,' ',epap.apellidoP,' ',epap.apellidoM) like '%"+nombre+"%' ";
			isEmpleado=true;
		}
		if(( tramite!=null)&&(!tramite.equals(""))) {
			params = params+" and sol.tipoTRamite='"+tramite+"' ";
			if(!isEmpleado)
				paramsSol = paramsSol+" and sol.tipoTRamite='"+tramite+"' ";
			
			
		}
		if((status!=null)&&(!status.equals(""))) {
			params = params+" and sol.statusSolicitud='"+status+"' ";
			if(!isEmpleado)
				paramsSol = paramsSol+" and sol.statusSolicitud='"+status+"' ";
		}
		List<SolicitudAPDTO> solic = daoSolicitud.getSolicitudesAnalistas( params, paramsSol,isEmpleado);
	/*	for(SolicitudAPDTO sol : solic) {
			EmpleadoAPDTO emp = daoUsuarioAcceso.getEmpleadoAP(sol.getRfcAsegurado());
			sol.setSexo(emp.getSexo() != null ? emp.getSexo().equals("HOMBRE") ? "M" : "F" : "");
			sol.setFechaNac(emp.getFechaNacimiento() != null ? emp.getFechaNacimiento() : "");
			
			EmpleadoAPDTO empGenera = daoUsuarioAcceso.getEmpleadoAPById(sol.getIdEmpleadoGeneraOrden());
			sol.setNombreEmpleadoGeneraOrden((empGenera.getNombre() != null ? empGenera.getNombre() : "") + " " + (empGenera.getApellidoPaterno() != null ? empGenera.getApellidoPaterno() : "") + " " + (empGenera.getApellidoMaterno() != null ? empGenera.getApellidoMaterno() : ""));
			OrdenPagoDTO orden = daoSolicitud.getOrdenPago(sol.getIdSolicitud());
			if(orden != null) {
				sol.setIdOrdenPago(orden.getIdOrdenPago());
			} 
			CalculoActuariaDTO calculo = daoSolicitud.getCalculoActuaria(sol.getIdSolicitud());
			if(calculo != null) {
				sol.setIdCalculoActuaria(calculo.getIdCalculo());
			} 
		}
		*/
		return solic;
	}
	
	@Override
	public boolean updateEstatusSolicitudAnalistas(SolicitudAPDTO solicitud) throws Exception {
		boolean status=true;
		status=daoSolicitud.updateEstatusSolicitudAnalistas(solicitud);
		if(solicitud.getStatusSolicitud().contentEquals("PENDIENTE DE DOCS")&&status && solicitud.getEmail() != null && !solicitud.getEmail().equals("")) {
			status=envioCorreoPendienteDocs(solicitud);
			
		}
		
		return status ;
	}
	
	private boolean envioCorreoPendienteDocs(SolicitudAPDTO solicitud) {
		boolean status=false;
		Session session = SessionEmail.sessionEmail();
        String from = SessionEmail.getFromProp();

        try {
        	 LocalDateTime myDateObj = LocalDateTime.now();
        	 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        	    String fecha = myDateObj.format(myFormatObj);
        	    
        	String nombre = solicitud.getNombre() + " " + solicitud.getApellidoPaterno() + " " + solicitud.getApellidoMaterno();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(solicitud.getEmail()));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("recepcion.gem@apvida.mx"));
            message.setSubject("Solicitud de Documentos");
            message.setContent(TemplateEmail.templateCambioPendienteDocs(nombre, fecha), "text/html");
            Transport.send(message);
            status=true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		return status;
	}
	
	private boolean envioCorreoNuevaSolicitud(SolicitudAPDTO solicitud) {
		boolean status=false;
		Session session = SessionEmail.sessionEmail();
        String from = SessionEmail.getFromProp();

        try {
        	 LocalDateTime myDateObj = LocalDateTime.now();
        	 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        	    String fecha = myDateObj.format(myFormatObj);
        	    
        	String nombre = solicitud.getNombre() + " " + solicitud.getApellidoPaterno() + " " + solicitud.getApellidoMaterno();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(solicitud.getEmail()));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("recepcion.gem@apvida.mx"));
            message.setSubject("Nueva Solicitud");
            message.setContent(TemplateEmail.templateCreacionSolicitudes(nombre, fecha, solicitud.getTipoTramite(), solicitud.getNumeroRegistro()+""), "text/html");
            Transport.send(message);
            status=true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		return status;
	}
	
	
	@Override
	public long crearObservacion(ObservacionDTO obs) throws Exception {
		long idObservacion = daoSolicitud.crearObservacionSolicitud(obs);
		if(idObservacion > 0L) {
			daoSolicitud.crearSolicitudHasObservacion(idObservacion, obs.getIdSolicitud());	
		}
		return idObservacion;
	}
	
	
	@Override
	public boolean validarImportes(SolicitudAPDTO solicitud) throws Exception {
		return daoSolicitud.validarImportes(solicitud);
	}
	
	@Override
	public long updateFechaOrdenPagoSolicitud(List<SolicitudAPDTO> solicitudes) throws Exception {
		boolean status = true;
		long ordenPago = 0L;
		if(solicitudes.size() > 0) {
			long idEmpleadoGenera = daoUsuarioAcceso.getEmpleadoAP(solicitudes.get(0).getRfcEmpleadoGeneraOrden()).getIdEmpleado();
			OrdenPagoDTO orden = new OrdenPagoDTO();
			orden.setFechaCreacion(solicitudes.get(0).getFechaOrdenPago());
			orden.setIdEmpleadoGenera(idEmpleadoGenera);
			ordenPago = daoSolicitud.crearOrdenPago(orden);
			if(ordenPago > 0L) {
				for(SolicitudAPDTO solic : solicitudes) {
					solic.setIdEmpleadoGeneraOrden(idEmpleadoGenera);
					boolean updateSolicitud = daoSolicitud.updateFechaOrdenPagoSolicitud(solic);
					if(updateSolicitud) {
						OrdenPagoHasSolicitudDTO ordenSolic = new OrdenPagoHasSolicitudDTO();
						ordenSolic.setIdOrdenPago(ordenPago);
						ordenSolic.setIdSolicitud(solic.getIdSolicitud());
						long ordenPagoSolic = daoSolicitud.crearOrdenPagoSolicitud(ordenSolic);
						if(!(ordenPagoSolic > 0)) {
							status = false;
						}
					}else {
						status = false;
					}
				}			
			}else {
				status = false;
			}			
		}else {
			status = false;
		}
		return status ? ordenPago : 0L;
	}
	
	@Override
	public boolean informacionPago(SolicitudAPDTO solicitud) throws Exception {
		return daoSolicitud.informacionPago(solicitud);
	}

	@Override
	public List<CatalogoAseguradosDTO> getCatAsegurados() {
		return daoSolicitud.getCatAsegurados();
	}
	
	
	@Override
	public List<SolicitudAPDTO> getValidarSolicitudRFC(String rfc) throws Exception {
		return daoSolicitud.getValidarSolicitudRFC(rfc);
	}
	
	
	@Override
	public List<SolicitudAPDTO> getOrdenPagoLayout(long idOrdenPago) throws Exception {
		List<SolicitudAPDTO> solic = daoSolicitud.getDataReport(idOrdenPago);
		for(SolicitudAPDTO sol : solic) {
			EmpleadoAPDTO emp = daoUsuarioAcceso.getEmpleadoAP(sol.getRfcAsegurado());
			sol.setSexo(emp.getSexo() != null ? emp.getSexo().equals("HOMBRE") ? "M" : "F" : "");
			sol.setFechaNac(emp.getFechaNacimiento() != null ? emp.getFechaNacimiento() : "");
			
			EmpleadoAPDTO empGenera = daoUsuarioAcceso.getEmpleadoAPById(sol.getIdEmpleadoGeneraOrden());
			sol.setNombreEmpleadoGeneraOrden((empGenera.getNombre() != null ? empGenera.getNombre() : "") + " " + (empGenera.getApellidoPaterno() != null ? empGenera.getApellidoPaterno() : "") + " " + (empGenera.getApellidoMaterno() != null ? empGenera.getApellidoMaterno() : ""));
			OrdenPagoDTO orden = daoSolicitud.getOrdenPago(sol.getIdSolicitud());
			if(orden != null) {
				sol.setIdOrdenPago(orden.getIdOrdenPago());
			} 
		}
		return solic;
	}
	
	
	@Override
	public long createCalculoActuaria(List<SolicitudAPDTO> solicitudes) throws Exception {
		boolean status = true;
		long idCalculo = 0L;
		if(solicitudes.size() > 0) {
			long idEmpleadoGenera = daoUsuarioAcceso.getEmpleadoAP(solicitudes.get(0).getRfcEmpleadoGeneraOrden()).getIdEmpleado();
			CalculoActuariaDTO calculo = new CalculoActuariaDTO();
			calculo.setIdEmpleadoGenera(idEmpleadoGenera);
			calculo.setFechaCreacion(solicitudes.get(0).getFechaOrdenPago()); // Utilizo este atributo de apoyo para no tener que agregar uno mas para manejar las fechas de creacion
			calculo.setNumRegistros(solicitudes.size());
			idCalculo = daoSolicitud.crearCalculoActuaria(calculo);
			if(idCalculo > 0L) {
				for(SolicitudAPDTO sol: solicitudes) {
					CalculoActuariaHasSolicDTO cal = new CalculoActuariaHasSolicDTO();
					cal.setIdCalculoActuaria(idCalculo);
					cal.setIdSolicitud(sol.getIdSolicitud());
					daoSolicitud.crearCalculoActuariaSolicitud(cal);
				}				
			}else {
				status = false;
			}
		}else {
			status = false;
		}
		return status ? idCalculo : 0L;
	}
	
	
	@Override
	public List<SolicitudAPDTO> getDataCalculoActuaria(long idCalculo) throws Exception {
		List<SolicitudAPDTO> solicitudes = daoSolicitud.getDataCalculoActuaria(idCalculo);
		return solicitudes;
	}
	
	@Override
	public boolean updateImportesSolicitudActuaria(List<SolicitudAPDTO> solicitudes) throws Exception {
		boolean status=true;
		int cont = 0;
		CalculoActuariaDTO cal = daoSolicitud.getCalculoActuariaByFolioSolicitud(solicitudes.get(0).getNumeroRegistro());
		for(SolicitudAPDTO soli : solicitudes) {
			String statusSoli = daoSolicitud.getStatusSolicitudByFolio(soli.getNumeroRegistro()).getStatusSolicitud();
			if(statusSoli.equals("Proceso de revision de pago")) {
				boolean statusUpdate = daoSolicitud.updateImportesSolicitudLayout(soli);
				if(statusUpdate) {
					cont++;
				}else {
					status = false;
				}
			}
		}
		System.out.println(cont);
		if(cont > 0) {
			cal.setRegistrosCargados(cal.getRegistrosCargados() + cont);
			if(cal.getNumRegistros() == cal.getRegistrosCargados()) {
				cal.setFechaCarga(solicitudes.get(0).getFechaCarga());
			}else {
				cal.setFechaCarga("");
			}
			status = daoSolicitud.updateNumeroImportesValidados(cal);
		}
		
		return status ;
	}
	
	
	@Override
	public List<CalculoActuariaDTO> getListCalculoActuaria() throws Exception {
		return daoSolicitud.getListCalculoActuaria();
	}

	@Override
	public List<EventoSolicitudDTO> getEventosSolicitud(long solicitud) {
		return daoSolicitud.getEventosSolicitud(solicitud);
	}

	@Override
	public boolean updateSOlicitudAsignacion(long idSolicitud, String RFC) throws SQLException {
		// TODO Auto-generated method stub
		return daoSolicitud.updateSOlicitudAsignacion(idSolicitud,RFC);
	}

	@Override
	public SolicitudAPDTO updateSolicitud(SolicitudAPDTO solicitud) throws Exception {
			return daoSolicitud.actualizarSolicitud(solicitud) ? solicitud : null;
	}
}