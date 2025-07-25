package com.sytecso.config.menu;

import java.io.Serializable;

public class CatalogoIconosDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 94965284155521416L;
	private long idCatalogoIconos;
	private String nombreIcono;
	
	
	public long getIdCatalogoIconos() {
		return idCatalogoIconos;
	}
	public void setIdCatalogoIconos(long idCatalogoIconos) {
		this.idCatalogoIconos = idCatalogoIconos;
	}
	public String getNombreIcono() {
		return nombreIcono;
	}
	public void setNombreIcono(String nombreIcono) {
		this.nombreIcono = nombreIcono;
	}
	
	
	

}
