package com.sytecso.dto.email;

import java.io.Serializable;

public class EmailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long idEmail;
	private String rfc;
	private String correo;
	private String nombre;
	
	private String numerosRegistro;
	private long idEvento;
	private long idAclaracion;
	private long idSolicitud;
	private String fechaEmail;
	private String tipo;
	private boolean status;
	private String fechaExito;
	
	
	
	public String getFechaExito() {
		return fechaExito;
	}
	public void setFechaExito(String fechaExito) {
		this.fechaExito = fechaExito;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFechaEmail() {
		return fechaEmail;
	}
	public void setFechaEmail(String fechaEmail) {
		this.fechaEmail = fechaEmail;
	}
	public long getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(long idEmail) {
		this.idEmail = idEmail;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNumerosRegistro() {
		return numerosRegistro;
	}
	public void setNumerosRegistro(String numerosRegistro) {
		this.numerosRegistro = numerosRegistro;
	}
	public long getIdEvento() {
		return idEvento;
	}
	public void setIdEvento(long idEvento) {
		this.idEvento = idEvento;
	}
	public long getIdAclaracion() {
		return idAclaracion;
	}
	public void setIdAclaracion(long idAclaracion) {
		this.idAclaracion = idAclaracion;
	}
	public long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	
	
	

}
