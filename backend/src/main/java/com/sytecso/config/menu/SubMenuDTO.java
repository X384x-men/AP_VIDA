package com.sytecso.config.menu;

import java.io.Serializable;
import java.util.List;

public class SubMenuDTO  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9137866754713801525L;
	private int index;
	private String name;
	private String icon;
	private boolean enabled;
	private String url;
	private List<PantallaDTO> pantallas;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<PantallaDTO> getPantallas() {
		return pantallas;
	}
	public void setPantallas(List<PantallaDTO> pantallas) {
		this.pantallas = pantallas;
	}
	
	
	
}
