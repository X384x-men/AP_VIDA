package com.sytecso.dao.usuario.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sytecso.component.utility.UtileriaCifrado;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.config.logger.SytecsoLogger;
import com.sytecso.dao.usuario.DAOUsuarioAcceso;
import com.sytecso.component.DefaultAdminAP;
import com.sytecso.component.exceptions.CuadrillasException;
import com.sytecso.component.exceptions.CuadrillasException.NotUserFoundException;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.exceptions.UsuarioAccesoException;
import com.sytecso.component.exceptions.UsuarioAccesoException.UsuarioNotExistsException;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.UsuarioAcceso;
import com.sytecso.security.profile.ProfileAccess;
import com.sytecso.dto.EmailBody;
import com.sytecso.dto.empleado.EmpleadoAPDTO;
import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.dto.usuarioacceso.UsuarioAccesoDTO;

@Repository
@Transactional
public class DAOUsuarioAccesoImpl implements DAOUsuarioAcceso {
	private static final String FIND_PERFILES = "SELECT idrolesAcceso, DescripcionRol, getAliasRolAcceso(nombreRol) AS nombreRol FROM rolesacceso WHERE filterRolByRolAcceso(nombreRol)";
	private static final String FIND_PERFIL_BY_ID = "SELECT idrolesAcceso, DescripcionRol, getAliasRolAcceso(nombreRol) AS nombreRol FROM rolesacceso WHERE idRolesAcceso=?";
	@Autowired
	@Qualifier("access")
	private ProfileAccess profile;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	DefaultAdminAP adminAP;
	
	
	@Override
	@Transactional(readOnly = true)
	public boolean usuarioExists(String usuario) {
		try {
			EntityManager entityManager = (EntityManager) sessionFactory.getCurrentSession();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioAcceso> query = cb.createQuery(UsuarioAcceso.class);
			Root<UsuarioAcceso> root = query.from(UsuarioAcceso.class);
			query.where(cb.equal(root.get("usuario"), usuario)).distinct(true);
			return entityManager.createQuery(query).getSingleResult() != null;
		} catch (NoResultException e) {
			SytecsoLogger.info("El usuario " + usuario + " no existe");
			return false;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioAccesoDTO findUserByUserNameAndPassword(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException {
		try {
			EntityManager entityManager = (EntityManager) sessionFactory.getCurrentSession();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioAccesoDTO> query = cb.createQuery(UsuarioAccesoDTO.class);
			Root<UsuarioAcceso> root = query.from(UsuarioAcceso.class);
			query.multiselect(root.get("usuario"), root.get("password"))
					.where(cb.equal(root.get("usuario"), usuario.getUsuario())).distinct(true);
			UsuarioAccesoDTO user = entityManager.createQuery(query).getSingleResult();
			if (UtileriaCifrado.validaPassword(usuario.getPassword(), user.getPassword()))
				return user;
		} catch (NoResultException e) {
			SytecsoLogger.info("El usuario " + usuario + " no existe");
		}
		throw new UsuarioAccesoException.UsuarioNotExistsException(
				"El usuario ".concat(usuario.getUsuario()).concat(" no existe"));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePasswordByUserName(UsuarioAccesoDTO usuario)  {
		boolean status = false;
		String sql = " UPDATE usuariosAcceso SET pwd=? WHERE usuario =?";
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, UtileriaCifrado.getMD5(usuario.getPassword()));
			pst.setString(2, usuario.getUsuario());
			pst.executeUpdate();
			status = true;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst);
		}
		return status;
	}

	@Override
	@Transactional(readOnly = true)
	public UsuarioAccesoDTO findUserByUserName(UsuarioAccesoDTO usuario) throws UsuarioNotExistsException {
		try {
			EntityManager entityManager = (EntityManager) sessionFactory.getCurrentSession();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioAccesoDTO> query = cb.createQuery(UsuarioAccesoDTO.class);
			Root<UsuarioAcceso> root = query.from(UsuarioAcceso.class);
			query.multiselect(root.get("usuario"), root.get("password"))
					.where(cb.equal(root.get("usuario"), usuario.getUsuario())).distinct(true);
			return entityManager.createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			SytecsoLogger.info("El usuario " + usuario + " no existe");
		}
		throw new UsuarioAccesoException.UsuarioNotExistsException(
				"El usuario ".concat(usuario.getUsuario()).concat(" no existe"));
	}
	

	@Override
	public boolean usuarioExists(String usuario, String profile) {
		try {
			EntityManager entityManager = (EntityManager) sessionFactory.getCurrentSession();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioAcceso> query = cb.createQuery(UsuarioAcceso.class);
			Root<UsuarioAcceso> root = query.from(UsuarioAcceso.class);
			Join<UsuarioAcceso, RolAcceso> joinUsuarioRol = root.join("rolesAcceso", JoinType.INNER);
			query.where(cb.equal(root.get("usuario"), usuario), cb.and(cb.equal(joinUsuarioRol.get("nombre"), profile)))
					.distinct(true);
			return entityManager.createQuery(query).getSingleResult() != null;
		} catch (NoResultException e) {
			SytecsoLogger
					.info("El usuario: ".concat(usuario).concat(" con el rol: ").concat(profile).concat(" no existe"));
			return false;
		}
	}

	@Override
	public List<UsuarioAccesoDTO> findUsuariosAccesoByTypeUser(int userType) throws NotUserFoundException {
		try {
			EntityManager entityManager = (EntityManager) sessionFactory.getCurrentSession();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioAccesoDTO> query = cb.createQuery(UsuarioAccesoDTO.class);
			Root<UsuarioAcceso> root = query.from(UsuarioAcceso.class);
			query.multiselect(root.get("usuario").alias("usuario")).where(cb.equal(root.get("tipoAcceso"), userType))
					.distinct(true);
			return entityManager.createQuery(query).getResultList();
		} catch (NoResultException e) {
			SytecsoLogger.info("Ocurrio un error con la busqueda de usuarios");
		}
		throw new CuadrillasException.NotUserFoundException("No se han encontrado usuarios");

	}

	@Override
	public UsuarioAccesoDTO findUserByUserName(String user) throws UsuarioNotExistsException {
		try {
			EntityManager entityManager = (EntityManager) sessionFactory.getCurrentSession();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<UsuarioAccesoDTO> query = cb.createQuery(UsuarioAccesoDTO.class);
			Root<UsuarioAcceso> root = query.from(UsuarioAcceso.class);
			query.multiselect(root.get("idUsuariosAcceso"), root.get("usuario"))
					.where(cb.equal(root.get("usuario"), user)).distinct(true);
			return entityManager.createQuery(query).getSingleResult();
		} catch (NoResultException e) {
			SytecsoLogger.info("El usuario " + user + " no existe");
		}
		throw new UsuarioAccesoException.UsuarioNotExistsException("El usuario ".concat(user).concat(" no existe"));
	}

	



	

	@Override
	public List<RolAccesoDTO> getPerfiles() {
		RolAccesoDTO rol = null;
		List<RolAccesoDTO> listRol = new ArrayList<>();
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(FIND_PERFILES);
			rs = pst.executeQuery();
			while (rs.next()) {
				rol = new RolAccesoDTO();
				rol.setIdrol(rs.getLong(1));
				rol.setDescripcion(rs.getString(2));
				rol.setNombre(rs.getString(3));
				listRol.add(rol);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return listRol;
	}

	@Override
	public RolAccesoDTO getRolbyID(long idRolesAcceso) {
		RolAccesoDTO rol = null;

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(FIND_PERFIL_BY_ID);
			pst.setLong(1, idRolesAcceso);
			rs = pst.executeQuery();
			while (rs.next()) {
				rol = new RolAccesoDTO();
				rol.setIdrol(rs.getLong(1));
				rol.setDescripcion(rs.getString(2));
				rol.setNombre(rs.getString(3));
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return rol;
	}





	@Override
	public boolean updatePasswordByUserNameEmpleado(com.sytecso.dto.usuarioacceso.UsuarioAcceso usuario) {
		boolean status = false;
		String sql = " UPDATE usuariosAcceso SET pwd=? WHERE idusuariosAcceso =?";
		Connection con = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, UtileriaCifrado.getMD5(usuario.getPwd()));
			pst.setLong(2, usuario.getIdusuariosAcceso());
			pst.executeUpdate();
			status = true;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst);
		}
		return status;
	}

	
	


