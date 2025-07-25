package com.sytecso.dao.modulos;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import com.sytecso.config.menu.MenuDTO;
import com.sytecso.config.menu.PantallaDTO;
import com.sytecso.config.menu.ServicioDTO;
import com.sytecso.config.menu.SubMenuDTO;
import com.sytecso.config.menu.ModuloDTO;
import com.sytecso.model.ModulosGui;

public interface DAOModulosGui {
	public List<ModulosGui> getAllByRolAcceso(String rol);

	public void create(List<ModulosGui> modulos);

	public ModulosGui findLeftAllModuloGuiByName(String menuName);

	public ModulosGui findModuloGuiByName(String menuName);

	public ModulosGui createOrUpdate(ModulosGui modulo);

	public ModulosGui create(ModulosGui modulo);

	public Set<ModulosGui> findAll();

	public List<com.sytecso.dto.modulosgui.ModuloDTO> getModuloNotPresentByRol(String rol);

	public List<com.sytecso.dto.modulosgui.ModuloDTO> getModuloPresentByRol(String rol);
	
	public ModulosGui findAllInnerModuloGuiByName(String menuName);
	public boolean validateModules();
	public long createIcon(String iconName,Connection con);
	public boolean createModule(ModuloDTO modulo,Connection con);
	public boolean createSeccion(SubMenuDTO seccion, Connection con,long idModulo);
	public boolean createPantalla(PantallaDTO pantalla,Connection con,long idSeccion);
	public boolean createCatalogoServicios(List<ServicioDTO> servicios,Connection con,long idPantalla);
	public boolean creaServiciosHasPantalla(long idPantalla,long idServicio, Connection con);
	public boolean createMenu(MenuDTO menu) throws SQLException;

}

