package com.sytecso.dao.usuario;

import java.sql.SQLException;
import java.util.List;

import com.sytecso.dto.EmailBody;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.dto.usuarioacceso.UsuarioAcceso;
import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.dto.usuarioacceso.UsuarioAccesoDTO;
import com.sytecso.component.exceptions.CuadrillasException.NotUserFoundException;
import com.sytecso.component.exceptions.UsuarioAccesoException.PasswordNotUpdatedException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioNotExistsException;
 import java.sql.Connection;

public interface DAOUsuarioAcceso {

	public boolean usuarioExists(String usuario);

	public boolean usuarioExists(String usuario, String profile);

	public UsuarioAccesoDTO findUserByUserNameAndPassword(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException;

	public UsuarioAccesoDTO findUserByUserName(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException;

	public boolean updatePasswordByUserName(UsuarioAccesoDTO usuario)
			throws PasswordNotUpdatedException, UsuarioNotExistsException;

	public List<UsuarioAccesoDTO> findUsuariosAccesoByTypeUser(int userType) throws NotUserFoundException;

	public UsuarioAccesoDTO findUserByUserName(String user) throws UsuarioNotExistsException;

	public List<RolAccesoDTO> getPerfiles();

	public RolAccesoDTO getRolbyID(long idRolesAcceso);

	public boolean updatePasswordByUserNameEmpleado(UsuarioAcceso empleadoUsuario);


	public long findidUsuarioAccesobyidUsuario(long idUsuario);

	public boolean saveEmpleadoUsuarioAP(EmpleadoAPDTO empleado);

	public boolean updateEmpleadoUsuarioAP(EmpleadoAPDTO empleado);

	public boolean updateEmpleadoData(EmpleadoAPDTO empleado);

	public EmpleadoAPDTO getUsuarioAP(String rfc);

	public EmpleadoAPDTO getEmpleadoAP(String usr);
	
	public EmpleadoAPDTO getEmpleadoAPById(long idEmpleado);

	public boolean updateEmpleadoAP(EmpleadoAPDTO empleado);

	public String nombreUsuariobyUser(String user);

	public boolean updatePwdEmpleadoAP(EmpleadoAPDTO empleado);
	
	public EmpleadoAPDTO getPwdEmpleadoAP(String nameUsuario);

	public EmpleadoAPDTO getEmpleadoAPbyRFC(String rfc);

	public boolean emailExits(EmailBody email);

	public String getEmail(String rfc);

	public boolean updateEmailbyRFC(EmailBody emailBody);

	public List<EmpleadoAPDTO> getBusquedaEmpleadosAP(String condicion);

	public Long createUsuarioAcceso(com.sytecso.model.UsuarioAcceso usuarioAcceso) throws SQLException;
	
	public boolean updateEstatus(EmpleadoAPDTO empleado);
	
	public List<EmpleadoAPDTO> getEmpleadosExternos();
	
	public long getUsuarioByRFC(String RFC, Connection connection);


	public String getRfcUsuarioByIdC(long idUsuario, Connection connection);
	
	public boolean getExistUserbyRfc(String rfc) throws SQLException;
}
