package com.sytecso.service.usuario.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sytecso.dao.usuario.DAOUsuarioAcceso;
import com.sytecso.dto.EmailBody;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.dto.usuarioacceso.UsuarioAcceso;
import com.sytecso.component.utility.SessionEmail;
import com.sytecso.component.utility.UtileriaCifrado;
import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.dto.usuarioacceso.UsuarioAccesoDTO;
import com.sytecso.component.exceptions.CuadrillasException.NotUserFoundException;
import com.sytecso.component.exceptions.UsuarioAccesoException.PasswordNotUpdatedException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioExistsException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioNotExistsException;
import com.sytecso.service.usuario.ServiceUsuarioAcceso;

@Service
public class ServiceUsuarioAccesoImpl implements ServiceUsuarioAcceso {

	
	@Autowired
	private DAOUsuarioAcceso daoUsuarioAcceso;

	




	@Override
	public boolean usuarioExists(String usuario) {
		return daoUsuarioAcceso.usuarioExists(usuario);
	}

	@Override
	public UsuarioAccesoDTO findUserByUserNameAndPassword(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException {
		return daoUsuarioAcceso.findUserByUserNameAndPassword(usuario);
	}

	

	@Override
	public UsuarioAccesoDTO findUserByUserName(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException {
		return daoUsuarioAcceso.findUserByUserName(usuario);
	}

	@Override
	public UsuarioAccesoDTO findUser(Integer param, UsuarioAccesoDTO usuario) throws UsuarioNotExistsException {
		switch (param) {
		case 1:
			return findUserByUserName(usuario);
		case 2:
			return findUserByUserNameAndPassword(usuario);
		default:
			break;
		}
		throw new IllegalArgumentException();
	}


	@Override
	public boolean updatePasswordByUserName(UsuarioAccesoDTO usuario, String cuadrilla, Integer option)
			throws PasswordNotUpdatedException, NotUserFoundException, UsuarioNotExistsException {
		switch (option) {
		case 1:
			return true;
		case 2:
			return this.update(usuario);
		default:
			break;
		}
		throw new IllegalArgumentException();
	}

	@Override
	public boolean usuarioExists(String usuario, String profile) {
		return this.usuarioExists(usuario, profile);
	}


	@Override
	public boolean update(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException, PasswordNotUpdatedException {
		return daoUsuarioAcceso.updatePasswordByUserName(usuario);
	}

	
	
	@Override
	public boolean createUsuarioAP(@Valid EmpleadoAPDTO empleado) throws UsuarioExistsException, SQLException {
		return daoUsuarioAcceso.saveEmpleadoUsuarioAP(empleado);
	}

	@Override
	public boolean updateUsuarioAP(@Valid EmpleadoAPDTO empleado) throws SQLException {
		return daoUsuarioAcceso.updateEmpleadoUsuarioAP(empleado);
	}

	@Override
	public boolean updateEmpleado(@Valid EmpleadoAPDTO empleado) throws SQLException {
		return daoUsuarioAcceso.updateEmpleadoData(empleado);
	}

	
	@Override
	public boolean actualizaEmpleadoAP(EmpleadoAPDTO empleado) throws UsuarioExistsException, SQLException {
		System.out.println("updating...");
		boolean state = false;
		if(empleado.getPsw().equals("") ) {
			System.out.println("password vacío");
			state = true;
		} else {
			if(daoUsuarioAcceso.getPwdEmpleadoAP(empleado.getRfc()).getPsw().equals(UtileriaCifrado.getMD5(empleado.getPswValidate()))) {
				state = daoUsuarioAcceso.updatePwdEmpleadoAP(empleado);
			}else {
				return false;
			}
		}
		if(state && daoUsuarioAcceso.updateEmpleadoAP(empleado)) {
		System.out.println("Update DB complete");
			//String nombreCompleo= empleado.getNombre()+" "+empleado.getApellidoPaterno()+" "+empleado.getApellidoPaterno();
			//serviceEmail.sendEmailUpdate(nombreCompleo,empleado.getMail());
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public List<RolAccesoDTO> getPerfiles() {
		return daoUsuarioAcceso.getPerfiles();
	}

	
	@Override
	public boolean updateUsuario(UsuarioAcceso usuario) {
		return daoUsuarioAcceso.updatePasswordByUserNameEmpleado(usuario);
	}


	
	@Override
	public boolean usuarioAPExists(String rfc) {
		EmpleadoAPDTO emp = daoUsuarioAcceso.getUsuarioAP(rfc);
		if(emp.getIdUsuarioAcceso() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public EmpleadoAPDTO getEmpleadoAP(String usr) {
		return daoUsuarioAcceso.getEmpleadoAP(usr);
		}
	
	@Override
	public String getusuarioNombrebyUsr(String user) {
		return daoUsuarioAcceso.nombreUsuariobyUser(user);
	}

	@Override
	public EmpleadoAPDTO getEmpleadoAPbyRFC(String rfc) {
		return daoUsuarioAcceso.getEmpleadoAPbyRFC( rfc) ;
	}
	
	@Override
	public String getReporte(String rfc, String anio, String mes) throws IOException {
	  StringBuilder resultado = new StringBuilder();
	  String urlVector = SessionEmail.getUrlVectorProp();
	  String urlSite = urlVector + rfc + "&anio=" + anio + "&mes=" + mes ;
		try {
			URL url = new URL(urlSite);
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setRequestProperty("x-api-key", "2nol7gD0BZ98IVaV9ibFP1pG1qxbaceL2YnFQNyA");
			  conexion.setRequestMethod("GET");
			  BufferedReader rd = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
			  String linea;
			  while ((linea = rd.readLine()) != null) {
			    resultado.append(linea);
			  }
			  rd.close();
			  System.out.println(resultado.toString());
			  return resultado.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	  return null;
	}

	@Override
	public EmpleadoAPDTO getEmpleadoAPbyeIdUsuario(String idUsuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEmail(String rfc) {
		return daoUsuarioAcceso.getEmail(rfc);
	}

	@Override
	public boolean updateEmailbyRFC(EmailBody email) {
		return daoUsuarioAcceso.updateEmailbyRFC(email);
	}

	@Override
	public List<EmpleadoAPDTO> getBusquedaEmpleadosAP(String rfc, String nombre, String dependencia,String unidadAdmin) {
		
		if(rfc==null||rfc.contentEquals("null")){
			rfc="";
		}
		if(nombre==null||nombre.contentEquals("null")){
			nombre="";
		}
		if(dependencia==null||dependencia.contentEquals("null")){
			dependencia="";
		}
		if(unidadAdmin==null||unidadAdmin.contentEquals("null")){
			unidadAdmin="";
		}
		String condicion="";
		if(!rfc.contentEquals("")){
			condicion="and rfc like '%"+rfc+"%' ";
		}
		if(!nombre.contentEquals("")){
			condicion=condicion+"and CONCAT(nombre,' ',apellidoP,' ',apellidoM) like '%"+nombre+"%' ";
		}
		if(!dependencia.contentEquals("")){
			condicion=condicion+"and descDepen like '%"+dependencia+"%' ";
		}
		if(!unidadAdmin.contentEquals("")){
			condicion=condicion+"and descUni like '%"+unidadAdmin+"%' ";
		}
		
		return daoUsuarioAcceso.getBusquedaEmpleadosAP(condicion);
	}

	@Override
	public Long createUsuarioAcceso(com.sytecso.model.UsuarioAcceso usuarioAcceso) throws SQLException {
		// TODO Auto-generated method stub
		return daoUsuarioAcceso.createUsuarioAcceso(usuarioAcceso);
	}
	
	@Override
	public boolean updateEstatus(EmpleadoAPDTO empleado) throws UsuarioExistsException, SQLException {
		return daoUsuarioAcceso.updateEstatus(empleado);
	}
	
	
	@Override
	public List<EmpleadoAPDTO> getEmpleadosExternos() {
		return daoUsuarioAcceso.getEmpleadosExternos();
	}

	@Override
	public boolean getUserExist(String rfc) throws SQLException {
		// TODO Auto-generated method stub
		return daoUsuarioAcceso.getExistUserbyRfc(rfc);
	}

	

}
