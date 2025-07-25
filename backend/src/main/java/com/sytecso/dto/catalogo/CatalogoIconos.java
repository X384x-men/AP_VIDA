package com.sytecso.dto.catalogo;

import java.io.Serializable;

public class CatalogoIconos implements Serializable {

	private static final long serialVersionUID = 6159038339662952233L;
	
	
	private Long idcatalogoIconos;
	private String nombreIcono;
	
	
	public Long getIdcatalogoIconos() {
		return idcatalogoIconos;
	}
	public void setIdcatalogoIconos(Long idcatalogoIconos) {
		this.idcatalogoIconos = idcatalogoIconos;
	}
	public String getNombreIcono() {
		return nombreIcono;
	}
	public void setNombreIcono(String nombreIcono) {
		this.nombreIcono = nombreIcono;
	}
	
	

}
