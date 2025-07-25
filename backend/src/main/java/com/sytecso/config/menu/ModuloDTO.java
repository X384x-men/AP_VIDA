package com.sytecso.config.menu;

import java.io.Serializable;
import java.util.List;

public class ModuloDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int index;
	private String menuTitle;
	private boolean enabled;
	private String icon;
	private String url;
	private List<SubMenuDTO> subMenu;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getMenuTitle() {
		return menuTitle;
	}
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<SubMenuDTO> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(List<SubMenuDTO> subMenu) {
		this.subMenu = subMenu;
	}
	
}
