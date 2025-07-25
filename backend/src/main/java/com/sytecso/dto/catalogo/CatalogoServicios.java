package com.sytecso.dto.catalogo;

import java.io.Serializable;

public class CatalogoServicios implements Serializable {

	private static final long serialVersionUID = 6279094395890874308L;

	private Long idcatalogoServicios;
	private String nombreServicio;

	
	
	public Long getIdcatalogoServicios() {
		return idcatalogoServicios;
	}

	public void setIdcatalogoServicios(Long idcatalogoServicios) {
		this.idcatalogoServicios = idcatalogoServicios;
	}

	public String getNombreServicio() {
		return nombreServicio;
	}

	public void setNombreServicio(String nombreServicio) {
		this.nombreServicio = nombreServicio;
	}

}
