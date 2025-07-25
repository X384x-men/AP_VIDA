package com.sytecso.dto.solicitud;

import java.io.Serializable;

public class CalculoActuariaDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7529525608191721780L;
	private long idCalculo;
	private String fechaCreacion;
	private String numeroProceso;
	private long idEmpleadoGenera;
	private long numRegistros;
	private long registrosCargados;
	private String fechaCarga;
	public long getIdCalculo() {
		return idCalculo;
	}
	public void setIdCalculo(long idCalculo) {
		this.idCalculo = idCalculo;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getNumeroProceso() {
		return numeroProceso;
	}
	public void setNumeroProceso(String numeroProceso) {
		this.numeroProceso = numeroProceso;
	}
	public long getIdEmpleadoGenera() {
		return idEmpleadoGenera;
	}
	public void setIdEmpleadoGenera(long idEmpleadoGenera) {
		this.idEmpleadoGenera = idEmpleadoGenera;
	}
	public long getNumRegistros() {
		return numRegistros;
	}
	public void setNumRegistros(long numRegistros) {
		this.numRegistros = numRegistros;
	}
	public long getRegistrosCargados() {
		return registrosCargados;
	}
	public void setRegistrosCargados(long registrosCargados) {
		this.registrosCargados = registrosCargados;
	}
	public String getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	
	
}
