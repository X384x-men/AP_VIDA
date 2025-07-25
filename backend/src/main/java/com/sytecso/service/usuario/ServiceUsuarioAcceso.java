package com.sytecso.service.usuario;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.validation.Valid;


import com.sytecso.dto.usuarioacceso.UsuarioAcceso;
import com.sytecso.dto.EmailBody;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.dto.usuarioacceso.UsuarioAccesoDTO;
import com.sytecso.component.exceptions.CuadrillasException.NotUserFoundException;
import com.sytecso.component.exceptions.UsuarioAccesoException.PasswordNotUpdatedException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioExistsException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioNotExistsException;

public interface ServiceUsuarioAcceso {

	public boolean usuarioExists(String usuario);

	public boolean usuarioExists(String usuario, String profile);

	public UsuarioAccesoDTO findUserByUserNameAndPassword(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException;

	public boolean updatePasswordByUserName(UsuarioAccesoDTO usuario, String cuadrilla, Integer option)
			throws PasswordNotUpdatedException, NotUserFoundException, UsuarioNotExistsException;

	public boolean update(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException, PasswordNotUpdatedException;

	public UsuarioAccesoDTO findUserByUserName(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException;

	public UsuarioAccesoDTO findUser(Integer param, UsuarioAccesoDTO usuario) throws UsuarioNotExistsException;

	public List<RolAccesoDTO> getPerfiles();

	public boolean updateUsuario(@Valid UsuarioAcceso usuario);

	public boolean createUsuarioAP(@Valid EmpleadoAPDTO empleado) throws UsuarioExistsException, SQLException;
	
	public boolean updateUsuarioAP(@Valid EmpleadoAPDTO empleado) throws SQLException;

	public boolean updateEmpleado(@Valid EmpleadoAPDTO empleado) throws SQLException;
	
	public EmpleadoAPDTO getEmpleadoAP(String usr);

	public boolean usuarioAPExists(String rfc);

	public boolean actualizaEmpleadoAP(EmpleadoAPDTO empleado) throws UsuarioExistsException, SQLException;

	public String getusuarioNombrebyUsr(String user);

	public EmpleadoAPDTO getEmpleadoAPbyRFC(String rfc);

	public EmpleadoAPDTO getEmpleadoAPbyeIdUsuario(String idUsuario);

	public String getEmail(String rfc);

	public boolean updateEmailbyRFC(EmailBody email);
	
	public String getReporte(String rfc, String anio, String mes) throws IOException;

	public List<EmpleadoAPDTO> getBusquedaEmpleadosAP(String rfc, String nombre, String dependencia,
			String unidadAdmin); 

	public Long createUsuarioAcceso(com.sytecso.model.UsuarioAcceso usuarioAcceso) throws SQLException;
	
	public boolean updateEstatus(EmpleadoAPDTO empleado) throws UsuarioExistsException, SQLException;
	
	public List<EmpleadoAPDTO> getEmpleadosExternos();
	
	public boolean getUserExist(String rfc) throws SQLException;
	
	
}
