package com.sytecso.dao.usuario.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sytecso.dao.usuario.DAOUsuario;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.Usuario;
import com.sytecso.model.UsuarioAcceso;

@Repository
public class DAOUsuarioImpl implements DAOUsuario {
	@Autowired
	private SessionFactory sessionFactory;

	

	@Override
	public UsuarioAcceso findByUserNameAndProfile(String userName, String profile) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioAcceso> query = criteriaBuilder.createQuery(UsuarioAcceso.class);
			Root<UsuarioAcceso> root = query.from(UsuarioAcceso.class);
			Join<UsuarioAcceso, RolAcceso> joinRol = root.join("rolesAcceso", JoinType.INNER);
			root.join("user", JoinType.INNER);
			query.where(criteriaBuilder.equal(root.get("usuario"), userName),
					criteriaBuilder.equal(joinRol.get("nombre"), profile)).distinct(true);
			return entityManager.createQuery(query).getSingleResult();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}



	@Override
	public Optional<Usuario> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Optional<List<Usuario>> findAll() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean deleteById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Optional<Usuario> create(Usuario object) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean create(Usuario object, Object[] params) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public Optional<List<Usuario>> create(List<Usuario> object) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Optional<Usuario> updateById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean updateAll(List<Usuario> object) {
		// TODO Auto-generated method stub
		return false;
	}



}
