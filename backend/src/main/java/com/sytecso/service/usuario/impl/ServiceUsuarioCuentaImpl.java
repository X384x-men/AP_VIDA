package com.sytecso.service.usuario.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.beans.UsuarioCuenta;
import com.sytecso.dao.usuario.DAOUsuarioCuenta;
import com.sytecso.service.usuario.ServiceUsuarioCuenta;

@Service
public class ServiceUsuarioCuentaImpl implements ServiceUsuarioCuenta{

    @Autowired
    private DAOUsuarioCuenta daoUsuarioCuenta;

    public void registrarUsuarioCuenta(UsuarioCuenta usuarioCuenta) throws Exception {
        daoUsuarioCuenta.registrarUsuarioCuenta(usuarioCuenta);
    }

    public void actualizarUsuarioCuenta(UsuarioCuenta usuarioCuenta) throws Exception {
        daoUsuarioCuenta.actualizarUsuarioCuenta(usuarioCuenta);
    }
    public void actualizarUsuarioCuentaPwd(UsuarioCuenta usuarioCuenta) throws Exception {
        daoUsuarioCuenta.actualizarUsuarioCuentaPwd(usuarioCuenta);
    }
    public UsuarioCuenta traerCamposUsuarioCuenta(int idUsuarioCuenta) throws Exception {
        return daoUsuarioCuenta.traerCamposUsuarioCuenta(idUsuarioCuenta);
    }

    public UsuarioCuenta traerUsuarioPorUsername(String username) throws Exception {
        return daoUsuarioCuenta.traerUsuarioPorUsername(username);
    }
    
	public List<UsuarioCuenta> traerTodosUsuarios() {
		List<UsuarioCuenta> lstUsuarios=null;
		lstUsuarios =  daoUsuarioCuenta.traerTodosUsuarios();
		return lstUsuarios;
	}
	public UsuarioCuenta traerUsuario(String idUsuarioCuenta) {
		UsuarioCuenta elUsuario=daoUsuarioCuenta.traerUsuario(idUsuarioCuenta);
		return elUsuario;
	}

	public void cancelarUsuarioCuenta(String idUsuarioCuenta) throws Exception {
		daoUsuarioCuenta.cancelarUsuarioCuenta(Integer.parseInt(idUsuarioCuenta));
	}
	public void suspenderUsuarioCuenta(String idUsuarioCuenta) throws Exception {
		daoUsuarioCuenta.suspenderUsuarioCuenta(Integer.parseInt(idUsuarioCuenta));
	} 
}
