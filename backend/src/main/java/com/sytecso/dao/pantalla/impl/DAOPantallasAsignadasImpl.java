package com.sytecso.dao.pantalla.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.dao.pantalla.DAOPantallasAsignadas;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.CatalogoIconos;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.service.catalogos.ServiceCatalogoIconos;
import com.sytecso.service.pantallas.ServicePantallasHasCatalogoServicio;

@Repository
public class DAOPantallasAsignadasImpl implements DAOPantallasAsignadas {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	ServicePantallasHasCatalogoServicio servicePantallasHasCatalogo;
	@Autowired
	private ServiceCatalogoIconos serviceCatalogoIconos;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Set<PantallasAsignadas> create(Set<PantallasAsignadas> pantallas) {
		Set<PantallasAsignadas> result = new HashSet<>(pantallas);
		Session session = this.sessionFactory.getCurrentSession();
		for (Iterator<PantallasAsignadas> iterator = result.iterator(); iterator.hasNext();) {
			try {
				PantallasAsignadas pantallasAsignadas = iterator.next();				
				CatalogoIconos c = this.serviceCatalogoIconos.findOrCreate(pantallasAsignadas.getCatalogoIconos());
				c.getPantallaAsignada().add(pantallasAsignadas);
				pantallasAsignadas.setCatalogoIconos((CatalogoIconos) session.merge(c));
				if (pantallasAsignadas.getServicios().length > 0) {
					pantallasAsignadas.setPantallaAsignadaHasCatalogoServicios(this.servicePantallasHasCatalogo
							.create(pantallasAsignadas, pantallasAsignadas.getServicios()));
				}
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Set<PantallasAsignadas> findPantallasByNombre(Set<PantallasAsignadas> pantallas) {
		Set<PantallasAsignadas> result = new HashSet<>();
		EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PantallasAsignadas> query = builder.createQuery(PantallasAsignadas.class);
		Root<PantallasAsignadas> root = query.from(PantallasAsignadas.class);
		for (PantallasAsignadas pantallasAsignadas : pantallas) {
			try {
				query.where(builder.equal(root.get("nombre"), pantallasAsignadas.getNombre())).distinct(true);
				PantallasAsignadas pantalla = entityManager.createQuery(query).getSingleResult();
				if (pantalla != null)
					result.add(pantalla);
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}

		}
		return result;
	}

}
