package com.sytecso.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sytecso.service.catalogosAP.ServiceCatalogosAP;

@Controller
@RequestMapping(path = "/catalogos/")
public class ControllerCatalogos {
	
	@Autowired
	private ServiceCatalogosAP serviceCatalogosAP;
	
	@GetMapping(value="/getDpendenciasAP")
	public ResponseEntity<?> getDependencias(){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.getDependencias(),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping(value = "/getConceptos")
	public ResponseEntity<?> getConceptos(){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.getConceptos(),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping(value = "/getUnidades")
	public ResponseEntity<?> getUnidades(){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.getUnidadesAdministrativas(),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	@GetMapping(value = "/getDependenciaUsuario")
	public ResponseEntity<?> getDependenciaUSuario(@RequestParam(name = "rfc") String rfc){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.getDependenciaUser(rfc),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Actualización de  descripción de dependencia
	 * @param id
	 * @param descripcion
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y descripcion de dependencia para su actualización en la base de datos
	 */
	
	@PostMapping(value = "/postActualizaDependenciaDesc")
	public ResponseEntity<?> postActualizaDependenciaDesc(@RequestParam(name = "id") int id , @RequestParam(name = "descripcion") String  descipcion){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.updateCatalogoDependenciasDescripcion(id, descipcion),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Actualización de  descripción de unidad administrativa
	 * @param id
	 * @param descripcion
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y descripcion de unidad administrativa para su actualización en la base de datos
	 */
	@PostMapping(value = "/postActualizaUnidadDesc")
	public ResponseEntity<?> postActualizaUnidadDesc(@RequestParam(name = "id") int id , @RequestParam(name = "descripcion") String  descipcion){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.updateCatalogoUnidadesAdministrativasDescripcion(id, descipcion),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Actualización de  descripción de conceptos
	 * @param id
	 * @param descripcion
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y descripcion de concepto  para su actualización en la base de datos
	 */
	@PostMapping(value = "/postActualizaConceptoDesc")
	public ResponseEntity<?> postActualizaConceptoDesc(@RequestParam(name = "id") int id , @RequestParam(name = "descripcion") String  descipcion){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.updateCatalogoConceptosDescripcion(id, descipcion),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Actualización de  status de dependencia
	 * @param id
	 * @param status
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y status 0 o 1 de concepto  para su actualización en la base de datos
	 */
	
	@PostMapping(value = "/postActualizaDependenciaStatus")
	public ResponseEntity<?> postActualizaDependenciaStatus(@RequestParam(name = "id") int id , @RequestParam(name = "status") int  status){
		try {	
			return new ResponseEntity<>(serviceCatalogosAP.updateCatalogoDependenciasStatus(id, status),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitudessss",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Actualización de  status  de unidad administrativa
	 * @param id
	 * @param status
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y status 0 o 1 de concepto  para su actualización en la base de datos
	 */
	@PostMapping(value = "/postActualizaUnidadStatus")
	public ResponseEntity<?> postActualizaUnidadStatus(@RequestParam(name = "id") int id , @RequestParam(name = "status") int  status){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.updateCatalogoUnidadesAdministrativasStatus(id, status),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Actualización de  status de conceptos
	 * @param id
	 * @param status
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y status 0 o 1 de concepto  para su actualización en la base de datos
	 */
	@PostMapping(value = "/postActualizaConceptoStatus")
	public ResponseEntity<?> postActualizaConceptoStatus(@RequestParam(name = "id") int id , @RequestParam(name = "status") int  status){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.updateCatalogoConceptosStatus(id, status),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Inserción  dependencia
	 * @param descripcion
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y status 0 o 1 de concepto  para su actualización en la base de datos
	 */
	
	@PostMapping(value = "/postInsertDependencia")
	public ResponseEntity<?> postInsertDependencia(@RequestParam(name = "descripcion") String descripcion){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.insertCatalogoDependenciasStatus(descripcion),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Inserción de  unidad administrativa
	 * @param descripcion
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y status 0 o 1 de concepto  para su actualización en la base de datos
	 */
	@PostMapping(value = "/postInsertaUnidad")
	public ResponseEntity<?> postInsertaUnidad(@RequestParam(name = "descripcion") String descripcion){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.insertCatalogoUnidadesAdministrativasStatus(descripcion),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	/**
	 * Inserción de  conceptos
	 * @param descripción
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * 
	 *  recibe id y status 0 o 1 de concepto  para su actualización en la base de datos
	 */
	@PostMapping(value = "/posInsertConcepto")
	public ResponseEntity<?> posInsertConcepto(@RequestParam(name = "descripcion") String descripcion){
		try {
			return new ResponseEntity<>(serviceCatalogosAP.insertCatalogoConceptosStatus(descripcion),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}

}
