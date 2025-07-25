package com.sytecso.dto.usuario;

import java.io.Serializable;

import com.sytecso.dto.usuarioacceso.UsuarioAcceso;

public class Usuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 828728867696298491L;
	
	private long idUsuario;
	private String fechaCreacion;
	private int asociado;
	private int estatus;
	private String status;
	private long idEmpleado;
	private UsuarioAcceso usuarioAcceso;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public int getAsociado() {
		return asociado;
	}
	public void setAsociado(int asociado) {
		this.asociado = asociado;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public UsuarioAcceso getUsuarioAcceso() {
		return usuarioAcceso;
	}
	public void setUsuarioAcceso(UsuarioAcceso usuarioAcceso) {
		this.usuarioAcceso = usuarioAcceso;
	}
	

}
