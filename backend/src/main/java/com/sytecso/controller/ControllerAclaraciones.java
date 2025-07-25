package com.sytecso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sytecso.component.EventMessage;
import com.sytecso.dto.AclaracionDTO;
import com.sytecso.dto.CatalogoDocumentoDTO;
import com.sytecso.dto.TipoAclaracionDTO;
import com.sytecso.service.aclaraciones.ServiceAclaraciones;

@Controller
@RequestMapping(path = "/aclaraciones/")
public class ControllerAclaraciones {
	
	@Autowired
	private ServiceAclaraciones serviceAclaraciones;
	
	@PostMapping(value = "/postCrearAclaracion")
	public ResponseEntity<?> postActualizaDependenciaDesc(@RequestBody  AclaracionDTO aclaracion ){
		try {		
			if(serviceAclaraciones.crearAclaracion(aclaracion)!=-1L) {
				return new ResponseEntity<>(new EventMessage("Aclaracion creada con éxito"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al  crear la aclaración"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			System.out.println(e);
			return new ResponseEntity<>(new EventMessage("Error al crear la aclaración"),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "/postinsertCatalogoDocumentos")
	public ResponseEntity<?> postinsertCatalogoDocumentos(@RequestParam("tipoDocumento")  String tipoDocumento){
		try {		
			if(serviceAclaraciones.insertCatalogoDocumento(tipoDocumento)) {
				return new ResponseEntity<>(new EventMessage("Ingreso en catálogo exitoso"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al insertar en catálogo"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<>(new EventMessage("Error al insertar en catálogo"),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PostMapping(value = "/postInsertTipoAclaracion")
	public ResponseEntity<?> postInsertTipoAclaracion(@RequestParam("tipoAclaracion")  String tipoAclaracion,@RequestParam("descripcion")  String descripcion){
		try {		
			if(serviceAclaraciones.insertCatalogoTipoAclaracion(tipoAclaracion, descripcion)) {
				return new ResponseEntity<>(new EventMessage("Aclaracion creada con éxito"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al  crear la aclaración"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<>(new EventMessage("Error al crear la solicitud"),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/getAclaraciones")
	public ResponseEntity<?> getAclaracions(@RequestParam("rfc")String rfc, @RequestParam("nombre")String nombre,
			@RequestParam("dependencia")String dependencia , @RequestParam("fechaRegistroPortal")String fechaRegistroPortal, @RequestParam("telefono")String telefono, @RequestParam("email")String email,
			@RequestParam("aclaracionEmpleados") int aclaracionEmpleados )
			throws Exception {
		List<AclaracionDTO> aclaraciones= serviceAclaraciones.getAclaraciones(rfc, nombre, dependencia, fechaRegistroPortal, telefono, email, aclaracionEmpleados);
		if (!aclaraciones.isEmpty()) {
			return new ResponseEntity<>(aclaraciones, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("Sin aclaraciones"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@PutMapping(value = "/updateAclaracionStatus")
	public ResponseEntity<?> updateAclaracionStatus(@RequestParam("idAclaracionStatus")  long idAclaracion,@RequestParam("status")  int status){
		try {		
			if(serviceAclaraciones.updateAclaracionStatus(idAclaracion, status)) {
				return new ResponseEntity<>(new EventMessage("Status actualizado con éxito"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping(value = "/updateTipoAclaracionesCatalogo")
	public ResponseEntity<?> updateTipoAclaracionesCatalogo(@RequestParam("idTipoDesc")  long idTipoDesc,@RequestParam("desc")  String desc){
		try {		
			if(serviceAclaraciones.updateTipoAclaracionesCatalogo(idTipoDesc, desc)) {
				return new ResponseEntity<>(new EventMessage("Resgistro actualizado con éxito"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@PutMapping(value = "/updateCatalogoDocumentos")
	public ResponseEntity<?> updateCatalogoDocumentos(@RequestParam("idTipoDocumento")  long idTipoDocumento,@RequestParam("desc")  String  desc){
		try {		
			if(serviceAclaraciones.updateCatalogoDocumentos(idTipoDocumento, desc)) {
				return new ResponseEntity<>(new EventMessage("Resgistro actualizado con éxito"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping(value = "/getTipoAclaracion")
	public ResponseEntity<?> getTipoAclaracion( )
			throws Exception {
		List<TipoAclaracionDTO> tipoAclaraciones= serviceAclaraciones.getTipoAclaracion();
		if (!tipoAclaraciones.isEmpty()) {
			return new ResponseEntity<>(tipoAclaraciones, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("Sin registros para mostrar"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	@GetMapping(value = "/getCatalogoDocumento")
	public ResponseEntity<?> getCatalogoDocumento()
			throws Exception {
		List<CatalogoDocumentoDTO> catalogodocumentos= serviceAclaraciones.getCatalogoDocumento();
		if (!catalogodocumentos.isEmpty()) {
			return new ResponseEntity<>(catalogodocumentos, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("Sin registros para mostrar"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@PutMapping(value = "/updateAclaracion")
	public ResponseEntity<?> updateAclaracion(@RequestBody AclaracionDTO aclaracion ){
		try {		
			if(serviceAclaraciones.updateAclaracion(aclaracion)) {
				return new ResponseEntity<>(new EventMessage("Aclaración actualizada con éxito"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/getAclaracion")
	public ResponseEntity<?> getAclaracion(@RequestParam("idAclaracion")long idAclaracion, @RequestParam("funcioamiento") int funcionamiento )
			throws Exception {
		AclaracionDTO aclaracion= serviceAclaraciones.getAclaracion(idAclaracion,funcionamiento);
		if (aclaracion.getIdAclaracion()!=-1L) {
			return new ResponseEntity<>(aclaracion, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new EventMessage("Sin aclaraciones"),
					HttpStatus.BAD_REQUEST);
		}		

	}
	
	@PutMapping(value = "/updateAclaracionEmpleado")
	public ResponseEntity<?> updateAclaracionEmpleado(@RequestBody AclaracionDTO aclaracion ){
		try {		
			if(serviceAclaraciones.updateAclaracionEmpleado(aclaracion)) {
				return new ResponseEntity<>(new EventMessage("Aclaración actualizada con éxito"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/updateAclaracionDocumento")
	public ResponseEntity<?> updateAclaracionDocumento(@RequestBody AclaracionDTO aclaracion ){
		try {		
			if(serviceAclaraciones.updateAclaracionDocumento(aclaracion)) {
				return new ResponseEntity<>(new EventMessage("Aclaración actualizada con éxito"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e){
			return new ResponseEntity<>(new EventMessage("Error al procesar la solicitud"),HttpStatus.BAD_REQUEST);
		}
	}

}
