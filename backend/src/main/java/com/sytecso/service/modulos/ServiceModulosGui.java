package com.sytecso.service.modulos;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import com.sytecso.config.menu.MenuDTO;
import com.sytecso.dto.modulosgui.Menu;
import com.sytecso.dto.modulosgui.ModuloDTO;
import com.sytecso.model.ModulosGui;

public interface ServiceModulosGui {
	public List<Menu> getAllByRolAcceso(Collection<? extends GrantedAuthority> authorities);

	public void createModulos(List<ModulosGui> modulos);

	public ModulosGui findAllLeftModuloGuiByName(String menuName);

	public ModulosGui findModuloGuiByName(String menuName);

	public Set<ModulosGui> findAll();
	
	public ModulosGui findAllInnerModuloGuiByName(String menuName);
	
	public boolean ValidateModules();
	
	public boolean createMenu(MenuDTO menu) throws SQLException;

	public List<ModuloDTO> getModuloNotPresentByRol(String rol);

	public List<ModuloDTO> getModuloPresentByRol(String rol);
	
}
