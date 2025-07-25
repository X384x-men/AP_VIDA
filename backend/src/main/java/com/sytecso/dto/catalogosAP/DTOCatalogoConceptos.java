package com.sytecso.dto.catalogosAP;

import java.io.Serializable;

public class DTOCatalogoConceptos  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private long idCatalogoConceptos;
	private String descripcion;
	private int status;
	public long getIdCatalogoConceptos() {
		return idCatalogoConceptos;
	}
	public void setIdCatalogoConceptos(long idCatalogoConceptos) {
		this.idCatalogoConceptos = idCatalogoConceptos;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
