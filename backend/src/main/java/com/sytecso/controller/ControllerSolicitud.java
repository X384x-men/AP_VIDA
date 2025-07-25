package com.sytecso.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Base64;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sytecso.component.EventMessage;
import com.sytecso.component.exceptions.EvidenciaOrdenException;
import com.sytecso.component.exceptions.EvidenciaOrdenException.EvidenciaNotFoundException;
import com.sytecso.component.exceptions.RolAccesoException;
import com.sytecso.dto.EventoSolicitudDTO;
import com.sytecso.dto.solicitud.CalculoActuariaDTO;
import com.sytecso.dto.solicitud.ObservacionDTO;
import com.sytecso.dto.solicitud.OrdenPagoDTO;
import com.sytecso.dto.solicitud.SolicitudAPDTO;
import com.sytecso.dto.usuarioacceso.CatalogoAseguradosDTO;
import com.sytecso.service.solicitud.ServiceSolicitud;


@Controller
@RequestMapping(path = "/solicitud/")
public class ControllerSolicitud {

	@Autowired
	private ServiceSolicitud serviceSolicitud;
	
	@PostMapping(value = "/crearSolicitud")
	public ResponseEntity<SolicitudAPDTO> crearSolicitud(@RequestBody SolicitudAPDTO solicitud)
			throws Exception {
		List<SolicitudAPDTO> solicitudes = serviceSolicitud.getValidarSolicitudRFC(solicitud.getRfcGEM(),solicitud.getTipoSolicitud());
		if(solicitudes.size() > 0) {
			return new ResponseEntity<>(solicitudes.get(0), HttpStatus.OK);
		}else {
			solicitud = serviceSolicitud.crearSolicitud(solicitud);
			solicitud.setSolicActiva(false);
			if (solicitud != null && solicitud.getIdSolicitud() > 0L) {
				solicitud.setNumeroRegistro(serviceSolicitud.getSolicitud(solicitud.getIdSolicitud(),solicitud.getTipoSolicitud()).getNumeroRegistro());
				return new ResponseEntity<>(solicitud, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(solicitud,
						HttpStatus.BAD_REQUEST);
			}		
		}

	}
	
	@PostMapping(path = "/documentoSolicitud")
	public ResponseEntity<Long> documentoSolicitud(@RequestParam("documento") MultipartFile file,
			 String fechaCreacion, int tipoDocumento, long idSolicitud, int tipoAccion, long idDocumento, int tipoArchivo, @RequestParam(name= "categoriaSolicitud") String categoriaSolicitud) {
		long id = serviceSolicitud.subirDocumento(file, fechaCreacion, tipoDocumento, idSolicitud, tipoAccion, idDocumento, tipoArchivo, categoriaSolicitud);
		if (id > 0L) {
			return new ResponseEntity<Long>(id, HttpStatus.OK);
		} else {
			return new ResponseEntity<Long>(id, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value = "/getSolicitud")
	public ResponseEntity<SolicitudAPDTO> getSolicitud(@RequestParam(
			name = "idSolicitud") long idSolicitud, @RequestParam(name ="categoriaSolicitud") String categoriaSolicitud) throws Exception {
		SolicitudAPDTO solicitud = serviceSolicitud.getSolicitud(idSolicitud, categoriaSolicitud);
		if (solicitud == null) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(solicitud, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getSolicitudesByEmpleado")
	public ResponseEntity<List<SolicitudAPDTO>> getSolicitudesByEmpleado(@RequestParam(
			name = "rfc") String rfc, @RequestParam(name ="categoriaSolicitud") String categoriaSolicitud) throws Exception {
		List<SolicitudAPDTO> solicitudes = serviceSolicitud.getSolicitudesByIdEmpleado(rfc, categoriaSolicitud);
		if (solicitudes.size() == 0) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(solicitudes, HttpStatus.OK);
	}
	
	@PostMapping(value = "/updateEstatusSolicitud")
	public ResponseEntity<EventMessage> updateEstatusSolicitud(@RequestBody SolicitudAPDTO solicitud)
			throws Exception {
		if (serviceSolicitud.updateEstatusSolicitud(solicitud)) {
			return new ResponseEntity<>(new EventMessage("Se han realizado los cambios correctamente"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("No se han podido realizar los cambios"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	
	@RequestMapping(value = "/getDocumento", method = RequestMethod.GET)
	public ResponseEntity<SolicitudAPDTO> getEvidenciaPdfOrden(@RequestParam(name = "id") Long id)
			throws EvidenciaNotFoundException {
		SolicitudAPDTO solicitud = serviceSolicitud.findDocumentoSolicitud(id);
		// String encodedString = new String(evidencia.getEvidencia());
		byte[] encodedBytes = Base64.getEncoder().encode(solicitud.getPdf());
		String encodedString = new String(encodedBytes);
		solicitud.setStringPdf(encodedString);
		// evidencia.setImage(encodedString);
		if (solicitud.getStringPdf().equals(""))
			throw new EvidenciaOrdenException.EvidenciaNotFoundException("No se ha encontrado el documento");
		return new ResponseEntity<>(solicitud, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getSolicitudesAnalistas")
	public ResponseEntity<List<SolicitudAPDTO>> getSolicitudesAnalistas(@RequestParam(name = "RFC", required = false)String rfc, @RequestParam(name = "nombre", required = false) String nombre,
			@RequestParam(name = "tramite", required = false)String tramite, @RequestParam(name = "status", required = false)String status, 
			@RequestParam( name ="fechaIni", required = false) String fechaIni, @RequestParam( name ="fechaFin", required = false) String fechaFin,
			@RequestParam(name ="categoriaSolicitud") String categoriaSolicitud) throws Exception {
		List<SolicitudAPDTO> solicitudes = new ArrayList<SolicitudAPDTO>();
		solicitudes=serviceSolicitud.getSolicitudesAnalistas(rfc,nombre,tramite,status,fechaIni, fechaFin,categoriaSolicitud);
		
		if ((solicitudes==null)||(solicitudes.size() == 0)) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		} 
		return new ResponseEntity<>(solicitudes, HttpStatus.OK);
	}
	
	@PostMapping(value = "/updateEstatusSolicitudAnalistas")
	public ResponseEntity<EventMessage> updateEstatusSolicitudAnalistas(@RequestBody SolicitudAPDTO solicitud)
			throws Exception {
		if (serviceSolicitud.updateEstatusSolicitudAnalistas(solicitud)) {
			return new ResponseEntity<>(new EventMessage("Se han realizado los cambios correctamente"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("No se han podido realizar los cambios"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@PostMapping(value = "/crearObservacion")
	public ResponseEntity<ObservacionDTO> crearObservacion(@RequestBody ObservacionDTO observacion, @RequestParam(name ="categoriaSolicitud") String categoriaSolicitud)
			throws Exception {
		observacion.setIdObservacion(serviceSolicitud.crearObservacion(observacion, categoriaSolicitud));
		if (observacion.getIdObservacion() > 0L) {
			return new ResponseEntity<>(observacion, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(observacion,
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@PostMapping(value = "/validarImportes")
	public ResponseEntity<EventMessage> validarImportes(@RequestBody SolicitudAPDTO solicitud)
			throws Exception {
		if (serviceSolicitud.validarImportes(solicitud)) {
			return new ResponseEntity<>(new EventMessage("Se han guardado los importes correctamente"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("No se han podido realizar los cambios"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	
	@PostMapping(value = "/updateFechaOrdenPagoSolicitud")
	public ResponseEntity<OrdenPagoDTO> updateFechaOrdenPagoSolicitud(@RequestBody List<SolicitudAPDTO> solicitud)
			throws Exception {
		OrdenPagoDTO orden = new OrdenPagoDTO();
		orden.setIdOrdenPago(serviceSolicitud.updateFechaOrdenPagoSolicitud(solicitud));
		if (orden.getIdOrdenPago() > 0L) {
			return new ResponseEntity<>(orden, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(orden,
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	
	@PostMapping(value = "/informacionPago")
	public ResponseEntity<EventMessage> informacionPago(@RequestBody SolicitudAPDTO solicitud)
			throws Exception {
		if (serviceSolicitud.informacionPago(solicitud)) {
			return new ResponseEntity<>(new EventMessage("Se ha guardado la información del pago correctamente"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("No se han podido realizar los cambios"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@GetMapping(value = "/getCatAsegurados")
	public ResponseEntity<List<CatalogoAseguradosDTO>> getCatAsegurados() throws Exception {
		List<CatalogoAseguradosDTO> cat = serviceSolicitud.getCatAsegurados();
		if (cat.size() == 0) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(cat, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getOrdenPagoLayout")
	public ResponseEntity<List<SolicitudAPDTO>> getOrdenPagoLayout(long idOrdenPago,  @RequestParam(name ="categoriaSolicitud") String categoriaSolicitud) throws Exception {
		List<SolicitudAPDTO> solicitudes = serviceSolicitud.getOrdenPagoLayout(idOrdenPago, categoriaSolicitud);
		if (solicitudes.size() == 0) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(solicitudes, HttpStatus.OK);
	}
	
	@PostMapping(value = "/crearLayoutCalculoActuaria")
	public ResponseEntity<CalculoActuariaDTO> crearLayoutCalculoActuaria(@RequestBody List<SolicitudAPDTO> solicitudes)
			throws Exception {
		CalculoActuariaDTO cal = new CalculoActuariaDTO();
		cal.setIdCalculo(serviceSolicitud.createCalculoActuaria(solicitudes));
		if (cal.getIdCalculo() > 0L) {
			return new ResponseEntity<>(cal, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(cal,
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@GetMapping(value = "/getDataCalculoActuaria")
	public ResponseEntity<List<SolicitudAPDTO>> getDataCalculoActuaria(@RequestParam(
			name = "idCalculo") long idCalculo,   @RequestParam(name ="categoriaSolicitud") String categoriaSolicitud) throws Exception {
		List<SolicitudAPDTO> solicitudes = serviceSolicitud.getDataCalculoActuaria(idCalculo, categoriaSolicitud);
		if (solicitudes.size() == 0) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(solicitudes, HttpStatus.OK);
	}
	
	@PostMapping(value = "/updateImportesDataLayout")
	public ResponseEntity<EventMessage> updateImportesDataLayout(@RequestBody List<SolicitudAPDTO> solicitudes)
			throws Exception {
		if (serviceSolicitud.updateImportesSolicitudActuaria(solicitudes)) {
			return new ResponseEntity<>(new EventMessage("Se han cargado correctamente los importes"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("No se han podido realizar uno o todos los cambios"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@GetMapping(value = "/getListCalculo")
	public ResponseEntity<List<CalculoActuariaDTO>> getListCalculo() throws Exception {
		List<CalculoActuariaDTO> archivos = serviceSolicitud.getListCalculoActuaria();
		if (archivos == null) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(archivos, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getEventosSolicitud")
	public ResponseEntity<List<EventoSolicitudDTO>> getEventosSolicitud(@RequestParam(name = "idSolicitud") long idSolicitud) throws Exception {
		List<EventoSolicitudDTO> eventosSolicitud = serviceSolicitud.getEventosSolicitud(idSolicitud);
		if (eventosSolicitud == null) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(eventosSolicitud, HttpStatus.OK);
	}
	
	@PostMapping(value = "/asignaSolicitud")
	public ResponseEntity<EventMessage> updateEstatusSolicitud(@RequestParam(name = "idSolicitud") long idSolicitud, @RequestParam(name = "RFCEmpleado") String RFCEmpleado, @RequestParam(name="tipoSolictud") String categoriaSolicitud)
			throws Exception {
		if (serviceSolicitud.updateSOlicitudAsignacion(idSolicitud, RFCEmpleado, categoriaSolicitud)) {
			return new ResponseEntity<>(new EventMessage("Se ha realizado la asigación satisfactoriamente"), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("No se han podido realizar los cambios"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@PostMapping(value = "/actualizaSolicitud")
	public ResponseEntity<SolicitudAPDTO> actualizaSolicitud(@RequestBody SolicitudAPDTO solicitud) throws Exception {
		SolicitudAPDTO solResultado= new SolicitudAPDTO();
		solResultado = serviceSolicitud.updateSolicitud(solicitud);
		if(solResultado!=null) {
			return new ResponseEntity<>(solicitud, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(solicitud,
					HttpStatus.BAD_REQUEST);	
		}				
	}

}