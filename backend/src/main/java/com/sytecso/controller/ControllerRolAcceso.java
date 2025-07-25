package com.sytecso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import com.sytecso.dto.modulosgui.ModuloDTO;
import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.component.exceptions.MenuException.SeccionNotCreatedException;
import com.sytecso.component.exceptions.MenuException.SeccionNotExistsException;
import com.sytecso.component.exceptions.MenuException.SeccionNotRemovedException;
import com.sytecso.component.exceptions.RolAccesoException;
import com.sytecso.component.exceptions.RolAccesoException.NotRolesFoundException;
import com.sytecso.component.exceptions.RolAccesoException.RolCannotNotAsocciateViewException;
import com.sytecso.component.exceptions.RolAccesoException.RolExistsException;
import com.sytecso.component.exceptions.RolAccesoException.RolNotCreatedException;
import com.sytecso.component.exceptions.RolAccesoException.RolNotExistsException;
import com.sytecso.component.exceptions.RolAccesoException.RolNotUpdatedException;
import com.sytecso.model.RolAcceso;
import com.sytecso.security.profile.Profile;
import com.sytecso.service.modulos.ServiceModulosGuiHasRolAcceso;
import com.sytecso.service.rolAcceso.ServiceRolAcceso;
import com.sytecso.service.seccion.ServiceSeccion;
import com.sytecso.service.seccion.ServiceSeccionHasRolesAcceso;

@RestController
@RequestMapping("/rol/")
public class ControllerRolAcceso {
	@Autowired
	private ServiceRolAcceso serviceRolAcceso;
	@Autowired
	private ServiceSeccion serviceSeccion;
	@Autowired
	private ServiceModulosGuiHasRolAcceso serviceModulosGuiHasRolAcceso;
	@Autowired private ServiceSeccionHasRolesAcceso serviceSeccionHasRolAcceso;
	private @Autowired @Qualifier("admin") Profile admin;

	@GetMapping("/find-all")
	public ResponseEntity<List<RolAccesoDTO>> findAll() throws NotRolesFoundException {
		List<RolAccesoDTO> rolesAcceso = this.serviceRolAcceso.findAll();
		if (!rolesAcceso.isEmpty())
			return new ResponseEntity<>(rolesAcceso, HttpStatus.OK);
		throw new RolAccesoException.NotRolesFoundException("No se han encontrado roles");

	}

