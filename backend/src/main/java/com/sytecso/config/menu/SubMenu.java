package com.sytecso.config.menu;

import java.util.List;

public class SubMenu extends MenuProperties {
	public SubMenu() {
		super();
	}

	List<Pantalla> pantallas;

	public List<Pantalla> getPantallas() {
		return pantallas;
	}

	public void setPantallas(List<Pantalla> pantallas) {
		this.pantallas = pantallas;
	}
}
