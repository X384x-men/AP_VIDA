package com.sytecso.dto.solicitud;

import java.io.Serializable;

public class ObservacionDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3616646540056683312L;
	private long idObservacion;
	private String observacion;
	private String fechaCreacion;
	private long idSolicitud;
	
	public long getIdObservacion() {
		return idObservacion;
	}
	public void setIdObservacion(long idObservacion) {
		this.idObservacion = idObservacion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	
}
