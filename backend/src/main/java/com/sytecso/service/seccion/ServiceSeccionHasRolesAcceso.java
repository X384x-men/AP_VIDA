package com.sytecso.service.seccion;

import java.util.List;
import java.util.Set;

import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.component.exceptions.MenuException.SeccionNotCreatedException;
import com.sytecso.component.exceptions.MenuException.SeccionNotExistsException;
import com.sytecso.component.exceptions.MenuException.SeccionNotRemovedException;
import com.sytecso.component.exceptions.RolAccesoException.RolNotExistsException;
import com.sytecso.model.SeccionHasRolAcceso;

public interface ServiceSeccionHasRolesAcceso {
	public boolean create(SeccionRolDTO seccionRolDTO)
			throws SeccionNotExistsException, RolNotExistsException, SeccionNotCreatedException;

	public boolean create(Set<SeccionHasRolAcceso> secciones);

	public boolean addAll(SeccionRolDTO seccionRolDTO)
			throws SeccionNotExistsException, RolNotExistsException, SeccionNotCreatedException;

	public boolean remove(SeccionRolDTO seccionRolDTO)
			throws SeccionNotExistsException, RolNotExistsException, SeccionNotRemovedException;

	public boolean remove(String columnName, long id);

	public List<SeccionHasRolAcceso> findByAuthority(String authority);
}
