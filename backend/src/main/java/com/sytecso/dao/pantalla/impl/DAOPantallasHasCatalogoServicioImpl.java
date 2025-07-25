package com.sytecso.dao.pantalla.impl;

import java.util.HashSet;
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

import com.sytecso.dao.pantalla.DAOPantallasHasCatalogoServicio;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.CatalogoServicios;
import com.sytecso.model.PantallaAsignadaHasCatalogoServicios;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.service.catalogos.ServiceCatalogoServicios;

@Repository
public class DAOPantallasHasCatalogoServicioImpl implements DAOPantallasHasCatalogoServicio {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ServiceCatalogoServicios serviceCatalogoServicios;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Set<PantallaAsignadaHasCatalogoServicios> findByIdPantallaAsignada(PantallasAsignadas pantallas) {
		try {
			EntityManager manager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<PantallaAsignadaHasCatalogoServicios> query = builder
					.createQuery(PantallaAsignadaHasCatalogoServicios.class);
			Root<PantallaAsignadaHasCatalogoServicios> root = query.from(PantallaAsignadaHasCatalogoServicios.class);
			query.where(builder.equal(root.get("idPantallaAsignada"), pantallas.getId()));
			return (Set<PantallaAsignadaHasCatalogoServicios>) manager.createQuery(query).getResultList();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Set<PantallaAsignadaHasCatalogoServicios> create(PantallasAsignadas pantallas, String[] servicios) {
		Session session = this.sessionFactory.getCurrentSession();
		Set<PantallaAsignadaHasCatalogoServicios> list = new HashSet<>();
		try {
			Set<CatalogoServicios> catalogos = this.serviceCatalogoServicios.findByNombreOrCreate(servicios);
			if (!catalogos.isEmpty()) {
				if (pantallas.getId() == null)
					session.saveOrUpdate(pantallas);
				for (CatalogoServicios catalogo : catalogos) {
					if (this.findByIdCatalogoAndIdPantalla(catalogo.getId(), pantallas.getId()) == null) {
						PantallaAsignadaHasCatalogoServicios hasCatalogo = new PantallaAsignadaHasCatalogoServicios();
						hasCatalogo.setIdCatalogoServicios(catalogo.getId());
						hasCatalogo.setIdPantallaAsignada(pantallas.getId());
						session.saveOrUpdate(hasCatalogo);
						list.add(hasCatalogo);
					}
				}
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return list;
	}

	@Override
	public PantallaAsignadaHasCatalogoServicios findByIdCatalogoAndIdPantalla(Long idCatalogo, Long idPantalla) {
		try {
			EntityManager manager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder builder = manager.getCriteriaBuilder();
			CriteriaQuery<PantallaAsignadaHasCatalogoServicios> query = builder
					.createQuery(PantallaAsignadaHasCatalogoServicios.class);
			Root<PantallaAsignadaHasCatalogoServicios> root = query.from(PantallaAsignadaHasCatalogoServicios.class);
			query.where(builder.equal(root.get("idPantallaAsignada"), idPantalla),
					builder.and(builder.equal(root.get("idCatalogoServicios"), idCatalogo)));
			return manager.createQuery(query).getSingleResult();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

}
