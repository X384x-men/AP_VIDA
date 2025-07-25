package com.sytecso.component.utility;

import java.sql.SQLException;
import java.util.List;

import com.sytecso.dto.EmailBody;
import com.sytecso.dto.email.EmailDTO;

public interface EmailPort {
	public boolean sendEmail(EmailBody emailBody) throws SQLException;
	public List<EmailDTO> getEmailList(EmailDTO email,int valor) throws SQLException;
	boolean reenvioCorreo(EmailDTO email) throws SQLException;
}