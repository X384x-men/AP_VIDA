package com.sytecso.dao.seccion.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.dao.seccion.DAOSeccionHasRolesAcceso;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.Seccion;
import com.sytecso.model.SeccionHasRolAcceso;

@Repository
public class DAOSeccionHasRolesAccesoImpl implements DAOSeccionHasRolesAcceso {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean create(SeccionHasRolAcceso seccionHasRolAcceso) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.save(seccionHasRolAcceso);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean remove(SeccionHasRolAcceso seccionHasRolAcceso) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.remove(seccionHasRolAcceso);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			return false;
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean create(Set<SeccionHasRolAcceso> seccionHasRolAcceso) {
		int index = 0;
		int tot = 0;
		for (Iterator<SeccionHasRolAcceso> iterator = seccionHasRolAcceso.iterator(); iterator.hasNext();) {
			SeccionHasRolAcceso seccionHasRolAcceso2 = iterator.next();
			try {
				tot++;
				index++;
				Session session = this.sessionFactory.getCurrentSession();
				session.saveOrUpdate(seccionHasRolAcceso2);
				tot = UtileriaSql.flushAndClearTransaction(session, tot, seccionHasRolAcceso.size(), index);
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
				return false;
			}
		}

		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(String columnName, long id) {
		EntityManager entityManager = (EntityManager) sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaDelete<SeccionHasRolAcceso> criteriaDelete = criteriaBuilder
				.createCriteriaDelete(SeccionHasRolAcceso.class);
		Root<SeccionHasRolAcceso> root = criteriaDelete.from(SeccionHasRolAcceso.class);
		criteriaDelete.where(criteriaBuilder.equal(root.get(columnName), id));
		return entityManager.createQuery(criteriaDelete).executeUpdate() > 0;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<SeccionHasRolAcceso> findByAuthority(String authority) {
		try {
			EntityManager entityManager = (EntityManager) sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<SeccionHasRolAcceso> criteriaQuery = criteriaBuilder.createQuery(SeccionHasRolAcceso.class);
			Root<SeccionHasRolAcceso> root = criteriaQuery.from(SeccionHasRolAcceso.class);
			Join<SeccionHasRolAcceso, RolAcceso> joinRol = root.join("rolesAcceso", JoinType.INNER);
			Fetch<SeccionHasRolAcceso, Seccion> fetchSeccion = root.fetch("seccion", JoinType.INNER);
			Fetch<Seccion, ModulosGui> fetchModulos = fetchSeccion.fetch("modulosGui", JoinType.INNER);
			fetchModulos.fetch("catalogoIconos", JoinType.INNER);
			fetchSeccion.fetch("catalogoIconos", JoinType.INNER);
			criteriaQuery.where(criteriaBuilder.equal(joinRol.get("nombre"), authority)).distinct(true);
			return new HashSet<>(entityManager.createQuery(criteriaQuery).getResultList());
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

}
