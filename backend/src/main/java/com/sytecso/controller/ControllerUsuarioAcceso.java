package com.sytecso.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sytecso.component.EventMessage;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.dto.usuarioacceso.UsuarioAcceso;
import com.sytecso.component.exceptions.RolAccesoException;
import com.sytecso.component.exceptions.RolAccesoException.NotRolesFoundException;
import com.sytecso.component.exceptions.CuadrillasException.NotUserFoundException;
import com.sytecso.component.exceptions.UsuarioAccesoException.PasswordNotUpdatedException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioExistsException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioNotExistsException;
import com.sytecso.service.usuario.ServiceUsuarioAcceso;

@RestController
@RequestMapping(path = "/usuario-acceso/")
public class ControllerUsuarioAcceso {
	@Autowired
	ServiceUsuarioAcceso serviceUsuarioAcceso;
	


	@PutMapping(value = "updateUsuario")
	public ResponseEntity<EventMessage> updateUsuario(@RequestBody @Valid UsuarioAcceso usuario)
			throws PasswordNotUpdatedException, NotUserFoundException, UsuarioNotExistsException {
		serviceUsuarioAcceso.updateUsuario(usuario);
		return new ResponseEntity<>(new EventMessage("El usuario fue actualizado correctamente"), HttpStatus.OK);
	}

	
	@GetMapping(value = "/getEmpleadoAP")
	public ResponseEntity<EmpleadoAPDTO> getEmpleadoAP(@RequestParam(name = "usr") String usr) throws NotRolesFoundException {
		EmpleadoAPDTO empleado = serviceUsuarioAcceso.getEmpleadoAP(usr);
		if (empleado.getIdEmpleado() == 0) {
			throw new RolAccesoException.NotRolesFoundException("No hay empleado registrado");
		}
		return new ResponseEntity<>(empleado, HttpStatus.OK);
	}
	
	@PutMapping(value = "/updateEmpleadoAP")
	public ResponseEntity<EventMessage> updateEmpleadoAP(@RequestBody EmpleadoAPDTO empleado)
			throws UsuarioExistsException, UsuarioNotExistsException, SQLException {
		System.out.println("UPDATING...");
		if (serviceUsuarioAcceso.actualizaEmpleadoAP(empleado)) {
			return new ResponseEntity<>(new EventMessage("Los datos fueron actualizados correctamente"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new EventMessage("Los datos no fueron actualizados, la contraseña actual es incorrecta"),
				HttpStatus.BAD_REQUEST);

	}
	
	@GetMapping(value = "usuarioNombre")
	public ResponseEntity<EventMessage> getusuarioNombrebyUsr(
			@RequestParam(name = "user") String user) throws NotUserFoundException {
		return new ResponseEntity<>(new EventMessage(serviceUsuarioAcceso.getusuarioNombrebyUsr(user)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getReporte")
	public ResponseEntity<String> getReporte(@RequestParam(name = "rfc") String rfc, @RequestParam(name = "anio") String anio, @RequestParam(name = "mes") String mes) throws NotRolesFoundException, IOException {
		String resp = serviceUsuarioAcceso.getReporte(rfc, anio, mes);
		
		if (resp == null) {
			throw new RolAccesoException.NotRolesFoundException("No existe reporte");
		}
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getBusquedaEmpleadosAP")
	public ResponseEntity<?> getBusquedaEmpleadosAP(@RequestParam(
			name = "rfc") String rfc,@RequestParam(name = "nombre") String nombre,@RequestParam(name = "dependencia") String dependencia,
			@RequestParam(name = "unidadAdmin") String unidadAdmin) throws NotRolesFoundException {
		List<EmpleadoAPDTO> empleados = serviceUsuarioAcceso.getBusquedaEmpleadosAP(rfc,nombre,dependencia,unidadAdmin);
		if (empleados.size() == 0) {
			return new ResponseEntity<>(new EventMessage("No existen resultados para la búsqueda intente de nuevo modificando los filtros"),
					HttpStatus.BAD_REQUEST);
			//throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(empleados, HttpStatus.OK);
	}

	@PutMapping(value = "/updateEstatus")
	public ResponseEntity<EventMessage> updateEstatus(@RequestBody EmpleadoAPDTO empleado)
			throws UsuarioExistsException, UsuarioNotExistsException, SQLException {
				
		if (serviceUsuarioAcceso.updateEstatus(empleado)) {
			String mensaje = empleado.getEstatus() == 1 ? "El usuario ha sido habilitado correctamente": "El usuario ha sido deshabilitado correctamente";
			return new ResponseEntity<>(new EventMessage(mensaje), HttpStatus.OK);
		}
		String mensaje = empleado.getEstatus() == 1 ? "El usuario ha sido deshabilitado": "El usuario no ha sido deshabilitado";
		return new ResponseEntity<>(new EventMessage(mensaje),
				HttpStatus.BAD_REQUEST);

	}
	
	@PostMapping(value = "/createAnalistaAP")
	public ResponseEntity<EventMessage> createEmpleadoAP(@RequestBody EmpleadoAPDTO empleado)
			throws UsuarioExistsException, UsuarioNotExistsException, SQLException {
		System.out.println("Entra a crear analista AP ***********************************");
			if (!serviceUsuarioAcceso.usuarioAPExists(empleado.getRfc())) {
				if (serviceUsuarioAcceso.createUsuarioAP(empleado)) {
					return new ResponseEntity<>(new EventMessage("El usuario fue guardado correctamente"), HttpStatus.OK);
				} else {
					return new ResponseEntity<>(new EventMessage("Ocurrio un error al guardar al usuario"),
							HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<>(new EventMessage("El RFC ya ha sido asignado anteriormente, asegurese de escribir bien el RFC o comuniquese con su ejecutivo para obtener mas ayuda"),
						HttpStatus.BAD_REQUEST);
			}

	}
	
	
	@GetMapping(value = "/getEmpleadosExternos")
	public ResponseEntity<List<EmpleadoAPDTO>> getEmpleadosExternos() throws NotRolesFoundException {
		List<EmpleadoAPDTO> empleados = serviceUsuarioAcceso.getEmpleadosExternos();
		if (empleados.size() == 0) {
			throw new RolAccesoException.NotRolesFoundException("No existen resultados");
		}
		return new ResponseEntity<>(empleados, HttpStatus.OK);
	}

}
