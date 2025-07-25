package com.sytecso.dao.modulos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.config.logger.SytecsoLogger;
import com.sytecso.config.menu.PantallaDTO;
import com.sytecso.config.menu.ServicioDTO;
import com.sytecso.config.menu.SubMenuDTO;
import com.sytecso.dao.modulos.DAOModulosGui;
import com.sytecso.dto.modulosgui.ModuloDTO;
import com.sytecso.config.menu.MenuDTO;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.exceptions.SytecsoExceptions;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.ModulosGuiHasRolesAcceso;
import com.sytecso.model.PantallaAsignadaHasCatalogoServicios;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.Seccion;
import com.sytecso.model.CatalogoIconos;
import com.sytecso.service.catalogos.ServiceCatalogoIconos;
import com.sytecso.service.seccion.ServiceSeccion;

@Repository
public class DAOModulosGuiImpl implements DAOModulosGui {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ServiceSeccion serviceSeccion;
	@Autowired
	private ServiceCatalogoIconos serviceCatalogoIconos;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<ModulosGui> getAllByRolAcceso(String rol) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ModulosGui> criteriaQuery = criteriaBuilder.createQuery(ModulosGui.class).distinct(true);
			Root<ModulosGui> root = criteriaQuery.from(ModulosGui.class);
			Join<ModulosGui, ModulosGuiHasRolesAcceso> joinModulos = root.join("modulosGuiHasRolesAccesos",
					JoinType.INNER);
			Join<ModulosGuiHasRolesAcceso, RolAcceso> joinRolAcceso = joinModulos.join("rolesAcceso", JoinType.INNER);
			Fetch<ModulosGui, Seccion> fetchModuloSeccion = root.fetch("seccion", JoinType.INNER);
			fetchModuloSeccion.fetch("catalogoIconos", JoinType.INNER);
			root.fetch("catalogoIconos", JoinType.INNER);
			criteriaQuery.where(criteriaBuilder.equal(joinRolAcceso.get("nombre"), rol))
					.orderBy(criteriaBuilder.asc(root.get("nombre"))).distinct(true);
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			SytecsoExceptions.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void create(List<ModulosGui> modulos) {
		Set<ModulosGui> m = new HashSet<>(modulos);
		Session session = this.sessionFactory.getCurrentSession();
		for (Iterator<ModulosGui> iterator = m.iterator(); iterator.hasNext();) {
			ModulosGui modulosGui = iterator.next();
			try {
				if (modulosGui.getSeccion() != null && !modulosGui.getSeccion().isEmpty()) {
					CatalogoIconos c = (CatalogoIconos) session
							.merge(this.serviceCatalogoIconos.findOrCreate(modulosGui.getCatalogoIconos()));
					session.saveOrUpdate(c);
					ModulosGui ms = this.mergeModulo(modulosGui, session);
					ms.setCatalogoIconos(c);
					session.saveOrUpdate(ms);
					modulosGui.getSeccion().stream().forEach(seccion -> seccion.setModulosGui(ms));
					this.serviceSeccion.create(modulosGui.getSeccion());
				} else if (modulosGui.getCatalogoIconos() != null) {
					modulosGui
							.setCatalogoIconos(this.serviceCatalogoIconos.findOrCreate(modulosGui.getCatalogoIconos()));
					session.saveOrUpdate(modulosGui);
				} else {
					session.saveOrUpdate(modulosGui);
				}
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		modulos.clear();
	}

	private ModulosGui mergeModulo(ModulosGui modulo, Session session) {
		if (modulo.getId() != null)
			return (ModulosGui) session.merge(modulo);
		ModulosGui modulos = new ModulosGui(modulo.getId(), modulo.getNombre());
		modulos.setEnabled(modulo.getEnabled());		
		modulos.setUrl(modulo.getUrl());
		modulos.setIndex(modulo.getIndex());
		return modulos;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public ModulosGui findLeftAllModuloGuiByName(String menuName) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ModulosGui> criteriaQuery = criteriaBuilder.createQuery(ModulosGui.class).distinct(true);
			Root<ModulosGui> root = criteriaQuery.from(ModulosGui.class);
			Fetch<ModulosGui, Seccion> fetchModuloSeccion = root.fetch("seccion", JoinType.LEFT);
			Fetch<Seccion, PantallasAsignadas> fetchSeccionPantallas = fetchModuloSeccion.fetch("pantallasAsignadas",
					JoinType.LEFT);
			Fetch<PantallasAsignadas, PantallaAsignadaHasCatalogoServicios> fetchPantaAsignadaPantallaCatalogo = fetchSeccionPantallas
					.fetch("pantallaAsignadaHasCatalogoServicios", JoinType.LEFT);
			fetchPantaAsignadaPantallaCatalogo.fetch("catalogoServicios", JoinType.LEFT);
			fetchSeccionPantallas.fetch("catalogoIconos", JoinType.LEFT);
			fetchModuloSeccion.fetch("catalogoIconos", JoinType.LEFT);
			root.fetch("catalogoIconos", JoinType.LEFT);
			criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), menuName)).distinct(true);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ModulosGui createOrUpdate(ModulosGui modulo) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			if (modulo.getId() != null && modulo.getId().compareTo(0L) > 0) {
				session.saveOrUpdate(modulo);
			} else {
				ModulosGui m = this.findLeftAllModuloGuiByName(modulo.getNombre());
				if (m != null) {
					modulo.setId(m.getId());
					session.update(modulo);
				} else {
					session.save(modulo);
				}
			}
			return modulo;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ModulosGui create(ModulosGui modulo) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.save(modulo);
			return modulo;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ModulosGui findModuloGuiByName(String menuName) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ModulosGui> criteriaQuery = criteriaBuilder.createQuery(ModulosGui.class).distinct(true);
			Root<ModulosGui> root = criteriaQuery.from(ModulosGui.class);
			criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), menuName)).distinct(true);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Set<ModulosGui> findAll() {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ModulosGui> criteriaQuery = criteriaBuilder.createQuery(ModulosGui.class);
			Root<ModulosGui> root = criteriaQuery.from(ModulosGui.class);
			Fetch<ModulosGui, Seccion> fetchModuloSeccion = root.fetch("seccion", JoinType.INNER);
			Fetch<Seccion, PantallasAsignadas> fetchSeccionPantallas = fetchModuloSeccion.fetch("pantallasAsignadas",
					JoinType.INNER);
			Fetch<PantallasAsignadas, PantallaAsignadaHasCatalogoServicios> fetchPantaAsignadaPantallaCatalogo = fetchSeccionPantallas
					.fetch("pantallaAsignadaHasCatalogoServicios", JoinType.INNER);
			fetchPantaAsignadaPantallaCatalogo.fetch("catalogoServicios", JoinType.INNER);
			fetchSeccionPantallas.fetch("catalogoIconos", JoinType.INNER);
			fetchModuloSeccion.fetch("catalogoIconos", JoinType.INNER);
			root.fetch("catalogoIconos", JoinType.INNER);
			criteriaQuery.distinct(true);
			return new HashSet<>(entityManager.createQuery(criteriaQuery).getResultList());
		} catch (Exception e) {
			SytecsoExceptions.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ModuloDTO> getModuloNotPresentByRol(String rol) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			CriteriaQuery<ModuloDTO> criteriaQuery = criteriaBuilder.createQuery(ModuloDTO.class);
			Root<ModulosGui> root = criteriaQuery.from(ModulosGui.class);

			Join<ModulosGui, Seccion> joinModuloSeccion = root.join("seccion", JoinType.INNER);
			Join<Seccion, PantallasAsignadas> joinPantallasAsignadas = joinModuloSeccion.join("pantallasAsignadas",
					JoinType.INNER);
			joinPantallasAsignadas.join("pantallaAsignadaHasCatalogoServicios", JoinType.INNER);

			CriteriaQuery<ModulosGuiHasRolesAcceso> subCriteriaQuery = criteriaBuilder
					.createQuery(ModulosGuiHasRolesAcceso.class);
			Subquery<ModulosGuiHasRolesAcceso> subQuery = subCriteriaQuery.subquery(ModulosGuiHasRolesAcceso.class);

			Root<ModulosGuiHasRolesAcceso> subModulosGui = subQuery.from(ModulosGuiHasRolesAcceso.class);
			Join<ModulosGuiHasRolesAcceso, RolAcceso> subModulosRoles = subModulosGui.join("rolesAcceso",
					JoinType.INNER);

			subQuery.select(subModulosGui.get("idModulos"))
					.where(criteriaBuilder.equal(subModulosRoles.get("nombre"), rol)).distinct(true);

			criteriaQuery.multiselect(root.get("nombre"))
					.where(criteriaBuilder.in(root.get("id")).value(subQuery).not()).distinct(true);
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();
	}

	@Override
	@Transactional(readOnly = true)
	public List<ModuloDTO> getModuloPresentByRol(String rol) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ModuloDTO> criteriaQuery = criteriaBuilder.createQuery(ModuloDTO.class);
			Root<ModulosGui> root = criteriaQuery.from(ModulosGui.class);
			Join<ModulosGui, ModulosGuiHasRolesAcceso> joinSeccionModulosHasRoles = root
					.join("modulosGuiHasRolesAccesos", JoinType.INNER);
			Join<ModulosGuiHasRolesAcceso, RolAcceso> joinModulosRol = joinSeccionModulosHasRoles.join("rolesAcceso",
					JoinType.INNER);
			criteriaQuery.multiselect(root.get("nombre"))
					.where(criteriaBuilder.equal(joinModulosRol.get("nombre"), rol));
			return entityManager.createQuery(criteriaQuery).getResultList();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();
	}

	/**
	 * Busca todos los modulos, secciones, catalogo de iconos, pantallas asignadas y
	 * catalogo de servicios que han sido asignados a un modulo, se regresan
	 * resultados, SI Y SOLO SI, el modulo tiene seccion, la seccion tiene una
	 * pantalla asignada, la pantalla tiene un catalodo de servicio asignado
	 * 
	 * El tipo de busqueda se hace mientate JOIN INNER
	 **/

	@Override
	@Transactional(readOnly = true)
	public ModulosGui findAllInnerModuloGuiByName(String menuName) {
		try {
			EntityManager entityManager = (EntityManager) this.sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<ModulosGui> criteriaQuery = criteriaBuilder.createQuery(ModulosGui.class).distinct(true);
			Root<ModulosGui> root = criteriaQuery.from(ModulosGui.class);
			Fetch<ModulosGui, Seccion> fetchModuloSeccion = root.fetch("seccion", JoinType.INNER);
			Fetch<Seccion, PantallasAsignadas> fetchSeccionPantallas = fetchModuloSeccion.fetch("pantallasAsignadas",
					JoinType.INNER);
			Fetch<PantallasAsignadas, PantallaAsignadaHasCatalogoServicios> fetchPantaAsignadaPantallaCatalogo = fetchSeccionPantallas
					.fetch("pantallaAsignadaHasCatalogoServicios", JoinType.INNER);
			fetchPantaAsignadaPantallaCatalogo.fetch("catalogoServicios", JoinType.INNER);
			fetchSeccionPantallas.fetch("catalogoIconos", JoinType.INNER);
			fetchModuloSeccion.fetch("catalogoIconos", JoinType.INNER);
			root.fetch("catalogoIconos", JoinType.INNER);
			criteriaQuery.where(criteriaBuilder.equal(root.get("nombre"), menuName)).distinct(true);
			return entityManager.createQuery(criteriaQuery).getSingleResult();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	/*****************************************************************
	 * 
	 * first we check if the modulules of the aplication exist or not
	 * ***************************************************************/
	@Override
	@Transactional(readOnly=true)
	public boolean validateModules() {
		String query="select count(idModulosGui) from modulosgui ";
		PreparedStatement ps=null;
		Connection con =null;
		ResultSet rs=null;
		boolean result=false;
		try {
			con=dataSource.getConnection();
			ps=con.prepareStatement(query);
			rs=ps.executeQuery();
			if(rs.next()) {
				if(rs.getInt(1)>0) {
					SytecsoLogger.info("there is  data in the database no action nedded");
				}else {
					result=true;
					SytecsoLogger.info("there is no data in the database it will procced  to create the new data");
				}
			}else {
				result=true;
			}
		}catch (Exception e) {
			SytecsoLogger.error("An error cur during the search of modules", e);
		}finally {
			UtileriaSql.closeConection(con, ps, rs);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public long createIcon(String iconName,Connection con) {
		String query="INSERT INTO catalogoiconos (`nombreIcono`) VALUES (?) ";
		PreparedStatement ps=null;
		long resultado=0L;
		ResultSet rs=null;
		try {
			ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, iconName);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				resultado=rs.getLong(1);
			}
		}catch(Exception e){
			SytecsoLogger.error("Error creating icon", e);
		}finally {
			UtileriaSql.closePreparedStatemetAndResultSet(ps, rs);
		}
		return resultado;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean createModule( com.sytecso.config.menu.ModuloDTO modulo, Connection con) {
		String query="INSERT INTO modulosgui (`nombreModulo`,  `catalogoIconos_idcatalogoIconos`, `url`, `enabled`,posicion ) VALUES (?,?, ?,?,?) ";
		PreparedStatement ps=null;
		boolean resultado=true;
		ResultSet rs=null;
		long moduloId=0L;
		try {
			long icon=createIcon(modulo.getIcon(),con);
			ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, modulo.getMenuTitle());
			ps.setLong(2, icon);
			ps.setString(3, modulo.getUrl());
			ps.setBoolean(4, modulo.isEnabled());
			ps.setInt(5,modulo.getIndex());
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next()){
				moduloId=rs.getLong(1);
				ListIterator<SubMenuDTO> secciones =modulo.getSubMenu().listIterator();
				while(secciones.hasNext()) {
					if(!createSeccion(secciones.next(),con,moduloId)) {
						resultado=false;
						break;
					}
				}
			}else {
				resultado=false;
			}
		}catch(Exception e) {
			SytecsoLogger.error("Error al crear el módulo"+modulo.getMenuTitle(), e);
			resultado=false;
		}finally {
			UtileriaSql.closePreparedStatemetAndResultSet(ps, rs);
		}
		return resultado;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean createSeccion(SubMenuDTO seccion,Connection con,long idModulo) {
		String query="INSERT INTO seccion ( `nombre`, `ModulosGui_idModulosGui`, `catalogoIconos_idcatalogoIconos`, `url`, `enabled`, `posicion`) "
				+ "VALUES ( ?, ?, ?, ?, ?, ?)" ; 
		PreparedStatement ps=null;
		boolean resultado=true;
		ResultSet rs=null;
		long idSeccion=0L;
		try {
			long icon=createIcon(seccion.getIcon(),con);
			ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, seccion.getName());
			ps.setLong(2, idModulo);
			ps.setLong(3, icon);
			ps.setString(4, seccion.getUrl());
			ps.setBoolean(5, seccion.isEnabled());
			ps.setInt(6, seccion.getIndex());
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next()){
				idSeccion=rs.getLong(1);
				ListIterator<PantallaDTO> pantallas =seccion.getPantallas().listIterator();
				while(pantallas.hasNext()) {
					if(!createPantalla(pantallas.next(),con,idSeccion)) {
						resultado=false;
						break;
					}
				}
			}else {
				resultado=false;
			}
		}catch(Exception e) {
			SytecsoLogger.error("Error al crear la seccion"+seccion.getName(), e);
			resultado=false;
		}finally {
			UtileriaSql.closePreparedStatemetAndResultSet(ps, rs);
		}
		return resultado;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean createPantalla(PantallaDTO pantalla,Connection con,long idSeccion) {
		String query="INSERT INTO pantallas_asignadas (`nombrePantalla`, `seccion_idseccion`, `catalogoIconos_idcatalogoIconos`, `url`, `enabled`, `posicion`) "
				+ "VALUES (?, ?, ?, ?, ?, ?) " ; 
		PreparedStatement ps=null;
		boolean resultado=true;
		ResultSet rs=null;
		long idPantalla=0L;
		List<ServicioDTO> servicios=new ArrayList<ServicioDTO>();
		try {
			long icon=createIcon(pantalla.getIcon(),con);
			ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, pantalla.getName());
			ps.setLong(2,idSeccion);
			ps.setLong(3, icon);
			ps.setString(4,pantalla.getUrl());
			ps.setBoolean(5, pantalla.isEnabled());
			ps.setInt(6, pantalla.getIndex());
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if(rs.next()){
				idPantalla=rs.getLong(1);
				StringTokenizer st = new StringTokenizer(pantalla.getServicios(),","); 
				while (st.hasMoreTokens()) {
					ServicioDTO servicio = new ServicioDTO();
					servicio.setNombreServicio(st.nextToken());
					servicios.add(servicio); 
			     }
				if(!createCatalogoServicios(servicios,con,idPantalla)) {
					resultado=false;
				}
			}else {
				resultado=false;
			}
		
		}catch(Exception e) {
			SytecsoLogger.error("Error al crear la pantalla"+pantalla.getName(), e);
			resultado=false;
		}finally {
			UtileriaSql.closePreparedStatemetAndResultSet(ps, rs);
		}
		return resultado;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean createCatalogoServicios(List<ServicioDTO> servicios,Connection con,long idPantalla) {
		String query="INSERT INTO catalogoServicios (`nombreServicio`) VALUES (?) "; 
		boolean resultado=true;
		ListIterator<ServicioDTO> serviciosIterator =servicios.listIterator();
		while(serviciosIterator.hasNext()) {
			ServicioDTO servicio=serviciosIterator.next();
			PreparedStatement ps=null;
			ResultSet rs=null;
			long idServicio=0L;
			try {
				ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, servicio.getNombreServicio());
				ps.executeUpdate();
				rs=ps.getGeneratedKeys();
				if(rs.next()) {
					idServicio=rs.getLong(1);
					if(!creaServiciosHasPantalla(idPantalla,idServicio,con)) {
						resultado=false;
						break;
					}
				}else {
					resultado=false;
					break;
				}
				
			}catch(Exception e) {
				SytecsoLogger.error("Error al crear el servicio"+servicio.getNombreServicio(), e);
				resultado=false;
			}finally {
				UtileriaSql.closePreparedStatemetAndResultSet(ps, rs);
			}
		}
		return resultado;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean creaServiciosHasPantalla(long idPantalla, long idServicio,Connection con) {
		String query="INSERT INTO pantallas_asignadas_has_catalogoservicios (`pantallas_asignadas_pantallas_asignadasId`, `catalogoServicios_idcatalogoServicios`) "
				+ "VALUES (?, ?) ";
		PreparedStatement ps=null;
		boolean resultado=true;
		try {
			ps=con.prepareStatement(query);
			ps.setLong(1, idPantalla);
			ps.setLong(2, idServicio);
			ps.execute();
		}catch(Exception e) {
			SytecsoLogger.error("Error al crear el servicio la relacion de las pantallas asignadas", e);
			resultado=false;
		}finally {
			UtileriaSql.closePreparedStatement(ps);
		}
		return resultado;
	}	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean createMenu(MenuDTO menu) throws SQLException {
		Connection con=null;
		boolean resultado=true;
		try {
			con=dataSource.getConnection();
			con.setAutoCommit(false);
			ListIterator< com.sytecso.config.menu.ModuloDTO> modulosList=menu.getModulos().listIterator();
			while(modulosList.hasNext()) {
				if(!createModule(modulosList.next(),con)) {
					con.rollback();
					break;
				}
			}
		}catch(Exception e){
			SytecsoLogger.error("Error en la creación del menu", e);
			resultado=false;
			con.rollback();
		}finally {
			con.setAutoCommit(true);
			UtileriaSql.closeConnection(con);
		}
		return resultado;
	}


}
