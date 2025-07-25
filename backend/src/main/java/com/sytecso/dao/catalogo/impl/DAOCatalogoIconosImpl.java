package com.sytecso.dao.catalogo.impl;

import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.dao.catalogo.DAOCatalogoIconos;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.CatalogoIconos;

@Repository
public class DAOCatalogoIconosImpl implements DAOCatalogoIconos {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public CatalogoIconos getById(Long id) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<CatalogoIconos> criteriaQuery = criteriaBuilder.createQuery(CatalogoIconos.class);
			Root<CatalogoIconos> root = criteriaQuery.from(CatalogoIconos.class);
			criteriaQuery.multiselect(root.get("nombre")).where(criteriaBuilder.equal(root.get("id"), id));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CatalogoIconos create(CatalogoIconos catalogo) {
		try {
			CatalogoIconos c = new CatalogoIconos(catalogo.getNombre());
			Session session = this.sessionFactory.getCurrentSession();
			session.save(c);
			c.setPantallaAsignada(new HashSet<>());
			return c;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public CatalogoIconos findIdIconoByNombre(CatalogoIconos catalogo) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<CatalogoIconos> criteriaQuery = criteriaBuilder.createQuery(CatalogoIconos.class);
			Root<CatalogoIconos> root = criteriaQuery.from(CatalogoIconos.class);
			criteriaQuery.multiselect(root.get("id"), root.get("nombre"))
					.where(criteriaBuilder.equal(root.get("nombre"), catalogo.getNombre())).distinct(true);
			CatalogoIconos c = entityManager.createQuery(criteriaQuery).getSingleResult();
			c.setPantallaAsignada(new HashSet<>());
			return c;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

}
