package com.sytecso.dto.solicitud;

import java.io.Serializable;

public class OrdenPagoDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2263431526971158556L;
	private long idOrdenPago;
	private String fechaCreacion;
	private long idEmpleadoGenera;
	
	public long getIdOrdenPago() {
		return idOrdenPago;
	}
	public void setIdOrdenPago(long idOrdenPago) {
		this.idOrdenPago = idOrdenPago;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public long getIdEmpleadoGenera() {
		return idEmpleadoGenera;
	}
	public void setIdEmpleadoGenera(long idEmpleadoGenera) {
		this.idEmpleadoGenera = idEmpleadoGenera;
	}
	
	
}
