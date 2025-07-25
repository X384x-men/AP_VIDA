package com.sytecso.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
import com.sytecso.dao.email.DAOEmail;
import com.sytecso.dao.usuario.DAOUsuarioAcceso;
import com.sytecso.dto.EmailBody;
import com.sytecso.dto.email.EmailDTO;
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
	@Autowired
	private DAOEmail daoEmail;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceEmail.class);


	@Override
	public boolean sendEmail(EmailBody emailBody) throws SQLException  {
		LOGGER.info("EmailBody: {}", emailBody.toString());
		return sendEmailTool(emailBody);
	}
	

	private boolean sendEmailTool(EmailBody emailBody) throws SQLException {
		boolean status= false;
		
		Session session = SessionEmail.sessionEmail();
        String from = SessionEmail.getFromProp();
	        try {
	        	UserDetails userDetails = myUserDetailService.loadUserByUsername(emailBody.getRfc());
	        	System.out.println("userDetails: "
						+ ReflectionToStringBuilder.toString(userDetails, ToStringStyle.JSON_STYLE));
	        	 String token = jwtService.generateToken(userDetails);
	        	 
	        	 EmpleadoAPDTO empleado= serviceUsuario.getEmpleadoAPbyRFC(emailBody.getRfc());
	        	
	        	 
	        	String url=context.getServer()+"/"+context.getApp_Name()+"/angular/changePswd?code="+token;
	        	//String url="https://www.apvida-consultas.mx/"+context.getApp_Name()+"/angular/changePswd?code="+token; // PROD
	        	
	            Message message = new MimeMessage(session);
	            message.setFrom(new InternetAddress(from));
	            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(emailBody.getEmail()));
	            message.setSubject("Restablecer Contraseña AP");
	            message.setContent(TemplateEmail.template(empleado.getNombre(),url), "text/html");
	            Transport.send(message);
	            status=true;
	            EmailDTO email= new EmailDTO();
	            email.setRfc(emailBody.getRfc());
	            email.setCorreo(emailBody.getEmail());
	            email.setNombre(empleado.getNombre());
	            email.setStatus(true);
	            email.setNumerosRegistro("");
	            email.setTipo("Restablecer Contraseña AP");
	            String currentTimestamp = LocalDateTime.now().format((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	            email.setFechaExito(currentTimestamp);
	            daoEmail.creacionEnvioEmail(email);
	            

	        } catch (MessagingException e) {
	            e.printStackTrace();
	            EmpleadoAPDTO empleado= serviceUsuario.getEmpleadoAPbyRFC(emailBody.getRfc());
	            EmailDTO email= new EmailDTO();
	            email.setRfc(emailBody.getRfc());
	            email.setCorreo(emailBody.getEmail());
	            email.setNombre(empleado.getNombre());
	            email.setStatus(false);
	            email.setNumerosRegistro("");
	            email.setTipo("Restablecer Contraseña AP");
	            email.setFechaExito("");
	            daoEmail.creacionEnvioEmail(email);
	        }
	        
			return status;
	    }


	public boolean reset_password(String token,  String password) throws PasswordNotUpdatedException, UsuarioNotExistsException, SQLException {
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


	
	
	private boolean envioCorreoExito(String usuario) throws SQLException {
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
            EmailDTO email= new EmailDTO();
            email.setRfc(empleado.getRfc());
            email.setCorreo(empleado.getMail());
            email.setNombre(empleado.getNombre());
            email.setStatus(true);
            email.setNumerosRegistro("");
            email.setTipo("Cambio de Contraseña AP");
            String currentTimestamp = LocalDateTime.now().format((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            email.setFechaExito(currentTimestamp);
            daoEmail.creacionEnvioEmail(email);
            
        } catch (MessagingException e) {
            e.printStackTrace();
            EmpleadoAPDTO empleado= serviceUsuario.getEmpleadoAPbyRFC(usuario);
            EmailDTO email= new EmailDTO();
            email.setRfc(empleado.getRfc());
            email.setCorreo(empleado.getMail());
            email.setNombre(empleado.getNombre());
            email.setStatus(false);
            email.setNumerosRegistro("");
            email.setTipo("Cambio de Contraseña AP");
            email.setFechaExito("");
            daoEmail.creacionEnvioEmail(email);
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


	@Override
	public List<EmailDTO> getEmailList(EmailDTO email,int valor) throws SQLException {
		String parametros="";
		int contador=0;
		if((email.getCorreo()!=null)&&(!email.getCorreo().equals(""))) {
			parametros=parametros+" where  correo like '%"+email.getCorreo()+"%' ";
			contador ++;
		}
		if((email.getTipo()!=null)&&(!email.getTipo().equals(""))) {
			
			if(contador>0)
				parametros=parametros+" and  tipo like '%"+email.getTipo()+"%' ";
			else
				parametros=parametros+" where tipo like '%"+email.getTipo()+"%' ";
			contador ++;
		}
		if((email.getFechaEmail()!=null)&&(!email.getFechaEmail().equals(""))) {
			if(contador>0)
				parametros=parametros+" and fechaCorreo like '%"+email.getFechaEmail()+"%' ";
			else
				parametros=parametros+" where fechaCorreo like '%"+email.getFechaEmail()+"%' ";
			contador ++;
		}
		if(valor>0) {
			if(email.isStatus()) {
				if(contador>0)	
					parametros=parametros+" and estatus=conv(1,10,2) ";
				else
					parametros=parametros+" where estatus=conv(1,10,2) ";
			}
			else {
				if(contador>0)	
					parametros=parametros+" and estatus=conv(0,10,2) ";
				else
					parametros=parametros+" where estatus=conv(0,10,2) ";
				
			}
			contador ++;
		}
		return daoEmail.getEmailList(parametros);
	}
	
	@Override
	public boolean reenvioCorreo(EmailDTO email) throws SQLException {
		boolean status=false;
		Session session = SessionEmail.sessionEmail();
        String from = SessionEmail.getFromProp();

        try {
        	LocalDateTime myDateObj = LocalDateTime.now();
        	DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        	String fecha = myDateObj.format(myFormatObj);
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email.getCorreo()));
            message.setSubject(email.getTipo());
            switch (email.getTipo()) {
            	case "Restablecer Contraseña AP":
            		UserDetails userDetails = myUserDetailService.loadUserByUsername(email.getRfc());
    	        	System.out.println("userDetails: "
    						+ ReflectionToStringBuilder.toString(userDetails, ToStringStyle.JSON_STYLE));
    	        	String token = jwtService.generateToken(userDetails);
            		String url=context.getServer()+"/"+context.getApp_Name()+"/angular/changePswd?code="+token;
    	        	//String url="https://www.apvida-consultas.mx/"+context.getApp_Name()+"/angular/changePswd?code="+token; // PROD
     	            message.setContent(TemplateEmail.template(email.getNombre(),url), "text/html");
            		break;
            	case "Cambio de Contraseña AP":
                    message.setContent(TemplateEmail.templateCambioContraseñaExito(email.getNombre(), fecha), "text/html");
            		break;
            	case "Nueva solicitud de aclaración":
                    message.setContent(TemplateEmail.templateCreacionAclaraciones(email.getNombre(), fecha, "Creación Aclaracion", email.getIdAclaracion()+""), "text/html");
                    break;
            	case "Nueva Solicitud":
                    message.setContent(TemplateEmail.templateCreacionSolicitudes(email.getNombre(), fecha, email.getTipo(), email.getNumerosRegistro()+""), "text/html");
            		break;   
            }
            Transport.send(message);
            status=true;
            email.setStatus(true);
            String currentTimestamp = LocalDateTime.now().format((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            email.setFechaExito(currentTimestamp);
            daoEmail.updateEnvioEmail(email);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		return status;
	}
	
	
	 
}