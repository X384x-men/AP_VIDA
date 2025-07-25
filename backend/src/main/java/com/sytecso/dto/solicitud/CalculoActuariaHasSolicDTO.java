package com.sytecso.dto.solicitud;

import java.io.Serializable;

public class CalculoActuariaHasSolicDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2787682544871165325L;
	private long idCalculoSolic;
	private long idSolicitud;
	private long idCalculoActuaria;
	public long getIdCalculoSolic() {
		return idCalculoSolic;
	}
	public void setIdCalculoSolic(long idCalculoSolic) {
		this.idCalculoSolic = idCalculoSolic;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public long getIdCalculoActuaria() {
		return idCalculoActuaria;
	}
	public void setIdCalculoActuaria(long idCalculoActuaria) {
		this.idCalculoActuaria = idCalculoActuaria;
	}
	
	

}
