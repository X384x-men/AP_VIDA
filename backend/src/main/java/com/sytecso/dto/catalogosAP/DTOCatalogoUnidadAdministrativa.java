package com.sytecso.dto.catalogosAP;

import java.io.Serializable;

public class DTOCatalogoUnidadAdministrativa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -536404332836503845L;
	
	private long idUnidadAdministrativa;
	private String descripcion;
	private int status;
	public long getIdUnidadAdministrativa() {
		return idUnidadAdministrativa;
	}
	public void setIdUnidadAdministrativa(long idUnidadAdministrativa) {
		this.idUnidadAdministrativa = idUnidadAdministrativa;
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
