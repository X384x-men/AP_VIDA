package com.sytecso.service.aclaraciones.impl;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.component.utility.SessionEmail;
import com.sytecso.component.utility.TemplateEmail;
import com.sytecso.dao.aclaraciones.DAOAclaraciones;
import com.sytecso.dao.email.DAOEmail;
import com.sytecso.dto.AclaracionDTO;
import com.sytecso.dto.CatalogoDocumentoDTO;
import com.sytecso.dto.TipoAclaracionDTO;
import com.sytecso.dto.email.EmailDTO;
import com.sytecso.service.aclaraciones.ServiceAclaraciones;

@Service
public class ServiceAclaracionesImpl implements ServiceAclaraciones {
	
	@Autowired
	private DAOAclaraciones daoAclaraciones;
	@Autowired
	private DAOEmail daoEmail;

	@Override
	public long crearAclaracion(AclaracionDTO aclaracion) throws SQLException {

		long idAclaracion=daoAclaraciones.crearAclaracion(aclaracion);
		try {
			envioCorreoNuevaAclaracion( aclaracion,idAclaracion);
			
			EmailDTO email= new EmailDTO();
            email.setRfc(aclaracion.getRfc());
            email.setCorreo(aclaracion.getEmailAclaracion());
            email.setNombre(aclaracion.getNombreAclaracion());
            email.setStatus(true);
            email.setNumerosRegistro("");
            email.setTipo("Nueva solicitud de aclaración");
            String currentTimestamp = LocalDateTime.now().format((DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            email.setFechaExito(currentTimestamp);
            email.setIdAclaracion(idAclaracion);
            daoEmail.creacionEnvioEmail(email);
		}catch(Exception e) {
			e.printStackTrace();
			EmailDTO email= new EmailDTO();
            email.setRfc(aclaracion.getRfc());
            email.setCorreo(aclaracion.getEmailAclaracion());
            email.setNombre(aclaracion.getNombreAclaracion());
            email.setStatus(false);
            email.setNumerosRegistro("");
            email.setTipo("Nueva solicitud de aclaración");
            email.setFechaExito("");
            email.setIdAclaracion(idAclaracion);
            daoEmail.creacionEnvioEmail(email);
			
		}
		
		return idAclaracion;
	}

	@Override
	public boolean insertCatalogoDocumento(String tipoDocumento) throws SQLException {
		return daoAclaraciones.insertCatalogoDocumento(tipoDocumento);
	}

	@Override
	public boolean insertCatalogoTipoAclaracion(String tipoAclaracion, String descripcion) throws SQLException {
		return daoAclaraciones.insertCatalogoTipoAclaracion(tipoAclaracion, descripcion);
	}

	@Override
	public List<AclaracionDTO> getAclaraciones(String rfc, String nombre, String dependencia,
			String fechaRegistroPortal, String telefono, String email, int aclaracionEmpleados ) {
		String filtros= "";
		String filtros2="";
		if(!rfc.equals("")) {
			filtros=filtros+" and eap.rfc like '%"+rfc+"%' ";
			filtros2=filtros2+" and ac.rfcAclaracion like '%"+rfc+"%' ";
		}
		if(!fechaRegistroPortal.equals("")) 
			filtros=filtros+"  and eap.fechaCreacion='"+fechaRegistroPortal+"' " ;
		if(!nombre.equals("")) {
			filtros=filtros+"   and concat( eap.nombre, ' ', eap.apellidoP, ' ' ,eap.apellidoM)  like '%"+nombre+"%'" ;
			filtros2=filtros2+" and ac.nombreAclaracion like '%"+nombre+"%' ";
		}
		if(!telefono.equals(""))
			filtros=filtros+"  and ( eap.telefonoCasa='"+telefono+"' or eap.telefonoMovil='"+telefono+"') ";
		if(!email.equals("")) {
			filtros=filtros+" and eap.email='"+email+"' ";
			filtros2=filtros2+"and ac.emailAclaracion='"+email+"' ";
		}
		if(!dependencia.equals(""))
			filtros=filtros+ "and catdep.Descripcion like '%"+dependencia+"%' ";
		return daoAclaraciones.getAclaraciones(filtros,aclaracionEmpleados,filtros2);
	}

	@Override
	public boolean updateAclaracionStatus(long idAclaracion, int status) throws SQLException {
		return daoAclaraciones.updateAclaracionStatus(idAclaracion, status);
	}

	@Override
	public boolean updateTipoAclaracionesCatalogo(long idTipoDesc, String desc) throws SQLException {
		return daoAclaraciones.updateTipoAclaracionesCatalogo(idTipoDesc, desc);
	}

	@Override
	public boolean updateCatalogoDocumentos(long idTipoDocumento, String desc) throws SQLException {
		return daoAclaraciones.updateCatalogoDocumentos(idTipoDocumento, desc);
	}

	@Override
	public List<TipoAclaracionDTO> getTipoAclaracion() {
		return daoAclaraciones.getTipoAclaracion();
	}

	@Override
	public List<CatalogoDocumentoDTO> getCatalogoDocumento() {
		return daoAclaraciones.getCatalogoDocumento();
	}

	@Override
	public boolean updateAclaracion(AclaracionDTO aclaracion) throws SQLException {
		return daoAclaraciones.updateAclaracion(aclaracion);
	}

	@Override
	public AclaracionDTO getAclaracion(long id,int funcionamiento) {
		return daoAclaraciones.getAclaracion(id,funcionamiento) ;
	}

	@Override
	public boolean updateAclaracionEmpleado(AclaracionDTO aclaracion) throws SQLException {
		return daoAclaraciones.updateAclaracionEmpleado(aclaracion);
	}

	@Override
	public boolean updateAclaracionDocumento(AclaracionDTO aclaracion) throws SQLException {
		return daoAclaraciones.updateAclaracionDocumento(aclaracion);
	}
	
	private boolean envioCorreoNuevaAclaracion(AclaracionDTO aclaracion, long idAclaracion) {
		boolean status=false;
		Session session = SessionEmail.sessionEmail();
        String from = SessionEmail.getFromProp();

        try {
        	 LocalDateTime myDateObj = LocalDateTime.now();
        	 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        	    String fecha = myDateObj.format(myFormatObj);
        	    
        	String nombre = aclaracion.getNombre();
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            if(!aclaracion.isCategoriaAclaracion())
            	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(aclaracion.getEmail()));
            else
            	message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(aclaracion.getEmailAclaracion()));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse("recepcion.gem@apvida.mx"));
            message.setSubject("Nueva solicitud de aclaración");
            message.setContent(TemplateEmail.templateCreacionAclaraciones(nombre, fecha, "Creación Aclaracion", idAclaracion+""), "text/html");
            Transport.send(message);
            status=true;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
		return status;
	}

}
