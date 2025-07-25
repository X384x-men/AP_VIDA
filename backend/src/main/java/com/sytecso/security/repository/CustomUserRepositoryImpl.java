package com.sytecso.security.repository;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sytecso.component.DefaultAdminAP;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.utility.UtileriaSql;
import com.sytecso.config.logger.SytecsoLogger;
import com.sytecso.config.menu.MenuDTO;
import com.sytecso.dto.Perfiles;
import com.sytecso.dto.usuarioacceso.UserAccess;
import com.sytecso.dto.usuarioacceso.UsuarioAccesoDTO;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.UsuarioAcceso;
import com.sytecso.security.profile.Profile;
import com.sytecso.service.modulos.ServiceModulosGui;
import com.sytecso.service.modulos.ServiceModulosGuiHasRolAcceso;
import com.sytecso.service.rolAcceso.ServiceRolAcceso;
import com.sytecso.service.usuario.ServiceUsuarioAcceso;

@Repository("userRepository")
@Transactional
public class CustomUserRepositoryImpl implements UserRepository {
	private static final String ROLE_PREFIX = "ROLE_";
	private @Autowired @Qualifier("admin") Profile admin;

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ServiceRolAcceso serviceRolAcceso;
	@Autowired
	private ServiceUsuarioAcceso serviceUsuarioAcceso;
	@Autowired
	private ServiceModulosGui serviceModulosGui;
	@Autowired
	private ServiceModulosGuiHasRolAcceso serviceModuloHasRolAcceso;
	@Autowired
	private DataSource dataSource;
	@Autowired
	DefaultAdminAP adminAP;

	@Transactional(readOnly = true)
	@Override
	public UserAccess findUser(String username, String password) {
		try {
			if (StringUtils.isAnyBlank(username, password)) {
				return null;
			} else {
				EntityManager entityManager = sessionFactory.getCurrentSession();
				CriteriaBuilder cb = entityManager.getCriteriaBuilder();
				CriteriaQuery<UsuarioAccesoDTO> query = cb.createQuery(UsuarioAccesoDTO.class);
				Root<UsuarioAcceso> root = query.from(UsuarioAcceso.class);
				Join<UsuarioAcceso, RolAcceso> joinRolAcceso = root.join("rolesAcceso", JoinType.INNER);
				query.multiselect(root.get("usuario"), root.get("password"), joinRolAcceso.get("nombre"))
						.where(cb.equal(root.get("usuario"), username)).distinct(true);
				UsuarioAccesoDTO user = entityManager.createQuery(query).getSingleResult();
				return new UserAccess(user.getUsuario(), user.getPassword(), true, true, true, true,
						this.getAuthorities(user.getRol()));

			}
		} catch (NoResultException e) {
			SytecsoLogger.info("El usuario " + username + " no existe");
		}
		return null;
	}
	// TODO cifrar rol de acceso
	private Collection<? extends GrantedAuthority> getAuthorities(String rol) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//		String mask = UtileriaCifrado.getMD5(ROLE_PREFIX + rol);
		authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + rol));
		return authorities;
	}

	@Override
	public void createDefaultUsersAndRoles() throws SQLException {
		
		List<Perfiles> perfiles = new ArrayList<Perfiles>();
		Perfiles root = new Perfiles();
		root.setNombreRol(this.admin.getRole());
		root.setDescripcionRol(this.admin.getDescripcion());
		root.setUserName(this.admin.getUserName());
		root.setPassword(this.admin.getPassword());
		root.setTipoAcceso(this.admin.getAcceso());
		perfiles.add(root);
		Perfiles admin = new Perfiles();
		admin.setNombreRol(adminAP.getNombreRolAdminAP());
		admin.setDescripcionRol(adminAP.getDescripcionRol());
		admin.setUserName(adminAP.getUsuarioAdminAP());
		admin.setPassword(adminAP.getPasswordAdminAP());
		admin.setTipoAcceso(adminAP.getTipoAcceso());
		perfiles.add(admin);
		
		for (Perfiles perfil : perfiles) {
			RolAcceso rolAcceso = this.serviceRolAcceso.findOrCreateRol(perfil.getNombreRol(),perfil.getDescripcionRol());
			if (!this.serviceUsuarioAcceso.usuarioExists(perfil.getUserName())) {
				SytecsoLogger.info("SE HA INICIADO EL PROCESO PARA CREAR AL USUARIO POR DEFECTO");
				UsuarioAcceso usuarioAcceso = new UsuarioAcceso(perfil.getUserName(),perfil.getPassword(),perfil.getTipoAcceso());
				usuarioAcceso.setRolesAcceso(rolAcceso);
				usuarioAcceso.setId(serviceUsuarioAcceso.createUsuarioAcceso(usuarioAcceso));
				SytecsoLogger.info("SE HA TERMINADO EL PROCESO PARA CREAR AL USUARIO POR DEFECTO");
				
			}
			if (rolAcceso != null)
				this.serviceModuloHasRolAcceso.create(rolAcceso);
			
		}
		
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void createDefaultMenuOptions() {
		try {
			SytecsoLogger.info("entré al metodo de creación de Menú.json en la base de datos");
			SytecsoLogger.info("Se debe realizar la comprobación si ya se asignaron las pantalla a la  base de datos");
			if(serviceModulosGui.ValidateModules()) {
				
				ObjectMapper objectMapper = new ObjectMapper();
			//	objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				String fileName = "json/menu.json";
				SytecsoLogger.info("Se realiza el mapeo del archivo.json");
		        ClassLoader classLoader = new CustomUserRepositoryImpl().getClass().getClassLoader();
		        SytecsoLogger.info("This is the path of the file: "+classLoader.getResource(fileName).getFile());
		        System.out.println("This is the path of the file: "+classLoader.getResource(fileName).getFile());
		        //read json file and convert to customer object
		        MenuDTO menu = objectMapper.readValue(new File(classLoader.getResource(fileName).getFile()), MenuDTO.class);	 
		        serviceModulosGui.createMenu(menu);
			}
		} catch (Exception e) {
			SytecsoLogger.error("Error al  hacer la llamada a la creación del menú", e);
			System.exit(0);
		}
	}
	@Override
	public UserAccess findUserName(String username) {
		try {
			if (StringUtils.isAnyBlank(username)) {
				return null;
			} else {
				UsuarioAccesoDTO user = new UsuarioAccesoDTO();
				String sql = "select u.idusuariosAcceso ,u.usuario, u.pwd, r.nombreRol, e.estatus     " + 
						"from usuariosacceso u, rolesAcceso r, empleado_ap e    " + 
						"where u.rolesAcceso_idrolesAcceso=r.idrolesAcceso and e.rfc = u.usuario    " + 
						"and u.usuario=? ";

				Connection con = null;
				ResultSet rs = null;
				PreparedStatement pst = null;
				try {
					con = dataSource.getConnection();
					pst = con.prepareStatement(sql);
					pst.setString(1, username);
					rs = pst.executeQuery();
					while (rs.next()) {
						user.setUsuario(rs.getString(2));
						user.setPassword(rs.getString(3));
						user.setRol(rs.getString(4));
						user.setStatus(rs.getInt(5));
					}
				} catch (Exception e) {
					SytecsoController.logClassAndMethodWithException(e);
				} finally {
					UtileriaSql.closeConection(con, pst, rs);
				}
				 
				return new UserAccess(user.getUsuario(), user.getPassword(), user.getStatus() == 1 ? true : false, true, true, true,
						this.getAuthorities(user.getRol()));

			}
		} catch (NoResultException e) {
			SytecsoLogger.info("El usuario " + username + " no existe");
		}
		return null;
	}

}