package com.sytecso.controller;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.sytecso.dto.email.EmailDTO;
import com.sytecso.service.ServiceEmail;
import com.sytecso.service.usuario.ServiceUsuarioAcceso;

@Controller
@RequestMapping(path = "/email/")
public class ControllerEmail {

	
	@Autowired
	private ServiceEmail serviceMail;
	@Autowired
	private ServiceUsuarioAcceso serviceUsuario;
	
	
	@PostMapping(value = "/sendEmail")
	public ResponseEntity<EventMessage> sendEmail(@RequestBody  EmailBody email) throws SQLException {
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
			throws PasswordNotUpdatedException, UsuarioNotExistsException, SQLException {
		
			if (serviceMail.reset_password(token,  password)) {
					return new ResponseEntity<>(new EventMessage("Se restablecio la contraseña con exito"), HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new EventMessage("Error"),
						HttpStatus.BAD_REQUEST);
			}

	}
	
	@GetMapping(value = "/getEmailList")
	public ResponseEntity<?> getEmailList(@RequestParam(name = "numeroRegistro", required = false)String numeroRegistro,
			@RequestParam(name = "correo", required = false)String correo,
			@RequestParam(name = "tipo", required = false)String tipo,
			@RequestParam(name = "fechaCorreo", required = false)String fechaCorreo,
			@RequestParam(name = "status", required = false)int  status) 
			throws PasswordNotUpdatedException, UsuarioNotExistsException, SQLException {
			EmailDTO email = new EmailDTO();
			email.setCorreo(correo);
			email.setTipo(tipo);
			email.setFechaEmail(fechaCorreo);
			int statusValue=0;
			if(status>0) {
				statusValue=1;
				if(status==1)
					email.setStatus(true);
				if(status==2)
					email.setStatus(false);
			}
			List<EmailDTO> emailList = new ArrayList<EmailDTO>();
			emailList=serviceMail.getEmailList(email,statusValue);
			if (!emailList.isEmpty()) {
				return new ResponseEntity<>(emailList, HttpStatus.OK);
			}else {
				return new ResponseEntity<>(new EventMessage("Error"),HttpStatus.BAD_REQUEST);
			}

	}
	
	@PostMapping(value="/reenvioCorreo")
	public ResponseEntity<?> reenvioCorreo(@RequestBody EmailDTO email) throws SQLException{
		boolean estatus=serviceMail.reenvioCorreo(email);
		if(estatus) 
			return new ResponseEntity<>(new EventMessage("Envío exitoso"), HttpStatus.OK);
		else
			return new ResponseEntity<>(new EventMessage("Envío fallido"), HttpStatus.BAD_REQUEST);		
	}
}
