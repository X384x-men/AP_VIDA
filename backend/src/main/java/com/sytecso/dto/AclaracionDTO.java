package com.sytecso.dto;

import java.io.Serializable;

import java.util.List;

public class AclaracionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long idAclaracion;
	private String nombre;
	private String rfc;
	private  String dependencia;
	private String fechaRegistroPortal;
	private String telefono;
	private String email;
	private long documentoTipo;
	private  String comentarios;
	private long tipoAclaracion;
	private int status;
	private long idEmpleadoAP;
	private String tipoAclaracionString;
	private String tipoDocumentoString;
	private String descripcionEmpleado;
	private List<DocumentoDTO> documentoList;
	private String fechaReal; 
	private String fechaAclaracion;
	private String nombreAclaracion;
	private String emailAclaracion;
	private boolean categoriaAclaracion;
	
	
	
	
	

	public String getNombreAclaracion() {
		return nombreAclaracion;
	}
	public void setNombreAclaracion(String nombreAclaracion) {
		this.nombreAclaracion = nombreAclaracion;
	}
	public String getEmailAclaracion() {
		return emailAclaracion;
	}
	public void setEmailAclaracion(String emailAclaracion) {
		this.emailAclaracion = emailAclaracion;
	}
	public boolean isCategoriaAclaracion() {
		return categoriaAclaracion;
	}
	public void setCategoriaAclaracion(boolean categoriaAclaracion) {
		this.categoriaAclaracion = categoriaAclaracion;
	}
	public String getFechaAclaracion() {
		return fechaAclaracion;
	}
	public void setFechaAclaracion(String fechaAclaracion) {
		this.fechaAclaracion = fechaAclaracion;
	}
	public String getFechaReal() {
		return fechaReal;
	}
	public void setFechaReal(String fechaReal) {
		this.fechaReal = fechaReal;
	}
	public List<DocumentoDTO> getDocumentoList() {
		return documentoList;
	}
	public void setDocumentoList(List<DocumentoDTO> documentoList) {
		this.documentoList = documentoList;
	}
	
	public String getDescripcionEmpleado() {
		return descripcionEmpleado;
	}
	public void setDescripcionEmpleado(String descripcionEmpleado) {
		this.descripcionEmpleado = descripcionEmpleado;
	}
	public String getTipoAclaracionString() {
		return tipoAclaracionString;
	}
	public void setTipoAclaracionString(String tipoAclaracionString) {
		this.tipoAclaracionString = tipoAclaracionString;
	}
	public String getTipoDocumentoString() {
		return tipoDocumentoString;
	}
	public void setTipoDocumentoString(String tipoDocumentoString) {
		this.tipoDocumentoString = tipoDocumentoString;
	}
	
	
	
	
	public long getIdEmpleadoAP() {
		return idEmpleadoAP;
	}
	public void setIdEmpleadoAP(long idEmpleadoAP) {
		this.idEmpleadoAP = idEmpleadoAP;
	}
	public long getIdAclaracion() {
		return idAclaracion;
	}
	public void setIdAclaracion(long idAclaracion) {
		this.idAclaracion = idAclaracion;
	}
	public long getDocumentoTipo() {
		return documentoTipo;
	}
	public void setDocumentoTipo(long documentoTipo) {
		this.documentoTipo = documentoTipo;
	}
	public long getTipoAclaracion() {
		return tipoAclaracion;
	}
	public void setTipoAclaracion(long tipoAclaracion) {
		this.tipoAclaracion = tipoAclaracion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	public String getFechaRegistroPortal() {
		return fechaRegistroPortal;
	}
	public void setFechaRegistroPortal(String fechaRegistroPortal) {
		this.fechaRegistroPortal = fechaRegistroPortal;
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
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
