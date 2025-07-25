package com.sytecso.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sytecso.component.CustomContext;
import com.sytecso.component.exceptions.UsuarioAccesoException.PasswordNotUpdatedException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioNotExistsException;
import com.sytecso.component.utility.EmailPort;
import com.sytecso.component.utility.SessionEmail;
import com.sytecso.component.utility.TemplateEmail;
import com.sytecso.dao.usuario.DAOUsuarioAcceso;
import com.sytecso.dto.EmailBody;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.dto.usuarioacceso.UsuarioAccesoDTO;
import com.sytecso.security.service.CustomUserDetailsService;
import com.sytecso.security.service.JwtService;
import com.sytecso.service.usuario.ServiceUsuarioAcceso;

import io.jsonwebtoken.ExpiredJwtException;

@Service
public class ServiceEmail implements EmailPort{
	
	@Autowired
	JwtService jwtService;
	@Autowired
	CustomContext context;
	@Autowired
	ServiceUsuarioAcceso serviceUsuario;
	@Autowired
	private DAOUsuarioAcceso daoUsuarioAcceso;
	@Autowired
	private CustomUserDetailsService myUserDetailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEmail.class);


	@Override
	public boolean sendEmail(EmailBody emailBody)  {
		LOGGER.info("EmailBody: {}", emailBody.toString());
		return sendEmailTool(emailBody);
	}
	

	private boolean sendEmailTool(EmailBody emailBody) {
		boolean status= false;
		
		Session session = SessionEmail.sessionEmail();
        String from = SessionEmail.getFromProp();
	        try {
	        	UserDetails userDetails = myUserDetailService.loadUserByUsername(emailBody.getRfc());
	        	System.out.println("userDetails: "
						+ ReflectionToStringBuilder.toString(userDetails, ToStringStyle.JSON_STYLE));
	        	 String token = jwtService.generateToken(userDetails);
	        	 
	        	 EmpleadoAPDTO empleado= serviceUsuario.getEmpleadoAPbyRFC(emailBody.getRfc());
	        	
	        	 
	        	//String url=context.getServer()+"/"+context.getApp_Name()+"/angular/changePswd?code="+token;
	        	String url="https://www.apvida-consultas.mx/"+context.getApp_Name()+"/angular/changePswd?code="+token; // PROD
	        	
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(from));
	            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailBody.getEmail()));
	            message.setSubject("Restablecer Contraseña AP");
	            message.setContent(TemplateEmail.template(empleado.getNombre(),url), "text/html");
	            Transport.send(message);
	            status=true;

	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
			return status;
	    }


	public boolean reset_password(String token,  String password) throws PasswordNotUpdatedException, UsuarioNotExistsException {
		boolean status=false;
		String username=null;
		
			try {
				username = jwtService.getUsernameFromToken(token);
			} catch (IllegalArgumentException e) {
				System.err.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.err.println("JWT Token ha expirado");
				SecurityContextHolder.clearContext();
			}
		
		System.out.println(username);
		if (username != null ) {
			UserDetails userDetails = this.myUserDetailService.loadUserByUsername(username);
			
			if (jwtService.validateToken(token, userDetails)) {
				UsuarioAccesoDTO usuario= new UsuarioAccesoDTO();
				usuario.setUsuario(username);
				usuario.setPassword(password);
				if(daoUsuarioAcceso.updatePasswordByUserName(usuario)) {
					status=envioCorreoExito(username);
				}
			}

		}
		return status;
	}


	
	
	private boolean envioCorreoExito(String usuario) {
		boolean status=false;
		Session session = SessionEmail.sessionEmail();
        String from = SessionEmail.getFromProp();

        try {
        	
        	 EmpleadoAPDTO empleado= serviceUsuario.getEmpleadoAPbyRFC(usuario);
        	 LocalDateTime myDateObj = LocalDateTime.now();
        	 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        	    String fecha = myDateObj.format(myFormatObj);
        	
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(empleado.getMail()));
            message.setSubject("Cambio de Contraseña AP");
            message.setContent(TemplateEmail.templateCambioContraseñaExito(empleado.getNombre(), fecha), "text/html");
            Transport.send(message);
            status=true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		return status;
	}


	public boolean emailExits(EmailBody email) {
		return daoUsuarioAcceso.emailExits(email);

	}


	public boolean sendEmailUpdate(String nombreCompleo, String mail) {
		boolean status=false;
		
	        Session session = SessionEmail.sessionEmail();
	        String from = SessionEmail.getFromProp();
        try {
        	
        	
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mail));
            message.setSubject("Actualización de Datos en apvida.mx");
            message.setContent(TemplateEmail.templateUpdateDatos(nombreCompleo), "text/html");
            Transport.send(message);
            status=true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		return status;
	}
	
	
	 
}