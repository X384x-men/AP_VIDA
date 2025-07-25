package com.sytecso.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.sytecso.component.EventMessage;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioExistsException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioNotExistsException;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.service.catalogosAP.ServiceCatalogosAP;
import com.sytecso.service.usuario.ServiceUsuarioAcceso;

@Controller
public class ControllerLogin {
	private static final String INDEX_VIEW = "index";
	
	@Autowired
	private ServiceUsuarioAcceso serviceUsuarioAcceso;
	
	@Autowired
	private ServiceCatalogosAP serviceCatalogosAP;

	@GetMapping(value = "/login")
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {
		return INDEX_VIEW;
	}
	
	@PostMapping(value = "/createEmpleadoAP")
	public ResponseEntity<EventMessage> createEmpleadoAP(@RequestBody EmpleadoAPDTO empleado)
			throws UsuarioExistsException, UsuarioNotExistsException, SQLException {
		System.out.println("Entra a crear empleado AP ***********************************" + empleado.getRfc());
		if (!serviceUsuarioAcceso.usuarioAPExists(empleado.getRfc())) {
			if (serviceUsuarioAcceso.createUsuarioAP(empleado)) {
				return new ResponseEntity<>(new EventMessage("El usuario fue guardado correctamente"), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(new EventMessage("Ocurrio un error al guardar al usuario"),
				HttpStatus.BAD_REQUEST);
			}
		}else {
			EmpleadoAPDTO employee = serviceUsuarioAcceso.getEmpleadoAPbyRFC(empleado.getRfc());
			System.out.println(employee.getRfc());
			String employeeA = empleado.getNombre() + empleado.getApellidoPaterno() + empleado.getApellidoMaterno();
			String employeeB = employee.getNombre() + employee.getApellidoPaterno() + employee.getApellidoMaterno();
			employeeB = employeeB.replace(" ", "");
			employeeB = employeeB.replace("null", "");
			System.out.println(employeeA + employeeB);
			if (hasEmail(employee)) {
				empleado.setIdEmpleado(employee.getIdEmpleado());
				empleado.setIdUsuarioAcceso(employee.getIdUsuarioAcceso());
				if(serviceUsuarioAcceso.updateUsuarioAP(empleado)) {
					if(serviceUsuarioAcceso.updateEmpleado(empleado)){
						return new ResponseEntity<>(new EventMessage("El usuario fue guardado correctamente."), HttpStatus.OK);
					}
				}
				
			}
			return new ResponseEntity<>(new EventMessage("El RFC ya ha sido asignado anteriormente, asegurese de escribir bien el RFC o comuniquese con su ejecutivo para obtener mas ayuda"),
					HttpStatus.BAD_REQUEST);
		}
	}

	private Boolean hasEmail(EmpleadoAPDTO employee) {
		return employee.getMail() != "";
	}
	
	
	
	@GetMapping(value = "/getDependencias")
	public ResponseEntity<?> getDependencias(){
		try {
			System.out.println("Entrando a la obtención de las dependencias");
			return new ResponseEntity<>(serviceCatalogosAP.getDependencias(),HttpStatus.OK);
		}catch(Exception e){
			return new ResponseEntity<>("Error al procesar la solicitud",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	
}
