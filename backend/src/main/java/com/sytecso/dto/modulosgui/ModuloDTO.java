package com.sytecso.dto.modulosgui;

import java.io.Serializable;

public class ModuloDTO implements Serializable {

	private static final long serialVersionUID = -5375720117862242739L;

	public ModuloDTO(String nombre) {
		super();
		this.nombre = nombre;
	}

	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
