package com.sytecso.dto.solicitud;

import java.io.Serializable;

public class ShortSolicitudAPDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fechaSolicitud;
	private String fechaFinLaboral;
	private String rfcAsegurado;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String telefono;
	private String email;
	private String sueldo;
	private String pagoAnterior;
	private String tipoPago;
	private String Banco;
	private String nombreBanco;
	private String observaciones;
	private String dependencia;
	
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public String getFechaFinLaboral() {
		return fechaFinLaboral;
	}
	public void setFechaFinLaboral(String fechaFinLaboral) {
		this.fechaFinLaboral = fechaFinLaboral;
	}
	public String getRfcAsegurado() {
		return rfcAsegurado;
	}
	public void setRfcAsegurado(String rfcAsegurado) {
		this.rfcAsegurado = rfcAsegurado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSueldo() {
		return sueldo;
	}
	public void setSueldo(String sueldo) {
		this.sueldo = sueldo;
	}
	public String getPagoAnterior() {
		return pagoAnterior;
	}
	public void setPagoAnterior(String pagoAnterior) {
		this.pagoAnterior = pagoAnterior;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getBanco() {
		return Banco;
	}
	public void setBanco(String banco) {
		Banco = banco;
	}
	public String getNombreBanco() {
		return nombreBanco;
	}
	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	

}
