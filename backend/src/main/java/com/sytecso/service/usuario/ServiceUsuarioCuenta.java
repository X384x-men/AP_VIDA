package com.sytecso.service.usuario;

import java.util.List;

import com.sytecso.beans.UsuarioCuenta;

public interface ServiceUsuarioCuenta {

    void registrarUsuarioCuenta(UsuarioCuenta usuarioCuenta) throws Exception;
    void actualizarUsuarioCuenta(UsuarioCuenta usuarioCuenta) throws Exception;
    void actualizarUsuarioCuentaPwd(UsuarioCuenta usuarioCuenta) throws Exception;
    UsuarioCuenta traerCamposUsuarioCuenta(int idUsuarioCuenta) throws Exception;
    UsuarioCuenta traerUsuarioPorUsername(String username) throws Exception;
	List<UsuarioCuenta> traerTodosUsuarios();
	UsuarioCuenta traerUsuario(String idUsuarioCuenta);
	void cancelarUsuarioCuenta(String idUsuarioCuenta) throws Exception;
	void suspenderUsuarioCuenta(String idUsuarioCuenta) throws Exception;
}
