package com.sytecso.dto;

import java.io.Serializable;

public class CatalogoDocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2327753392461488258L;
	
	private long idCatalogoDocumento;
	private String tipoDocumento;
	public long getIdCatalogoDocumento() {
		return idCatalogoDocumento;
	}
	public void setIdCatalogoDocumento(long idCatalogoDocumento) {
		this.idCatalogoDocumento = idCatalogoDocumento;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	

}
