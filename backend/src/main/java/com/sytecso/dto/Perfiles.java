package com.sytecso.dto;

import java.io.Serializable;

public class Perfiles implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4856421148523415653L;
	private String nombreRol;
	private String descripcionRol;
	private String userName;
	private String password;
	private String tipoAcceso;
	
	
	
	public String getNombreRol() {
		return nombreRol;
	}
	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}
	public String getDescripcionRol() {
		return descripcionRol;
	}
	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTipoAcceso() {
		return tipoAcceso;
	}
	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}
	
	
	
	
}
