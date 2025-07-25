package com.sytecso.dao.rolAcceso;

import java.util.List;

import com.sytecso.dto.rol.RolAccesoDTO;
import com.sytecso.model.RolAcceso;

public interface DAORolAcceso {
	public RolAcceso save(RolAcceso rolAcceso);

	public RolAcceso findByRol(String rol);

	public List<RolAccesoDTO> findAll();

	public List<RolAcceso> getAll();

	public boolean userHasAcceso(String authority, String service);

	public boolean update(RolAccesoDTO rol, String prevRol);
	
	public boolean userHasAccesoURLParams(String rol, String servicio);
}
