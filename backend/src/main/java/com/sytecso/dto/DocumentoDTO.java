package com.sytecso.dto;

import java.io.Serializable;


public class DocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long idDocumento;
	private long idAclaracion;
	private String fecha;
	private String documentoString;
	
	
	
	public String getDocumentoString() {
		return documentoString;
	}
	public void setDocumentoString(String documentoString) {
		this.documentoString = documentoString;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public long getIdAclaracion() {
		return idAclaracion;
	}
	public void setIdAclaracion(long idAclaracion) {
		this.idAclaracion = idAclaracion;
	}
	public long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(long idDocumento) {
		this.idDocumento = idDocumento;
	}
	

}