	@GetMapping("/info")
	public ResponseEntity<RolAccesoDTO> getInfo() {
		RolAccesoDTO rol = this.serviceRolAcceso.getInfo();
		if (rol == null)
			return new ResponseEntity<>(rol, HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(rol, HttpStatus.OK);
	}

	@PostMapping("/find")
	public ResponseEntity<RolAccesoDTO> getFind(@RequestBody RolAccesoDTO rolAcceso) throws RolNotExistsException {
		RolAcceso acceso = this.serviceRolAcceso.findByRol(rolAcceso.getNombre());
		return new ResponseEntity<>(new RolAccesoDTO(acceso.getNombre(), acceso.getDescripcion()), HttpStatus.OK);
	}

	@PostMapping("/add")
	public ResponseEntity<Void> add(@RequestBody RolAccesoDTO rol) throws RolNotCreatedException, RolExistsException {
		if (this.serviceRolAcceso.createRolIfnotExists(rol) != null)
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		throw new RolAccesoException.RolNotCreatedException("Ocurrio un error al crear el rol".concat(rol.getNombre()));
	}

	@PutMapping("/update")
	public ResponseEntity<Void> update(@RequestParam(name = "value") String value, @RequestBody RolAccesoDTO rol)
			throws RolNotUpdatedException {
		if (this.serviceRolAcceso.update(rol, value))
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		throw new RolAccesoException.RolNotUpdatedException(
				"El rol: ".concat(value).concat(" no pudo ser actualizado"));
	}

	@PostMapping("/seccion")
	public ResponseEntity<List<ModuloDTO>> getSeccionByRol(@RequestBody SeccionRolDTO seccionRolDTO,
			@RequestParam(name = "value") int value) throws NotRolesFoundException {
		List<ModuloDTO> secciones = this.serviceSeccion.getSeccionByRol(seccionRolDTO, value);
		if (!secciones.isEmpty() ) {
			return new ResponseEntity<>(secciones, HttpStatus.OK);
		}
		
		if(value == 1 || value == 4) {
			final String errorMessage = ", no tiene mas secciones disponibles para asignar";
			throw new RolAccesoException.NotRolesFoundException(
					"El rol: ".concat(seccionRolDTO.getRol()).concat(errorMessage));
		}else {
			return new ResponseEntity<>(secciones, HttpStatus.OK);
			//", no tiene secciones asignadas"
		}
		
	}
	
	@PostMapping("/modulo/alta")
	public ResponseEntity<EventMessage> postCreateModuloRol(@RequestBody SeccionRolDTO seccionRolDTO)
			throws RolCannotNotAsocciateViewException {
		if (admin.getViews().stream().anyMatch(r -> r.equalsIgnoreCase(seccionRolDTO.getSeccion())))
			throw new RolAccesoException.RolCannotNotAsocciateViewException(
					"La seccion ".concat(seccionRolDTO.getSeccion()).concat(" no puede ser asignada a otro rol"));
		if (this.serviceModulosGuiHasRolAcceso.create(seccionRolDTO))
			return new ResponseEntity<>(
					new EventMessage("La seccion fue asignada correctamente al rol: ".concat(seccionRolDTO.getRol())),
					HttpStatus.OK);
		throw new RolAccesoException.RolCannotNotAsocciateViewException("Ocurrio un erro al asociar el modulo: "
				.concat(seccionRolDTO.getSeccion()).concat(" al rol: ").concat(seccionRolDTO.getRol()));
	}

	@PutMapping("/modulo/remove")
	public ResponseEntity<EventMessage> putRemoveModuloRol(@RequestBody SeccionRolDTO seccionRolDTO)
			throws RolCannotNotAsocciateViewException {
		if (admin.getViews().stream().anyMatch(r -> r.equalsIgnoreCase(seccionRolDTO.getSeccion())))
			throw new RolAccesoException.RolCannotNotAsocciateViewException(
					"La seccion ".concat(seccionRolDTO.getSeccion()).concat(" no puede ser asignada a otro rol"));
		if (this.serviceModulosGuiHasRolAcceso.remove(seccionRolDTO))
			return new ResponseEntity<>(
					new EventMessage("El modulo fue removido correctamente del rol: ".concat(seccionRolDTO.getRol())),
					HttpStatus.OK);
		throw new RolAccesoException.RolCannotNotAsocciateViewException("Ocurrio un erro al remover el modulo: "
				.concat(seccionRolDTO.getSeccion()).concat(" del rol: ").concat(seccionRolDTO.getRol()));
	}

	@PostMapping("/seccion/alta")
	public ResponseEntity<EventMessage> postCreateSeccionRol(@RequestBody SeccionRolDTO seccionRolDTO)
			throws RolCannotNotAsocciateViewException, SeccionNotExistsException, RolNotExistsException, SeccionNotCreatedException {
		if (admin.getViews().stream().anyMatch(r -> r.equalsIgnoreCase(seccionRolDTO.getSeccion())))
			throw new RolAccesoException.RolCannotNotAsocciateViewException(
					"La seccion ".concat(seccionRolDTO.getSeccion()).concat(" no puede ser asignada a otro rol"));
		this.serviceSeccionHasRolAcceso.create(seccionRolDTO);
		return new ResponseEntity<>(
				new EventMessage("La seccion fue asignada correctamente al rol: ".concat(seccionRolDTO.getRol())),
				HttpStatus.OK);
	}

	@PutMapping("/seccion/remove")
	public ResponseEntity<EventMessage> putRemoveSeccion(@RequestBody SeccionRolDTO seccionRolDTO)
			throws RolCannotNotAsocciateViewException, SeccionNotExistsException, RolNotExistsException, SeccionNotRemovedException {
		if (admin.getViews().stream().anyMatch(r -> r.equalsIgnoreCase(seccionRolDTO.getSeccion()))
				&& admin.getRole().equalsIgnoreCase(seccionRolDTO.getRol()))
			throw new RolAccesoException.RolCannotNotAsocciateViewException(
					"La seccion ".concat(seccionRolDTO.getSeccion()).concat(" no puede ser removida a del rol ")
							.concat(seccionRolDTO.getRol()));
		this.serviceSeccionHasRolAcceso.remove(seccionRolDTO);
		return new ResponseEntity<>(
				new EventMessage("La seccion fue removida correctamente del rol: ".concat(seccionRolDTO.getRol())),
				HttpStatus.OK);
	}
}
