package com.sytecso.dao.usuario;


import org.springframework.transaction.annotation.Transactional;

import com.sytecso.dao.DAOSimpleOperations;
import com.sytecso.model.Usuario;
import com.sytecso.model.UsuarioAcceso;

public interface DAOUsuario extends DAOSimpleOperations<Usuario> {
	@Transactional(readOnly = true)
	public UsuarioAcceso findByUserNameAndProfile(String userName, String profile);

	
}
