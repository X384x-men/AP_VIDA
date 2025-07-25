package com.sytecso.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sytecso.component.utility.UtileriaAcceso;
import com.sytecso.dto.modulosgui.Menu;
import com.sytecso.service.modulos.ServiceModulosGui;

@RestController
@RequestMapping(path = "acceso")
public class ControllerUsuario extends UtileriaAcceso {
	@Autowired
	private ServiceModulosGui serviceModulosGui;


	@GetMapping(path = "menu")
	public ResponseEntity<List<Menu>> getMenu() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			List<Menu> menu = this.serviceModulosGui.getAllByRolAcceso(context.getAuthentication().getAuthorities());
			if (!menu.isEmpty()) {
				return new ResponseEntity<>(menu, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
	}
	
	
	
	

	@GetMapping(path = "detail")
	public ResponseEntity<List<String>> getRolUser() {
		SecurityContext context = SecurityContextHolder.getContext();
			List<String> rol = this.getRol(context);
			if (!rol.isEmpty()) {
				return new ResponseEntity<>(rol, HttpStatus.OK);
			}
		
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
	}
}
