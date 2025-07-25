package com.sytecso.dto.solicitud;

import java.io.Serializable;

public class OrdenPagoHasSolicitudDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5512759305106324482L;
	private long idOrdenPagoSolicitud;
	private long idOrdenPago;
	private long idSolicitud;
	
	public long getIdOrdenPagoSolicitud() {
		return idOrdenPagoSolicitud;
	}
	public void setIdOrdenPagoSolicitud(long idOrdenPagoSolicitud) {
		this.idOrdenPagoSolicitud = idOrdenPagoSolicitud;
	}
	public long getIdOrdenPago() {
		return idOrdenPago;
	}
	public void setIdOrdenPago(long idOrdenPago) {
		this.idOrdenPago = idOrdenPago;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	
}