	@Override
	public long findidUsuarioAccesobyidUsuario(long idUsuario) {
		long idUsuarioAcceso = 0;
		String sql = "select idusuariosAcceso from usuariosacceso ua, rolesacceso r "
				+ "where ua.rolesAcceso_idrolesAcceso=r.idrolesAcceso " + "and au.Usuario_idUsuario=?";

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setLong(1, idUsuario);
			rs = pst.executeQuery();
			while (rs.next()) {
				idUsuarioAcceso = rs.getLong("idusuariosAcceso");
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return idUsuarioAcceso;
	}

	@Transactional(rollbackFor = Exception.class)
	public boolean updateEmpleadoUsuarioAP(EmpleadoAPDTO empleado) {
		long key = -1L;
		boolean status = true;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String updateEmpleado = "UPDATE usuariosacceso SET pwd=?, tipoAcceso=?, rolesAcceso_idrolesAcceso=? WHERE usuario=?";

		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			preparedStatement = con.prepareStatement(updateEmpleado, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, UtileriaCifrado.getMD5(empleado.getPsw()));
			preparedStatement.setInt(2, 11);
			if(empleado.getTipoAnalista() == 0) {
				preparedStatement.setInt(3, 2);				
			}else {
				preparedStatement.setInt(3, (empleado.getTipoAnalista() + 3));	
			}
			preparedStatement.setString(4, empleado.getRfc());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnectionAndCommit(con, preparedStatement, rs, status);
		}
		return status;

	}

