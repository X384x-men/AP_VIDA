package com.sytecso.dto.modulosgui;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {

	private static final long serialVersionUID = -4956845548816030802L;
	private Integer index;
	private String menuTitle;
	private Integer enabled;
	private String icon;
	private String url;
	private List<Options> subMenu;

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getMenuTitle() {
		return menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
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

	public List<Options> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Options> subMenu) {
		this.subMenu = subMenu;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
}
