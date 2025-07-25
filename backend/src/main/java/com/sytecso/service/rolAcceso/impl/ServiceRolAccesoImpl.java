package com.sytecso.service.rolAcceso.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import com.sytecso.component.utility.UtileriaAcceso;
import com.sytecso.config.url.UrlProperties;
import com.sytecso.dao.rolAcceso.DAORolAcceso;
import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.component.exceptions.RolAccesoException;
import com.sytecso.component.exceptions.RolAccesoException.RolExistsException;
import com.sytecso.component.exceptions.RolAccesoException.RolNotExistsException;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.component.exceptions.SytecsoExceptions;
import com.sytecso.model.RolAcceso;
import com.sytecso.security.profile.Profile;
import com.sytecso.service.rolAcceso.ServiceRolAcceso;

@Service
public class ServiceRolAccesoImpl extends UtileriaAcceso implements ServiceRolAcceso {
	@Autowired
	private DAORolAcceso daoRolAcceso;
	private @Autowired @Qualifier("admin") Profile admin;
	private @Autowired @Qualifier("defaultAccess") UrlProperties url;

	@Override
	public RolAcceso save(String rol, String descripcion) {
		RolAcceso rolAcceso = new RolAcceso();
		rolAcceso.setNombre(rol);
		rolAcceso.setDescripcion(descripcion);
		return daoRolAcceso.save(rolAcceso);
	}

	@Override
	public RolAcceso findByRol(String rol) throws RolNotExistsException {
		RolAcceso rolAcceso = this.daoRolAcceso.findByRol(rol);
		if (rolAcceso == null)
			throw new RolAccesoException.RolNotExistsException("El rol: ".concat(rol).concat(" no existe"));
		return rolAcceso;
	}

	@Override
	public List<RolAccesoDTO> findAll() {
		return this.daoRolAcceso.findAll();
	}

	@Override
	public RolAcceso findOrCreateRol(String rol, String descripcion) {
		try {
			RolAcceso rolAcceso = this.findByRol(rol);
			if (rolAcceso != null)
				return rolAcceso;
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return this.save(rol, descripcion);
	}

	@Override
	public List<RolAcceso> getAll() {
		return this.daoRolAcceso.getAll();
	}

	@Override
	public boolean userHasAcceso(Collection<? extends GrantedAuthority> authorities, FilterInvocation filter) {
		try {
			if (this.url.getUrls().stream().anyMatch(view -> view.equals(filter.getRequestUrl())))
				return true;
			String builder = this.removeBaseHrefPath(filter.getRequestUrl(), this.url.getUrls());
			if (builder != null && builder.length() > 0) {
				for (Iterator<? extends GrantedAuthority> iterator = authorities.iterator(); iterator.hasNext();) {
					String authority = getRol(iterator.next());
					if (this.userHasAccesoURLParams(authority,
							this.removeOnlyBaseHrefPath(filter.getRequestUrl(), this.url.getUrls())))
						return true;
					if (this.daoRolAcceso.userHasAcceso(authority, builder))
						return true;
				}
			}
		} catch (Exception e) {
			SytecsoExceptions.logClassAndMethodWithException(e);
		}
		return true;
	}

	@Override
	public RolAccesoDTO getInfo() {
		SecurityContext context = SecurityContextHolder.getContext();
		List<String> rol = this.getRol(context);
		if (!rol.isEmpty())
			return new RolAccesoDTO(rol.get(0));
		return null;
	}

	@Override
	public boolean update(RolAccesoDTO rol, String prevRol) {
		return this.daoRolAcceso.update(rol, prevRol);
	}

	@Override
	public RolAcceso createRolIfnotExists(RolAccesoDTO rol) throws RolExistsException {
		RolAcceso rolAcceso = null;
		try {
			rolAcceso = this.findByRol(rol.getNombre());
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		if (rolAcceso != null)
			throw new RolAccesoException.RolExistsException("El rol ".concat(rol.getNombre()).concat(" ya existe"));
		return this.save(rol);
	}

	@Override
	public RolAcceso save(RolAccesoDTO rol) {
		RolAcceso rolAcceso = new RolAcceso();
		rolAcceso.setNombre(rol.getNombre().toUpperCase());
		if(rolAcceso.getNombre().equals("ADMINISTRADOR")||rolAcceso.getNombre().equals("ADMINISTRACION")||rolAcceso.getNombre().equals("ADMINISTRACIÓN")) {
			rolAcceso.setNombre("ADMIN");
		}
		rolAcceso.setDescripcion(rol.getDescripcion());
		
		return daoRolAcceso.save(rolAcceso);
	}

	@Override
	public boolean userHasAccesoURLParams(String rol, String servicio) {
		return this.daoRolAcceso.userHasAccesoURLParams(rol, servicio);
	}

}
