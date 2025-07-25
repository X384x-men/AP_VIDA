package com.sytecso.dto.rol;

import java.io.Serializable;

public class RolAccesoDTO implements Serializable {

	private static final long serialVersionUID = -3449103627438806768L;
	private long idrol;
	private String nombre;
	private String descripcion;

	public RolAccesoDTO() {
		super();
	}

	public RolAccesoDTO(String nombre) {
		super();
		this.nombre = nombre;
	}

	public RolAccesoDTO(String nombre, String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getIdrol() {
		return idrol;
	}

	public void setIdrol(long idrol) {
		this.idrol = idrol;
	}

}
