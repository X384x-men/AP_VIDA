package com.sytecso.dto.solicitud;

import java.io.Serializable;

public class SolicitudHasDocumentoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2577081559581646863L;
	private long id;
	private long idSolicitud;
	private long idDocumento;
	private long tipoDocumento;
	private long tipoArchivo;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public long getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(long idDocumento) {
		this.idDocumento = idDocumento;
	}
	public long getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(long tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public long getTipoArchivo() {
		return tipoArchivo;
	}
	public void setTipoArchivo(long tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}
	
	
}
