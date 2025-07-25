package com.sytecso.dao.seccion;

import java.util.Set;

import com.sytecso.model.SeccionHasRolAcceso;

public interface DAOSeccionHasRolesAcceso {
	public boolean create(SeccionHasRolAcceso seccionHasRolAcceso);

	public boolean create(Set<SeccionHasRolAcceso> seccionHasRolAcceso);

	public boolean remove(SeccionHasRolAcceso seccionHasRolAcceso);

	public boolean removeById(String columnName, long id);
	public Set<SeccionHasRolAcceso> findByAuthority(String authority);
}
