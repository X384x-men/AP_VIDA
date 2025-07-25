package com.sytecso.service.modulos.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.sytecso.component.utility.UtileriaAcceso;
import com.sytecso.component.utility.UtileriaMenuGui;
import com.sytecso.config.menu.MenuDTO;
import com.sytecso.dao.modulos.DAOModulosGui;
import com.sytecso.dto.modulosgui.Menu;
import com.sytecso.dto.modulosgui.ModuloDTO;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.SeccionHasRolAcceso;
import com.sytecso.security.profile.Profile;
import com.sytecso.service.modulos.ServiceModulosGui;
import com.sytecso.service.seccion.ServiceSeccionHasRolesAcceso;

@Service
public class ServiceModulosGuiImpl extends UtileriaAcceso implements ServiceModulosGui {
	@Autowired
	private DAOModulosGui daoModulosGui;
	@Autowired
	private ServiceSeccionHasRolesAcceso serviceSeccionHasRolAcceso;
	private @Autowired @Qualifier("admin") Profile admin;

	@Override
	public List<Menu> getAllByRolAcceso(Collection<? extends GrantedAuthority> authorities) {
		List<Menu> menu = new ArrayList<>();
		try {
			System.out.println(authorities);
			System.out.println(this.admin.getRole());
			if (!this.isAuthoritiePresent(authorities, this.admin.getRole())) {
				for (Iterator<? extends GrantedAuthority> iterator = authorities.iterator(); iterator.hasNext();) {
					String authority = getRol(iterator.next());
					List<SeccionHasRolAcceso> modulosGui = this.serviceSeccionHasRolAcceso.findByAuthority(authority);
					menu.addAll(UtileriaMenuGui.createMenu(modulosGui));
					Collections.sort(menu, (o1, o2) -> o1.getIndex().compareTo(o2.getIndex()));
				}
				return menu;
			} else {
				List<SeccionHasRolAcceso> modulosGui = this.serviceSeccionHasRolAcceso
						.findByAuthority(this.admin.getRole());
				menu.addAll(UtileriaMenuGui.createMenu(modulosGui));
				Collections.sort(menu, (o1, o2) -> o1.getIndex().compareTo(o2.getIndex()));
				return menu;
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new ArrayList<>();
	}

	

	@Override
	public ModulosGui findAllLeftModuloGuiByName(String menuName) {
		try {
			return this.daoModulosGui.findLeftAllModuloGuiByName(menuName);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return null;
	}

	@Override
	public void createModulos(List<ModulosGui> modulos) {
		this.daoModulosGui.create(modulos);
	}

	@Override
	public Set<ModulosGui> findAll() {
		try {
			return this.daoModulosGui.findAll();
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	

	@Override
	public ModulosGui findModuloGuiByName(String menuName) {
		return this.daoModulosGui.findModuloGuiByName(menuName);
	}

	@Override
	public ModulosGui findAllInnerModuloGuiByName(String menuName) {
		return this.daoModulosGui.findAllInnerModuloGuiByName(menuName);
	}

	@Override
	public boolean ValidateModules() {
		return daoModulosGui.validateModules();
	}

	@Override
	public boolean createMenu(MenuDTO menu) throws SQLException {
		return daoModulosGui.createMenu(menu);
	}
	
	@Override
	public List<ModuloDTO> getModuloNotPresentByRol(String rol) {
		return this.daoModulosGui.getModuloNotPresentByRol(rol);
	}
	@Override
	public List<ModuloDTO> getModuloPresentByRol(String rol) {
		return this.daoModulosGui.getModuloPresentByRol(rol);
	}

}
