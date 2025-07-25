package com.sytecso.config.menu;

import java.util.List;

public class Menu extends MenuProperties {
	public Menu() {
		super();
	}

	private List<SubMenu> subMenu;

	public List<SubMenu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<SubMenu> subMenu) {
		this.subMenu = subMenu;
	}

}
