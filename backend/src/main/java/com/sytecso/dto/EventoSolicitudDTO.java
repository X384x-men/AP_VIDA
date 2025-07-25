package com.sytecso.dto;

import java.io.Serializable;

public class EventoSolicitudDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long idEvento;
	private long idSolicitud;
	private String empleadoSolicitad;
	private String empleadoAP;
	private String tipo;
	private String fecha;
	private String descripcion;
	private String claseSolicitud;
	
	
	public String getClaseSolicitud() {
		return claseSolicitud;
	}
	public void setClaseSolicitud(String claseSolicitud) {
		this.claseSolicitud = claseSolicitud;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(long idEvento) {
		this.idEvento = idEvento;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getEmpleadoSolicitad() {
		return empleadoSolicitad;
	}
	public void setEmpleadoSolicitad(String empleadoSolicitad) {
		this.empleadoSolicitad = empleadoSolicitad;
	}
	public String getEmpleadoAP() {
		return empleadoAP;
	}
	public void setEmpleadoAP(String empleadoAP) {
		this.empleadoAP = empleadoAP;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
