package com.sytecso.dto.catalogosAP;

import java.io.Serializable;

public class DTOCatalogoDependencias  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String descripcionCatalogo;
	private long idCatalogo;
	private int status;
	public String getDescripcionCatalogo() {
		return descripcionCatalogo;
	}
	public void setDescripcionCatalogo(String descripcionCatalogo) {
		this.descripcionCatalogo = descripcionCatalogo;
	}
	public long getIdCatalogo() {
		return idCatalogo;
	}
	public void setIdCatalogo(long idCatalogo) {
		this.idCatalogo = idCatalogo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