	@Override
	@Transactional(rollbackFor = Exception.class) //here
	public boolean updateEmpleadoData(EmpleadoAPDTO empleado) {
		long key = -1L;
		boolean status = true;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String updateEmpleado = "UPDATE empleado_ap SET sexo=?, rfc=?, numeroEmpleado=?, dependencia=?, unidadAdministrativa=?, email=?, nombre=?, apellidoP=?, apellidoM=? WHERE idEmpleadoAP=?";

		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			preparedStatement = con.prepareStatement(updateEmpleado, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, empleado.getSexo());
			preparedStatement.setString(2, empleado.getRfc());
			preparedStatement.setString(3, empleado.getNoEmpleado());
			preparedStatement.setString(4, empleado.getDependencia());
			preparedStatement.setString(5, empleado.getUnidadAdministrativa());
			preparedStatement.setString(6, empleado.getMail());
			preparedStatement.setString(7, empleado.getNombre());
			preparedStatement.setString(8, empleado.getApellidoPaterno());
			preparedStatement.setString(9, empleado.getApellidoMaterno());
			preparedStatement.setLong(10, empleado.getIdEmpleado());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnectionAndCommit(con, preparedStatement, rs, status);
		}
		return status;

	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveEmpleadoUsuarioAP(EmpleadoAPDTO empleado) {
		long key = -1L;
		boolean status = true;
		Connection con = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String insert_Empleado = "Insert into usuariosacceso (usuario, pwd, tipoAcceso, rolesAcceso_idrolesAcceso) VALUES (?,?,?,?)";

		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			preparedStatement = con.prepareStatement(insert_Empleado, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, empleado.getRfc());
			preparedStatement.setString(2, UtileriaCifrado.getMD5(empleado.getPsw()));
			preparedStatement.setInt(3, 11);
			if(empleado.getTipoAnalista() == 0) {
				preparedStatement.setInt(4, 2);				
			}else {
				preparedStatement.setInt(4, (empleado.getTipoAnalista() + 3));	
			}
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getLong(1);
			}
			if (key > 0) {
				empleado.setIdUsuarioAcceso(key);
				status = createEmpleadoAP(con, empleado);
				
			}
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConnectionAndCommit(con, preparedStatement, rs, status);
		}
		return status;

	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public boolean createEmpleadoAP(Connection connection, EmpleadoAPDTO empleado) {
		boolean status = true;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String insert_usuario = "Insert into empleado_ap (nombre, apellidoP, apellidoM, sexo, "
				+ " rfc, numeroEmpleado, " + 
				" fechaNacimiento, UsuarioAcceso_idUsuarioAcceso, email, estatus, fechaCambioEstatus, tipoAnalista,unidad_fk,dependencia_fk ) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,1,?,?,?,?)";

		try {
			preparedStatement = connection.prepareStatement(insert_usuario, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, empleado.getNombre());
			preparedStatement.setString(2, empleado.getApellidoPaterno());
			preparedStatement.setString(3, empleado.getApellidoMaterno());
			preparedStatement.setString(4, empleado.getSexo());
			preparedStatement.setString(5, empleado.getRfc());
			preparedStatement.setString(6, empleado.getNoEmpleado());
			/*if(empleado.getDependencia().equals("")) {
				preparedStatement.setString(7, null);
			}else {
				preparedStatement.setString(7, empleado.getDependencia());				
			}*/
			if(empleado.getFechaNacimiento().equals("")) {
				preparedStatement.setString(7, null);
			}else {
				preparedStatement.setString(7, empleado.getFechaNacimiento());				
			}/*
			if(empleado.getUnidadAdministrativa().equals("")) {
				preparedStatement.setString(9, null);
			}else {
				preparedStatement.setString(9, empleado.getUnidadAdministrativa());				
			}*/
			
			preparedStatement.setLong(8, empleado.getIdUsuarioAcceso());
			preparedStatement.setString(9, empleado.getMail());
			preparedStatement.setString(10, empleado.getFechaCambioEstatus());
			if(empleado.getTipoAnalista() == 0) {
				preparedStatement.setString(11, null);
			}else {
				preparedStatement.setInt(11, empleado.getTipoAnalista());				
			}
			
			if(empleado.getIdUnidad()==0) {
				preparedStatement.setInt(12, (Integer) null);
			}else {
				preparedStatement.setInt(12, empleado.getIdUnidad());				
			}
			if(empleado.getIdidDependencia()==0) {
				preparedStatement.setInt(13, (Integer) null);
			}else {
				preparedStatement.setInt(13, empleado.getIdidDependencia());				
			}
			
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closePreparedStatemetAndResultSet(preparedStatement, rs);
		}
		return status;
	}
	
	@Override
	public EmpleadoAPDTO getUsuarioAP(String rfc) {
		EmpleadoAPDTO empleado = new EmpleadoAPDTO();
		String sql = 
				"SELECT usuario, idusuariosAcceso FROM usuariosacceso WHERE usuario = ? ";

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, rfc);
			rs = pst.executeQuery();
			while (rs.next()) {
				empleado.setRfc(rs.getString("usuario"));
				empleado.setIdUsuarioAcceso(rs.getLong("idusuariosAcceso"));
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return empleado;
	}
	
	
	@Override
	public EmpleadoAPDTO getEmpleadoAP(String usr) {
		EmpleadoAPDTO empleado = new EmpleadoAPDTO();
		String sql = 
				"select * from ( "
				+ "SELECT e.nombre, e.apellidoP, e.apellidoM, e.sexo, e.calle, e.colonia, e.noInterior, e.noExterior, e.codigoPostal, e.rfc, e.curp, e.telefonoCasa, e.telefonoMovil, e.numeroEmpleado, "
				+ "e.dependencia, e.fechaNacimiento, e.unidadAdministrativa, e.fechaIncorporacionSeguro, e.banco, e.cuenta, "
				+ " e.email, e.UsuarioAcceso_idUsuarioAcceso, e.idEmpleadoAP, e.estatus, e.fechaCambioEstatus, e.tipoAnalista, "
				+ " unidad.idCatalogoUnidadAdministrativa as idUnidad,unidad.descripcion as descUni, dependenci.idCatalogoDependencias as idDependencia, dependenci.descripcion as descDepen,  ua.usuario as usuarioAcceso "
				+ "FROM empleado_ap e, usuariosacceso ua,  catalogoUnidadAdministrativa  unidad, catalogoDependencias dependenci "
				+ "WHERE e.UsuarioAcceso_idUsuarioAcceso = ua.idusuariosAcceso "
				+ "and  e.unidad_fk = unidad.idCatalogoUnidadAdministrativa "
				+ "and  e.dependencia_fk = dependenci.idCatalogoDependencias "
				+ " and  ua.usuario='"+usr+"' "
				+ "union "
				+ "SELECT e.nombre, e.apellidoP, e.apellidoM, e.sexo, e.calle, e.colonia, e.noInterior, e.noExterior, e.codigoPostal, e.rfc, e.curp, e.telefonoCasa, e.telefonoMovil, e.numeroEmpleado, "
				+ "e.dependencia, e.fechaNacimiento, e.unidadAdministrativa, e.fechaIncorporacionSeguro, e.banco, e.cuenta, "
				+ " e.email, e.UsuarioAcceso_idUsuarioAcceso, e.idEmpleadoAP, e.estatus, e.fechaCambioEstatus, e.tipoAnalista, "
				+ " e.unidad_fk as idUnidad ,'' as descUni, dependenci.idCatalogoDependencias as idDependencia, dependenci.descripcion as descDepen, ua.usuario as usuarioAcceso "
				+ "FROM empleado_ap e, usuariosacceso ua,   catalogoDependencias dependenci  "
				+ "WHERE e.UsuarioAcceso_idUsuarioAcceso = ua.idusuariosAcceso "
				+ "and  e.unidad_fk is null "
				+ "and  e.dependencia_fk = dependenci.idCatalogoDependencias "
				+ " and  ua.usuario='"+usr+"' "
				+ "union "
				+ "SELECT e.nombre, e.apellidoP, e.apellidoM, e.sexo, e.calle, e.colonia, e.noInterior, e.noExterior, e.codigoPostal, e.rfc, e.curp, e.telefonoCasa, e.telefonoMovil, e.numeroEmpleado, "
				+ "e.dependencia, e.fechaNacimiento, e.unidadAdministrativa, e.fechaIncorporacionSeguro, e.banco, e.cuenta,"
				+ " e.email, e.UsuarioAcceso_idUsuarioAcceso, e.idEmpleadoAP, e.estatus, e.fechaCambioEstatus, e.tipoAnalista, "
				+ "unidad.idCatalogoUnidadAdministrativa as idUnidad,unidad.descripcion as descUni, e.dependencia_fk as idDependencia, '' as descDepen, ua.usuario as usuarioAcceso "
				+ "FROM empleado_ap e, usuariosacceso ua,catalogoUnidadAdministrativa  unidad,   catalogoDependencias dependenci "
				+ "WHERE e.UsuarioAcceso_idUsuarioAcceso = ua.idusuariosAcceso "
				+ "and  e.unidad_fk = unidad.idCatalogoUnidadAdministrativa "
				+ "and  e.dependencia_fk  is null "
				+ " and  ua.usuario='"+usr+"' "
				+ "union "
				+ "SELECT e.nombre, e.apellidoP, e.apellidoM, e.sexo, e.calle, e.colonia, e.noInterior, e.noExterior, e.codigoPostal, e.rfc, e.curp, e.telefonoCasa, e.telefonoMovil, e.numeroEmpleado,  "
				+ "e.dependencia, e.fechaNacimiento, e.unidadAdministrativa, e.fechaIncorporacionSeguro, e.banco, e.cuenta, "
				+ " e.email, e.UsuarioAcceso_idUsuarioAcceso, e.idEmpleadoAP, e.estatus, e.fechaCambioEstatus, e.tipoAnalista, "
				+ "e.unidad_fk as idUnidad,'' as descUni, e.dependencia_fk as idDependencia, '' as descDepen, ua.usuario as usuarioAcceso "
				+ "FROM empleado_ap e, usuariosacceso ua,catalogoUnidadAdministrativa  unidad,   catalogoDependencias dependenci  "
				+ "WHERE e.UsuarioAcceso_idUsuarioAcceso = ua.idusuariosAcceso "
				+ "and  e.unidad_fk is null "
				+ "and  e.dependencia_fk  is null "
				+ " and  ua.usuario='"+usr+"' "
				+ ") as Usuarios ";

		System.out.println(sql);
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				empleado.setNombre(rs.getString(1));
				empleado.setApellidoPaterno(rs.getString(2));
				empleado.setApellidoMaterno(rs.getString(3));
				empleado.setSexo(rs.getString(4));
				//empleado.setCalle(rs.getString("calle"));
				//empleado.setColonia(rs.getString("colonia"));
				//empleado.setNoInt(rs.getInt("noInterior"));
				//empleado.setNoExt(rs.getInt("noExterior"));
				//empleado.setCp(rs.getInt("codigoPostal"));
				empleado.setRfc(rs.getString(10));
				//empleado.setCurp(rs.getString("curp"));
				//empleado.setTelCasa(rs.getInt("telefonoCasa"));
				//empleado.setTelMovil(rs.getInt("telefonoMovil"));
				empleado.setNoEmpleado(rs.getString(14));
				empleado.setDependencia(rs.getString(15));
				empleado.setFechaNacimiento(rs.getString(16));
				empleado.setUnidadAdministrativa(rs.getString(17));
				//empleado.setFechaIngresoSeguro(rs.getString("fechaIncorporacionSeguro"));
				//empleado.setBanco(rs.getString("banco"));
				//empleado.setCuenta(rs.getLong("cuenta"));
				empleado.setMail(rs.getString(21));
				empleado.setIdUsuarioAcceso(rs.getLong(22));
				empleado.setIdEmpleado(rs.getLong(23));
				empleado.setEstatus(rs.getInt(24));
				empleado.setFechaCambioEstatus(rs.getString(25));
				empleado.setTipoAnalista(rs.getInt(26));
				empleado.setIdUnidad(rs.getInt(27));
				empleado.setUnidadCatalogo(rs.getString(28));
				empleado.setIdidDependencia(rs.getInt(29));
				empleado.setDependenciaCatalogo(rs.getString(30));
				System.out.println(empleado);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return empleado;
	}
	
	@Override
	public EmpleadoAPDTO getEmpleadoAPById(long idEmpleado) {
		EmpleadoAPDTO empleado = new EmpleadoAPDTO();
		String sql = 
				"SELECT e.nombre, e.apellidoP, e.apellidoM, e.sexo, e.calle, e.colonia, e.noInterior, e.noExterior, e.codigoPostal, e.rfc, e.curp, e.telefonoCasa, e.telefonoMovil, e.numeroEmpleado, " + 
				"e.dependencia, e.fechaNacimiento, e.unidadAdministrativa, e.fechaIncorporacionSeguro, e.banco, e.cuenta, e.email, e.UsuarioAcceso_idUsuarioAcceso, e.idEmpleadoAP, e.estatus, e.fechaCambioEstatus, e.tipoAnalista " + 
				"FROM empleado_ap e, usuariosacceso ua WHERE e.UsuarioAcceso_idUsuarioAcceso = ua.idusuariosAcceso AND e.idEmpleadoAP = ? ";

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setLong(1, idEmpleado);
			rs = pst.executeQuery();
			while (rs.next()) {
				empleado.setNombre(rs.getString("nombre"));
				empleado.setApellidoPaterno(rs.getString("apellidoP"));
				empleado.setApellidoMaterno(rs.getString("apellidoM"));
				empleado.setSexo(rs.getString("sexo"));
				//empleado.setCalle(rs.getString("calle"));
				//empleado.setColonia(rs.getString("colonia"));
				//empleado.setNoInt(rs.getInt("noInterior"));
				//empleado.setNoExt(rs.getInt("noExterior"));
				//empleado.setCp(rs.getInt("codigoPostal"));
				empleado.setRfc(rs.getString("rfc"));
				//empleado.setCurp(rs.getString("curp"));
				//empleado.setTelCasa(rs.getInt("telefonoCasa"));
				//empleado.setTelMovil(rs.getInt("telefonoMovil"));
				empleado.setNoEmpleado(rs.getString("numeroEmpleado"));
				empleado.setDependencia(rs.getString("dependencia"));
				empleado.setFechaNacimiento(rs.getString("fechaNacimiento"));
				empleado.setUnidadAdministrativa(rs.getString("unidadAdministrativa"));
				//empleado.setFechaIngresoSeguro(rs.getString("fechaIncorporacionSeguro"));
				//empleado.setBanco(rs.getString("banco"));
				//empleado.setCuenta(rs.getLong("cuenta"));
				empleado.setMail(rs.getString("email"));
				empleado.setIdUsuarioAcceso(rs.getLong("UsuarioAcceso_idUsuarioAcceso"));
				empleado.setIdEmpleado(rs.getLong("idEmpleadoAP"));
				empleado.setEstatus(rs.getInt("estatus"));
				empleado.setFechaCambioEstatus(rs.getString("fechaCambioEstatus"));
				empleado.setTipoAnalista(rs.getInt("tipoAnalista"));
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return empleado;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateEmpleadoAP(EmpleadoAPDTO empleado) {
		System.out.println("update data");
		boolean status = true;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection con = null;
		String insert_usuario = "UPDATE empleado_ap SET nombre = ?, apellidoP = ?, apellidoM = ?, sexo = ?, " + 
				"numeroEmpleado = ?, fechaNacimiento = ?,  email = ?, tipoAnalista = ?, dependencia_fk=?, unidad_fk=? " + 
				"WHERE idEmpleadoAP = ? ";

		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement(insert_usuario, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, empleado.getNombre());
			preparedStatement.setString(2, empleado.getApellidoPaterno());
			preparedStatement.setString(3, empleado.getApellidoMaterno());
			preparedStatement.setString(4, empleado.getSexo());
			preparedStatement.setString(5, empleado.getNoEmpleado());
			preparedStatement.setString(6, empleado.getFechaNacimiento());
			preparedStatement.setString(7, empleado.getMail());
			if(empleado.getTipoAnalista() == 0) {
				preparedStatement.setString(8, null);
			}else {
				preparedStatement.setInt(8, empleado.getTipoAnalista());				
			}
			preparedStatement.setInt(9,empleado.getIdidDependencia());
			preparedStatement.setInt(10, empleado.getIdUnidad());
			preparedStatement.setLong(11, empleado.getIdEmpleado());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			
			System.out.println("Actualizando DB ");
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, preparedStatement, rs);
		}
		return status;
	}
	
	@Override
	@Transactional(readOnly = true)
	public String nombreUsuariobyUser(String user) {
		String nombreUsuario="";
		String sql = "select COALESCE(concat(e.Nombre, ' ',e.apellidoP, ' ',e.apellidoM), e.nombre) as nombre      " + 
				" from usuariosacceso usr, empleado_ap e     " + 
				" where e.UsuarioAcceso_idUsuarioAcceso = usr.idusuariosAcceso and usr.usuario=?  ";
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, user);
			rs = pst.executeQuery();
			while (rs.next()) {
				nombreUsuario=rs.getString(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return nombreUsuario;
	}


	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updatePwdEmpleadoAP(EmpleadoAPDTO empleado) {
		boolean status = true;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection con = null;
		String update_usuario = "UPDATE usuariosacceso SET pwd = ? WHERE usuario = ? " ;

		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement(update_usuario, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, UtileriaCifrado.getMD5(empleado.getPsw()));
			preparedStatement.setString(2, empleado.getRfc());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, preparedStatement, rs);
		}
		return status;
	}
	
	public EmpleadoAPDTO getPwdEmpleadoAP(String nameUsuario) {
		EmpleadoAPDTO empleado = new EmpleadoAPDTO();
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		String sql = "  select usrAcc.pwd from usuariosacceso usrAcc where usrAcc.usuario= ?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, nameUsuario);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				empleado.setPsw(rs.getString("pwd"));
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, preparedStatement, rs);
		}
		return empleado;
	}

	@Override
	public EmpleadoAPDTO getEmpleadoAPbyRFC(String rfc) {
		EmpleadoAPDTO empleado = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		String sql = "select e.idEmpleadoAP, concat(e.Nombre, ' ',e.ApellidoP, ' ',e.ApellidoM) as empleado, e.rfc,u.idusuariosAcceso, e.email " + 
				"from empleado_ap e, usuariosacceso u  " + 
				// "where e.UsuarioAcceso_idUsuarioAcceso=u.idusuariosAcceso " + 
				"where e.rfc=?";

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, rfc);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				empleado=new EmpleadoAPDTO();
				empleado.setIdEmpleado(rs.getLong(1));
				empleado.setNombre(rs.getString(2));
				empleado.setRfc(rs.getString(3));
				empleado.setIdUsuarioAcceso(rs.getLong(4));
				empleado.setMail(rs.getString(5));
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, preparedStatement, rs);
		}
		return empleado;
	}

	@Override
	public boolean emailExits(EmailBody email) {
		boolean status= false;
		String sql = "select e.idEmpleadoAP, concat(e.Nombre, ' ',e.ApellidoP, ' ',e.ApellidoM) as empleado, e.rfc,u.idusuariosAcceso, e.email " + 
				"from empleado_ap e, usuariosacceso u  " + 
				"where e.UsuarioAcceso_idUsuarioAcceso=u.idusuariosAcceso " + 
				"and e.rfc=? and e.email =? ";

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, email.getRfc());
			pst.setString(2, email.getEmail());
			rs = pst.executeQuery();
			while (rs.next()) {
				status=true;
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return status;
	}

	@Override
	public String getEmail(String rfc) {
		String email=null;
		String sql = "select  e.email " + 
				"from empleado_ap e, usuariosacceso u  " + 
				"where e.UsuarioAcceso_idUsuarioAcceso=u.idusuariosAcceso " + 
				"and e.rfc=?  ";

		Connection con = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			con = dataSource.getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, rfc);
			rs = pst.executeQuery();
			while (rs.next()) {
				email=rs.getString(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, pst, rs);
		}
		return email;
	}

	@Override
	public boolean updateEmailbyRFC(EmailBody emailBody) {
		boolean status = true;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection con = null;
		String sql = "UPDATE empleado_ap SET email = ? WHERE rfc = ?"  ;

		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, emailBody.getEmail());
			preparedStatement.setString(2, emailBody.getRfc());
			preparedStatement.executeUpdate();
			
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, preparedStatement, rs);
		}
		return status;
	}

	@Override
	public List<EmpleadoAPDTO> getBusquedaEmpleadosAP(String condicion) {
		List<EmpleadoAPDTO> empleados= new ArrayList<EmpleadoAPDTO>();
		EmpleadoAPDTO em = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		/*String sql = "select e.nombre, e.apellidoP,e.apellidoM,e.sexo,e.rfc, e.numeroEmpleado,e.email, e.dependencia,e.unidadadministrativa,e.fechanacimiento,e.fechaCreacion, e.estatus, e.fechaCambioEstatus, e.idEmpleadoAP, e.tipoAnalista " + 
				" from empleado_ap e, usuariosacceso u " + 
				" where e.UsuarioAcceso_idUsuarioAcceso=u.idusuariosAcceso "+condicion;*/
		String sql = " select * from empleados ";
		if(!condicion.isEmpty()) {
			if(condicion.startsWith("and"))
				condicion=condicion.substring(3);
			sql =sql+"where "+condicion;
		}
		System.out.println(sql);
		

		try {
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				em=new EmpleadoAPDTO();
				em.setNombre(rs.getString(1));
				em.setApellidoPaterno(rs.getString(2));
				em.setApellidoMaterno(rs.getString(3));
				em.setSexo(rs.getString(4));
				em.setRfc(rs.getString(5));
				em.setNoEmpleado(rs.getString(6));
				em.setMail(rs.getString(7));
				em.setFechaNacimiento(rs.getString(8));
				em.setFechaCreacion(rs.getString(9));
				em.setEstatus(rs.getInt(10));
				em.setFechaCambioEstatus(rs.getString(11));
				em.setIdEmpleado(rs.getLong(12));
				em.setTipoAnalista(rs.getInt("tipoAnalista"));
				em.setIdUnidad(rs.getInt(14));
				em.setUnidadCatalogo(rs.getString(15));
				em.setIdidDependencia(rs.getInt(16));
				em.setDependenciaCatalogo(rs.getString(17));
				empleados.add(em);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, preparedStatement, rs);
		}
		return empleados;
	}

	

	

	@Transactional(rollbackFor = Exception.class)
	public long createEmpleadoAPDefault(UsuarioAcceso usuarioAcceso,Connection con) {
		long id=0L;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String sql = "INSERT INTO empleado_ap (nombre,apellidop,apellidom, rfc, UsuarioAcceso_idUsuarioAcceso,fechaNacimiento) VALUES ('Admin','','','XAXX010101000',?, NOW())";
		try {
			preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setLong(1, usuarioAcceso.getId());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			
			if (rs.next()) {
				id = rs.getLong(1);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} 
		return id;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long createUsuarioAcceso(UsuarioAcceso usuarioAcceso) throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		String query = "INSERT INTO usuariosacceso (usuario, pwd,tipoAcceso, rolesAcceso_idrolesAcceso)"
				+ "VALUES (?,?,?,?)";

		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			ps=con.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, usuarioAcceso.getUsuario());
			ps.setString(2, usuarioAcceso.getPassword());
			ps.setString(3, usuarioAcceso.getTipoAcceso());
			ps.setLong(4, usuarioAcceso.getRolesAcceso().getId());
			System.out.println(ps);
			ps.executeUpdate();
			rs=ps.getGeneratedKeys();
			if (rs.next()) {
				usuarioAcceso.setId(rs.getLong(1));
				if(usuarioAcceso.getRolesAcceso().getNombre().contentEquals(adminAP.getNombreRolAdminAP())) {
					if(createEmpleadoAPDefault(usuarioAcceso,con)>0) {
						con.commit();
					}else {
						con.rollback();
					}
				}else {
					con.commit();
				}
			}
		} catch (Exception e) {
			con.rollback();
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			con.setAutoCommit(true);
			UtileriaSql.closePreparedStatemetAndResultSet(ps, rs);
		}
		return usuarioAcceso.getId();
	}
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateEstatus(EmpleadoAPDTO empleado) {
		boolean status = true;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Connection con = null;
		String insert_usuario = "UPDATE empleado_ap SET estatus = ?, fechaCambioEstatus = ? " + 
		"WHERE idEmpleadoAP = ? ";

		try {
			con = dataSource.getConnection();
			preparedStatement = con.prepareStatement(insert_usuario, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, empleado.getEstatus());
			preparedStatement.setString(2, empleado.getFechaCambioEstatus());
			preparedStatement.setLong(3, empleado.getIdEmpleado());
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();
			
		} catch (Exception e) {
			status = false;
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(con, preparedStatement, rs);
		}
		return status;
	}
	
	@Override
	public List<EmpleadoAPDTO> getEmpleadosExternos() {
		List<EmpleadoAPDTO> empleados= new ArrayList<EmpleadoAPDTO>();
		EmpleadoAPDTO em = null;
		Connection connection = null;
		ResultSet rs = null;
		PreparedStatement preparedStatement = null;
		String sql = " "
				+ " select * from ( "
				+ "select e.nombre, e.apellidoP,e.apellidoM,e.sexo,e.rfc, e.numeroEmpleado,e.email,e.fechanacimiento,  "
				+ "e.fechaCreacion, e.estatus, e.fechaCambioEstatus, e.idEmpleadoAP, e.tipoAnalista, "
				+ " unidad.idCatalogoUnidadAdministrativa as idUnidad,unidad.descripcion as descUni, dependenci.idCatalogoDependencias as idDependencia, dependenci.descripcion as descDepen "
				+ "from empleado_ap e, usuariosacceso u, catalogoUnidadAdministrativa  unidad,   catalogoDependencias dependenci   "
				+ "where e.UsuarioAcceso_idUsuarioAcceso=u.idusuariosAcceso  "
				+ "and u.rolesAcceso_idrolesAcceso = 2 "
				+ "and  e.unidad_fk = unidad.idCatalogoUnidadAdministrativa "
				+ "and  e.dependencia_fk = dependenci.idCatalogoDependencias "
				+ "union "
				+ "select e.nombre, e.apellidoP,e.apellidoM,e.sexo,e.rfc, e.numeroEmpleado,e.email,e.fechanacimiento,  "
				+ "e.fechaCreacion, e.estatus, e.fechaCambioEstatus, e.idEmpleadoAP, e.tipoAnalista, "
				+ " e.unidad_fk as idUnidad,'' as descUni, dependenci.idCatalogoDependencias as idDependencia, dependenci.descripcion as descDepen "
				+ "from empleado_ap e, usuariosacceso u, catalogoUnidadAdministrativa  unidad,   catalogoDependencias dependenci   "
				+ "where e.UsuarioAcceso_idUsuarioAcceso=u.idusuariosAcceso  "
				+ "and u.rolesAcceso_idrolesAcceso = 2 "
				+ "and  e.unidad_fk  is null  "
				+ "and  e.dependencia_fk = dependenci.idCatalogoDependencias "
				+ "union "
				+ "select e.nombre, e.apellidoP,e.apellidoM,e.sexo,e.rfc, e.numeroEmpleado,e.email,e.fechanacimiento,  "
				+ "e.fechaCreacion, e.estatus, e.fechaCambioEstatus, e.idEmpleadoAP, e.tipoAnalista, "
				+ " unidad.idCatalogoUnidadAdministrativa as idUnidad,unidad.descripcion as descUni, e.dependencia_fk as idDependencia, '' as descDepen "
				+ "from empleado_ap e, usuariosacceso u, catalogoUnidadAdministrativa  unidad "
				+ "where e.UsuarioAcceso_idUsuarioAcceso=u.idusuariosAcceso  "
				+ "and u.rolesAcceso_idrolesAcceso = 2 "
				+ "and  e.unidad_fk = unidad.idCatalogoUnidadAdministrativa "
				+ "and  e.dependencia_fk is null "
				+ "union "
				+ "select e.nombre, e.apellidoP,e.apellidoM,e.sexo,e.rfc, e.numeroEmpleado,e.email,e.fechanacimiento,  "
				+ "e.fechaCreacion, e.estatus, e.fechaCambioEstatus, e.idEmpleadoAP, e.tipoAnalista, "
				+ "e.unidad_fk as idUnidad,\"\" as descUni, e.dependencia_fk as idDependencia, \"\" as descDepen "
				+ "from empleado_ap e, usuariosacceso u, catalogoUnidadAdministrativa  unidad,   catalogoDependencias dependenci   "
				+ "where e.UsuarioAcceso_idUsuarioAcceso=u.idusuariosAcceso  "
				+ "and u.rolesAcceso_idrolesAcceso = 2 "
				+ "and  e.unidad_fk is null "
				+ "and  e.dependencia_fk  is null  "
				+ ") as analistas ";

		try {
			System.out.println(sql);
			connection = dataSource.getConnection();
			preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				em=new EmpleadoAPDTO();
				em.setNombre(rs.getString(1));
				em.setApellidoPaterno(rs.getString(2));
				em.setApellidoMaterno(rs.getString(3));
				em.setSexo(rs.getString(4));
				em.setRfc(rs.getString(5));
				em.setNoEmpleado(rs.getString(6));
				em.setMail(rs.getString(7));
				em.setFechaNacimiento(rs.getString(8));
				em.setFechaCreacion(rs.getString(9));
				em.setEstatus(rs.getInt(10));
				em.setFechaCambioEstatus(rs.getString(11));
				em.setIdEmpleado(rs.getLong(12));
				em.setTipoAnalista(rs.getInt("tipoAnalista"));
				em.setIdUnidad(rs.getInt(14));
				em.setUnidadCatalogo(rs.getString(15));
				em.setIdidDependencia(rs.getInt(16));
				em.setDependenciaCatalogo(rs.getString(17));
				empleados.add(em);
			}
			rs.close();

		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, preparedStatement, rs);
		}
		return empleados;
	}
	
	@Override
	public long getUsuarioByRFC(String RFC, Connection connection) {
		long asignacionRFC=-1L;
		ResultSet rs = null;
		PreparedStatement pst = null;
		 
		String sql = "select idusuariosAcceso from  usuariosacceso "
				+ "    where usuario='"+RFC+"' ";
		try {
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				asignacionRFC=rs.getLong(1);
			}
			rs.close();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closePreparedStatemetAndResultSet( pst, rs);
		}
		return asignacionRFC;
	}
	
	
	@Override
	public String getRfcUsuarioByIdC(long idUsuario, Connection connection) {
		String RFC="";
		ResultSet rs = null;
		PreparedStatement pst = null;
		 
		String sql = "select   usuario  from  usuariosacceso "
				+ "    where idusuariosAcceso="+idUsuario+" ";
		try {
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				RFC=rs.getString(1);
			}
			rs.close();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closePreparedStatemetAndResultSet( pst, rs);
		}
		return RFC;
	}
	
	@Override
	public boolean getExistUserbyRfc(String rfc) throws SQLException {
		Connection connection = dataSource.getConnection();
		ResultSet rs = null;
		PreparedStatement pst = null;
		boolean resultado=false;
		 
		String sql = "select rfc from empleado_ap  where rfc='"+rfc+"' ";
		try {
			pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			rs = pst.executeQuery();
			while (rs.next()) {
				resultado=true;
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		} finally {
			UtileriaSql.closeConection(connection, pst, rs);
		}
		return resultado;
	}

}