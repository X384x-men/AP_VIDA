package com.sytecso.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MenuProperties implements Serializable {

	private static final long serialVersionUID = 8321845669433199651L;
	@Column(name = "url")
	private String url;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "enabled")
	private int enabled;

	@Column(name = "posicion")
	private int index;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public abstract CatalogoIconos getCatalogoIconos();

	public abstract void setCatalogoIconos(CatalogoIconos catalogoIconos);

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
