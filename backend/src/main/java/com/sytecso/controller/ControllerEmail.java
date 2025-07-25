package com.sytecso.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sytecso.component.EventMessage;
import com.sytecso.component.exceptions.UsuarioAccesoException.PasswordNotUpdatedException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioNotExistsException;
import com.sytecso.dto.EmailBody;
import com.sytecso.service.ServiceEmail;
import com.sytecso.service.usuario.ServiceUsuarioAcceso;

@Controller
@RequestMapping(path = "/email/")
public class ControllerEmail {

	
	@Autowired
	ServiceEmail serviceMail;
	@Autowired
	ServiceUsuarioAcceso serviceUsuario;
	
	
	@PostMapping(value = "/sendEmail")
	public ResponseEntity<EventMessage> sendEmail(@RequestBody  EmailBody email) {
		System.out.println(email);
		if (serviceUsuario.usuarioAPExists(email.getRfc())) {
			String mail = serviceUsuario.getEmail(email.getRfc());
			boolean status = false;
			if (mail != null) {
				if (mail.equals(email.getEmail())) {
					status = true;
				} else {
					return new ResponseEntity<>(new EventMessage("El e-mail no es el mismo del sistema"),
							HttpStatus.OK);
				}
			} else {
				status = serviceUsuario.updateEmailbyRFC(email);
			}
			if (status) {
				if (serviceMail.sendEmail(email)) {
					return new ResponseEntity<>(new EventMessage(
							"En breve recibirá un correo electrónico con las instrucciones para reestablecer la contraseña"),
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(new EventMessage("Error en el Envio del correo electronico "), HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<>(new EventMessage("Error la direccion de correo  no es la mismo"), HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(new EventMessage("El RFC no existe en el sistema"), HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/reset_password")
	public ResponseEntity<EventMessage> reset_password(@RequestParam(name = "code") String token,@RequestParam(name = "pw") String password) 
			throws PasswordNotUpdatedException, UsuarioNotExistsException {
		
			if (serviceMail.reset_password(token,  password)) {
					return new ResponseEntity<>(new EventMessage("Se restablecio la contraseña con exito"), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new EventMessage("Error"),
						HttpStatus.BAD_REQUEST);
			}

	}
}
