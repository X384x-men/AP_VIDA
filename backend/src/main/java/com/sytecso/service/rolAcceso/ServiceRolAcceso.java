package com.sytecso.service.rolAcceso;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.component.exceptions.RolAccesoException.RolExistsException;
import com.sytecso.component.exceptions.RolAccesoException.RolNotExistsException;
import com.sytecso.model.RolAcceso;

public interface ServiceRolAcceso {
	public RolAcceso save(String rol, String descripcion);

	public RolAcceso save(RolAccesoDTO rol);

	public boolean update(RolAccesoDTO rol, String prevRol);

	public RolAcceso findByRol(String rol) throws RolNotExistsException;

	public List<RolAcceso> getAll();

	public List<RolAccesoDTO> findAll();

	public RolAcceso findOrCreateRol(String rol, String descripcion);

	public RolAcceso createRolIfnotExists(RolAccesoDTO rol) throws RolExistsException;

	public boolean userHasAcceso(Collection<? extends GrantedAuthority> authorities, FilterInvocation filter);

	public RolAccesoDTO getInfo();

	public boolean userHasAccesoURLParams(String rol, String servicio);
}
