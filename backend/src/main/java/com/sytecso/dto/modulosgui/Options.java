package com.sytecso.dto.modulosgui;

import java.io.Serializable;

public class Options implements Serializable {

	private static final long serialVersionUID = 1338310479173884124L;

	private int index;
	private String name;
	private Integer enabled;
	private String icon;
	private String url;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

}
