package com.sytecso.dao.seccion.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.dao.seccion.DAOSeccion;
import com.sytecso.dto.modulosgui.ModuloDTO;
import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.CatalogoIconos;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.Seccion;
import com.sytecso.model.SeccionHasRolAcceso;
import com.sytecso.service.catalogos.ServiceCatalogoIconos;
import com.sytecso.service.pantallas.ServicePantallasAsignadas;

@Repository
public class DAOSeccionImpl implements DAOSeccion {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ServicePantallasAsignadas servicePantallasAsignadas;
	@Autowired
	private ServiceCatalogoIconos serviceCatalogoIconos;

	@Override
	@Transactional(readOnly = true)
	public List<Seccion> getAllByIdModulosGui(Long id) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Seccion> criteriaQuery = criteriaBuilder.createQuery(Seccion.class);
			Root<Seccion> root = criteriaQuery.from(Seccion.class);
			root.fetch("modulosGui", JoinType.INNER);
			criteriaQuery.where(criteriaBuilder.equal(root.get("modulosGui"), id)).distinct(true);
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();
	}

	/**
	 * Busca las secciones que no han sido asigadas a un rol dado, apartir de un
	 * modulo solo se tomaran como seleccionables, aquellas secciones que cuenten
	 * con pantallas asignadas, catalogo de iconos y con catalogo de servicios
	 * asignados. La busqueda es tipo JOIN INNER
	 **/
	@Override
	@Transactional(readOnly = true)
	public List<ModuloDTO> getSeccionNotPresentByRolAndModulo(SeccionRolDTO seccionRolDTO) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			CriteriaQuery<ModuloDTO> criteriaQuery = criteriaBuilder.createQuery(ModuloDTO.class);

			Root<Seccion> root = criteriaQuery.from(Seccion.class);
			Join<Seccion, ModulosGui> joinSeccionRol = root.join("modulosGui", JoinType.INNER);
			Join<Seccion, PantallasAsignadas> joinSeccionPantallas = root.join("pantallasAsignadas", JoinType.INNER);
			joinSeccionPantallas.join("pantallaAsignadaHasCatalogoServicios", JoinType.INNER);
			root.join("catalogoIconos", JoinType.INNER);
			joinSeccionPantallas.join("catalogoIconos", JoinType.INNER);
			joinSeccionRol.join("catalogoIconos", JoinType.INNER);

			// Se hace un subQuery para obtener las secciones no asignadas al rol
			CriteriaQuery<SeccionHasRolAcceso> subCriteriaQuery = criteriaBuilder
					.createQuery(SeccionHasRolAcceso.class);
			Subquery<SeccionHasRolAcceso> subQuery = subCriteriaQuery.subquery(SeccionHasRolAcceso.class);

			Root<SeccionHasRolAcceso> subHasRolAcceso = subQuery.from(SeccionHasRolAcceso.class);
			Join<SeccionHasRolAcceso, RolAcceso> subSeccionRoles = subHasRolAcceso.join("rolesAcceso", JoinType.INNER);

			subQuery.select(subHasRolAcceso.get("idSeccion"))
					.where(criteriaBuilder.equal(subSeccionRoles.get("nombre"), seccionRolDTO.getRol())).distinct(true);

			criteriaQuery.multiselect(root.get("nombre"))
					.where(criteriaBuilder.in(root.get("id")).value(subQuery).not(),
							criteriaBuilder.and(
									criteriaBuilder.equal(joinSeccionRol.get("nombre"), seccionRolDTO.getModulo())))
					.distinct(true);

			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ModuloDTO> getSeccionPresentByRol(SeccionRolDTO seccionRolDTO) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ModuloDTO> criteriaQuery = criteriaBuilder.createQuery(ModuloDTO.class);
			Root<Seccion> root = criteriaQuery.from(Seccion.class);
			Join<Seccion, ModulosGui> joinSeccionModulos = root.join("modulosGui", JoinType.INNER);
			Join<Seccion, SeccionHasRolAcceso> joinSeccionHasRoles = root.join("seccionHasRolesAcceso", JoinType.INNER);
			Join<SeccionHasRolAcceso, RolAcceso> joinHasRol = joinSeccionHasRoles.join("rolesAcceso", JoinType.INNER);
			criteriaQuery.multiselect(root.get("nombre")).where(
					criteriaBuilder.equal(joinSeccionModulos.get("nombre"), seccionRolDTO.getModulo()),
					criteriaBuilder.and(criteriaBuilder.equal(joinHasRol.get("nombre"), seccionRolDTO.getRol())));
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();

	}

	@Override
	@Transactional(readOnly = true)
	public Seccion findSeccionByName(String seccion) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Seccion> criteriaQuery = criteriaBuilder.createQuery(Seccion.class);
			Root<Seccion> root = criteriaQuery.from(Seccion.class);
			root.fetch("modulosGui", JoinType.INNER);
			criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), seccion));
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Set<Seccion> findIdSeccionesByNombre(Set<Seccion> secciones) {
		Set<Seccion> result = new HashSet<>();
		EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Seccion> criteriaQuery = criteriaBuilder.createQuery(Seccion.class);
		Root<Seccion> root = criteriaQuery.from(Seccion.class);
		for (Seccion seccion2 : secciones) {
			try {
				criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), seccion2.getNombre()));
				Seccion seccion = entityManager.createQuery(criteriaQuery).getSingleResult();
				if (seccion != null) {
					result.add(seccion);
				}
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Set<Seccion> create(Set<Seccion> secciones) {
		Set<Seccion> result = new HashSet<>(secciones);
		Set<Seccion> pr = new HashSet<>();
		Session session = this.sessionFactory.getCurrentSession();
		for (Iterator<Seccion> iterator = result.iterator(); iterator.hasNext();) {
			try {
				Seccion seccion = iterator.next();
				CatalogoIconos c = (CatalogoIconos) session
						.merge(this.serviceCatalogoIconos.findOrCreate(seccion.getCatalogoIconos()));
				seccion.setCatalogoIconos(c);
				Seccion s = this.mergeSeccion(seccion, session);
				pr.add(s);
				c.setSeccion(pr);
				s.setCatalogoIconos(c);
				session.saveOrUpdate(s);
				if (seccion.getPantallasAsignadas() != null && !seccion.getPantallasAsignadas().isEmpty()) {
					seccion.getPantallasAsignadas().stream().forEach(pantalla -> pantalla.setSeccion(s));
					s.setPantallasAsignadas(this.servicePantallasAsignadas.create(seccion.getPantallasAsignadas()));
					pr.add(s);
				}
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return pr;
	}

	private Seccion mergeSeccion(Seccion seccion, Session session) {
		if (seccion.getId() != null)
			return (Seccion) session.merge(seccion);
		Seccion s = new Seccion(seccion.getId(), seccion.getNombre(), seccion.getModulosGui());
		s.setCatalogoIconos(seccion.getCatalogoIconos());
		s.setUrl(seccion.getUrl());
		s.setEnabled(seccion.getEnabled());
		s.setIndex(seccion.getIndex());
		session.saveOrUpdate(s);
		return s;
	}
}
