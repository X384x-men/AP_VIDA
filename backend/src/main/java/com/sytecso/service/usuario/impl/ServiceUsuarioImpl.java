package com.sytecso.service.usuario.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.usuario.DAOUsuario;
import com.sytecso.model.Usuario;
import com.sytecso.model.UsuarioAcceso;
import com.sytecso.service.usuario.ServiceUsuario;

@Service
public class ServiceUsuarioImpl implements ServiceUsuario {
	@Autowired
	private DAOUsuario daoUsuario;

	@Override
	public Optional<Usuario> findById(Long id) {
		return this.daoUsuario.findById(id);
	}

	@Override
	public Optional<List<Usuario>> findAll() {
		return this.daoUsuario.findAll();
	}

	@Override
	public boolean deleteById(Long id) {
		return this.daoUsuario.deleteById(id);
	}

	@Override
	public Optional<Usuario> create(Usuario object) {
		return this.daoUsuario.create(object);
	}

	@Override
	public boolean create(Usuario object, int option) {
		switch (option) {
		case 1:
			Object[] params = {};
			return this.daoUsuario.create(object, params);
		default:
			break;
		}
		return false;
	}

	@Override
	public Optional<Usuario> updateById(Long id) {
		return this.daoUsuario.updateById(id);
	}

	@Override
	public boolean updateAll(List<Usuario> object) {
		return this.daoUsuario.updateAll(object);
	}

	@Override
	public UsuarioAcceso findByUserNameAndProfile(String userName, String profile) {
		return this.daoUsuario.findByUserNameAndProfile(userName, profile);
	}

	@Override
	public Optional<List<Usuario>> create(List<Usuario> object) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
