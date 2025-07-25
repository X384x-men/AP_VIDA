package com.sytecso.service.modulos;

import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.model.ModulosGuiHasRolesAcceso;
import com.sytecso.model.RolAcceso;

public interface ServiceModulosGuiHasRolAcceso {
	public boolean remove(ModulosGuiHasRolesAcceso modulo);

	public boolean remove(SeccionRolDTO seccionRolDTO);

	public boolean create(ModulosGuiHasRolesAcceso modulo);

	public void create(RolAcceso rol);

	public boolean create(SeccionRolDTO seccionRolDTO);
}
