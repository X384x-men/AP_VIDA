package com.sytecso.config.menu;

import java.io.Serializable;

public class ServicioDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2713229223063955749L;
	private String nombreServicio;

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

}
