package com.sytecso.component.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sytecso.dto.modulosgui.Menu;
import com.sytecso.dto.modulosgui.Options;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.Seccion;
import com.sytecso.model.SeccionHasRolAcceso;

public class UtileriaMenuGui {
	private UtileriaMenuGui() {
		throw new IllegalStateException("Cant instance this class");
	}

	/**
	 * @param modulosGui
	 * @return retorna lista de menu principal para la creacion de opciones a
	 *         mostrar en front-end
	 */
	public static List<Menu> createMenu(List<SeccionHasRolAcceso> secciones) {
		Map<String, ModulosGui> modulos = new HashMap<>();
		for (SeccionHasRolAcceso modulo : secciones) {
			Seccion seccion = modulo.getSeccion();
			ModulosGui gui = seccion.getModulosGui();
			if (modulos.containsKey(gui.getNombre())) {
				modulos.get(gui.getNombre()).getSeccion().add(seccion);
			} else {
				gui.setSeccion(new HashSet<>());
				gui.getSeccion().add(seccion);
				modulos.put(gui.getNombre(), gui);
			}
		}
		if (!modulos.isEmpty())
			return createMenu(modulos);
		return new ArrayList<>();
	}

	/**
	 * @param modulos
	 */
	private static List<Menu> createMenu(Map<String, ModulosGui> modulos) {
		List<Menu> menus = new ArrayList<>();
		Menu menu = null;
		for (Map.Entry<String, ModulosGui> gui : modulos.entrySet()) {
			menu = new Menu();
			ModulosGui modulo = gui.getValue();
			String title = modulo.getNombre();
			String menuView =  title.contains("-")?title.substring(0,title.indexOf('-')):title;
			menu.setIndex(modulo.getIndex());
			menu.setEnabled(modulo.getEnabled());
			menu.setUrl(modulo.getUrl());
			menu.setIcon(modulo.getCatalogoIconos().getNombre());
			menu.setMenuTitle(menuView);
			menu.setSubMenu(createOptions(modulo));
			menus.add(menu);
		}
		return menus;	}

	/**
	 * @param y
	 * @param modulo
	 * @return retorna lista de options para las opciones de un sub menu
	 */
	private static List<Options> createOptions(ModulosGui modulo) {
		List<Options> options = new ArrayList<>();
		if (!modulo.getSeccion().isEmpty()) {
			for (Seccion seccion : modulo.getSeccion()) {
				if (seccion.getEnabled() > 0) {
					Options option = new Options();
					option.setEnabled(seccion.getEnabled());
					option.setIndex(seccion.getIndex());
					option.setName(seccion.getNombre());
					option.setIcon(seccion.getCatalogoIconos().getNombre());
					option.setUrl(seccion.getUrl());
					options.add(option);

				}
			}
		}
		return options;
	}
}
