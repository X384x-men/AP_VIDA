package com.sytecso.dao.rolAcceso.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.config.logger.SytecsoLogger;
import com.sytecso.dao.rolAcceso.DAORolAcceso;
import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.model.CatalogoServicios;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.ModulosGuiHasRolesAcceso;
import com.sytecso.model.PantallaAsignadaHasCatalogoServicios;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.Seccion;

@Repository
@Transactional
public class DAORolAccesoImpl implements DAORolAcceso {
	@Autowired
	private SessionFactory sessionFactory;
	private static final String ROL_USER_ANROID = "PRINCIPAL";
	@Autowired
	private DataSource dataSource;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public RolAcceso save(RolAcceso rolAcceso) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String query = "INSERT INTO rolesacceso (DescripcionRol, nombreRol) VALUES (?,?)";

		try {
			con = dataSource.getConnection();
			ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, rolAcceso.getDescripcion());
			ps.setString(2, rolAcceso.getNombre());
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if (rs.next()) {
				rolAcceso.setId( rs.getLong(1));
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closePreparedStatemetAndResultSet(ps, rs);
		}
		return rolAcceso;
	}

	@Override
	@Transactional(readOnly = true)
	public RolAcceso findByRol(String rol) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RolAcceso> query = builder.createQuery(RolAcceso.class);
			Root<RolAcceso> root = query.from(RolAcceso.class);
			query.multiselect(root.get("id"), root.get("nombre"), root.get("descripcion"))
					.where(builder.equal(root.get("nombre"), rol)).distinct(true);
			return entityManager.createQuery(query).getSingleResult();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<RolAccesoDTO> findAll() {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.createEntityManager();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RolAccesoDTO> query = builder.createQuery(RolAccesoDTO.class);
			Root<RolAcceso> root = query.from(RolAcceso.class);
			query.multiselect(root.get("nombre")).where(builder.notLike(root.get("nombre"), ROL_USER_ANROID));
			return entityManager.createQuery(query).getResultList();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();
	}

	@Override
	public List<RolAcceso> getAll() {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder builder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RolAcceso> query = builder.createQuery(RolAcceso.class);
			Root<RolAcceso> root = query.from(RolAcceso.class);
			query.multiselect(root.get("id"), root.get("nombre")).distinct(true);
			return entityManager.createQuery(query).getResultList();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean userHasAcceso(String rol, String servicio) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RolAcceso> criteriaQuery = criteriaBuilder.createQuery(RolAcceso.class);
			Root<RolAcceso> root = criteriaQuery.from(RolAcceso.class);
			Join<RolAcceso, ModulosGuiHasRolesAcceso> joinRolModulos = root.join("modulosGuiHasRolesAcceso",
					JoinType.INNER);
			Join<ModulosGuiHasRolesAcceso, ModulosGui> joinModulosGuiModulos = joinRolModulos.join("modulosGui",
					JoinType.INNER);
			Join<ModulosGui, Seccion> joinModulosGuiSeccion = joinModulosGuiModulos.join("seccion", JoinType.INNER);
			Join<Seccion, PantallasAsignadas> joinSeccionPantallas = joinModulosGuiSeccion.join("pantallasAsignadas",
					JoinType.INNER);
			Join<PantallasAsignadas, PantallaAsignadaHasCatalogoServicios> joinPantallasHasPantallas = joinSeccionPantallas
					.join("pantallaAsignadaHasCatalogoServicios", JoinType.INNER);
			Join<PantallaAsignadaHasCatalogoServicios, CatalogoServicios> joinPantallasCatalogo = joinPantallasHasPantallas
					.join("catalogoServicios", JoinType.INNER);
			criteriaQuery
					.where(criteriaBuilder.equal(root.get("nombre"), rol),
							criteriaBuilder.and(criteriaBuilder.or(
									criteriaBuilder.equal(joinModulosGuiModulos.get("url"), servicio),
									criteriaBuilder.equal(joinModulosGuiSeccion.get("url"), servicio),
									criteriaBuilder.equal(joinSeccionPantallas.get("url"), servicio),
									criteriaBuilder.equal(joinPantallasCatalogo.get("nombre"), servicio))))
					.distinct(true);

			return entityManager.createQuery(criteriaQuery).getSingleResult() != null;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			SytecsoLogger.error("El servcio: ".concat(servicio).concat(" ").concat(" no esta asignado al perfil: ").concat(rol), e);
			System.err.println("El servcio: ".concat(servicio).concat(" ").concat(" no esta asignado al perfil: ").concat(rol));
		}
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean update(RolAccesoDTO rol, String prevRol) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			EntityManager entityManager = (EntityManager) session;
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RolAcceso> criteriaQuery = criteriaBuilder.createQuery(RolAcceso.class);
			Root<RolAcceso> root = criteriaQuery.from(RolAcceso.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), prevRol)).distinct(true);
			RolAcceso acceso = entityManager.createQuery(criteriaQuery).getSingleResult();
			if (acceso != null) {
				acceso.setNombre(rol.getNombre());
				acceso.setDescripcion(rol.getDescripcion());
				session.saveOrUpdate(acceso);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			return false;
		}
		return true;
	}

	@Override
	public boolean userHasAccesoURLParams(String rol, String servicio) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<RolAcceso> criteriaQuery = criteriaBuilder.createQuery(RolAcceso.class);
			Root<RolAcceso> root = criteriaQuery.from(RolAcceso.class);
			Join<RolAcceso, ModulosGuiHasRolesAcceso> joinRolModulos = root.join("modulosGuiHasRolesAcceso",
					JoinType.INNER);
			Join<ModulosGuiHasRolesAcceso, ModulosGui> joinModulosGuiModulos = joinRolModulos.join("modulosGui",
					JoinType.INNER);
			Join<ModulosGui, Seccion> joinModulosGuiSeccion = joinModulosGuiModulos.join("seccion", JoinType.INNER);
			Join<Seccion, PantallasAsignadas> joinSeccionPantallas = joinModulosGuiSeccion.join("pantallasAsignadas",
					JoinType.INNER);
			Join<PantallasAsignadas, PantallaAsignadaHasCatalogoServicios> joinPantallasHasPantallas = joinSeccionPantallas
					.join("pantallaAsignadaHasCatalogoServicios", JoinType.INNER);
			joinPantallasHasPantallas.join("catalogoServicios", JoinType.INNER);
			criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), rol),
					criteriaBuilder
							.and(
							criteriaBuilder.or(
									criteriaBuilder.equal(joinModulosGuiSeccion.get("url"), servicio),
									criteriaBuilder.equal(joinSeccionPantallas.get("url"), servicio),
									criteriaBuilder.equal(joinModulosGuiModulos.get("url"), servicio)))
							).distinct(true);

			return entityManager.createQuery(criteriaQuery).getSingleResult() != null;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
			System.err.println("El servcio con parametros de url: ".concat(servicio).concat(" ").concat(" no esta asignado al perfil: ").concat(rol).concat(" si la url no esta tipificada con parametros por defecto, omita este mensaje"));
			SytecsoLogger.error("El servcio con parametros de url: ".concat(servicio).concat(" ").concat(" no esta asignado al perfil: ").concat(rol).concat(" si la url no esta tipificada con parametros por defecto, omita este mensaje"), e);

		}
		return true;
	}

}
