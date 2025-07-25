package com.sytecso.dao.email;

import java.sql.SQLException;
import java.util.List;

import com.sytecso.dto.email.EmailDTO;

public interface DAOEmail {
	public boolean creacionEnvioEmail(EmailDTO email) throws SQLException;
	public boolean updateEnvioEmail(EmailDTO email) throws SQLException;
	public List<EmailDTO> getEmailList(String parametros) throws SQLException;
}
