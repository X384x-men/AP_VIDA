package com.sytecso.service.usuario;


import com.sytecso.model.Usuario;
import com.sytecso.model.UsuarioAcceso;
import com.sytecso.service.ServiceCRUDOperations;

public interface ServiceUsuario extends ServiceCRUDOperations<Usuario> {
	public UsuarioAcceso findByUserNameAndProfile(String userName, String profile);


}
