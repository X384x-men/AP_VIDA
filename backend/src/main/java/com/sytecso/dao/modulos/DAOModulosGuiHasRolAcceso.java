package com.sytecso.dao.modulos;


import java.util.Set;

import com.sytecso.model.ModulosGuiHasRolesAcceso;
import com.sytecso.model.SeccionHasRolAcceso;

public interface DAOModulosGuiHasRolAcceso {
	public boolean remove(ModulosGuiHasRolesAcceso modulo);
	public boolean create(ModulosGuiHasRolesAcceso modulo);
	public void create(Set<ModulosGuiHasRolesAcceso> modulos,Set<SeccionHasRolAcceso> secciones);
}
