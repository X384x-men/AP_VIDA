package com.sytecso.dto;

import java.io.Serializable;

public class TipoAclaracionDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long idTipoAclaracion;
	private String tipoAclaracion;
	private String descripcion;
	public long getIdTipoAclaracion() {
		return idTipoAclaracion;
	}
	public void setIdTipoAclaracion(long idTipoAclaracion) {
		this.idTipoAclaracion = idTipoAclaracion;
	}
	public String getTipoAclaracion() {
		return tipoAclaracion;
	}
	public void setTipoAclaracion(String tipoAclaracion) {
		this.tipoAclaracion = tipoAclaracion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
