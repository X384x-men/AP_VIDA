package com.sytecso.dao.catalogo.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.dao.catalogo.DAOCatalogoServicios;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.CatalogoServicios;

@Repository
public class DAOCatalogoServiciosImpl implements DAOCatalogoServicios {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional(readOnly = true)
	public Set<CatalogoServicios> findByNombre(String[] catalogos) {
		Set<CatalogoServicios> result = new HashSet<>();
		EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CatalogoServicios> criteriaQuery = criteriaBuilder.createQuery(CatalogoServicios.class);
		Root<CatalogoServicios> root = criteriaQuery.from(CatalogoServicios.class);
		for (int i = 0; i < catalogos.length; i++) {
			try {
				criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), catalogos[i])).distinct(true);
				CatalogoServicios catalogo = entityManager.createQuery(criteriaQuery).getSingleResult();
				if (catalogo != null) {
					result.add(catalogo);
				}
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Set<CatalogoServicios> create(String[] catalogos) {
		Set<CatalogoServicios> result = new HashSet<>();
		Session session = this.sessionFactory.getCurrentSession();
		for (int i = 0; i < catalogos.length; i++) {
			try {
				CatalogoServicios catalogo = new CatalogoServicios();
				catalogo.setNombre(catalogos[i]);
				session.saveOrUpdate(catalogo);
				result.add(catalogo);
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Set<CatalogoServicios> create(List<CatalogoServicios> catalogos) {
		Set<CatalogoServicios> result = new HashSet<>();
		Session session = this.sessionFactory.getCurrentSession();
		for (CatalogoServicios catalogo : catalogos) {
			try {
				session.saveOrUpdate(catalogo);
				result.add(catalogo);
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return result;
	}

}
