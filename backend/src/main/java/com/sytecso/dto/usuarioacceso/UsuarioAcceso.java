package com.sytecso.dto.usuarioacceso;

import java.io.Serializable;

import com.sytecso.dto.rol.RolAccesoDTO;

public class UsuarioAcceso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7836233415969962563L;
	//idusuariosAcceso, usuario, pwd, tipoAcceso, rolesAcceso_idrolesAcceso, Usuario_idUsuario, fechaCreacion, estatus
	private long idusuariosAcceso;
	private String usuario;
	private String pwd;
	private int tipoAcceso;
	private String typeAcceso;
	private long idRolesAcceso;
	private long idUsuario;
	private String fechaCreacion;
	private int estatus;
	private RolAccesoDTO rolAcceso;
	
	public long getIdusuariosAcceso() {
		return idusuariosAcceso;
	}
	public void setIdusuariosAcceso(long idusuariosAcceso) {
		this.idusuariosAcceso = idusuariosAcceso;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getTipoAcceso() {
		return tipoAcceso;
	}
	public void setTipoAcceso(int tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}
	public long getIdRolesAcceso() {
		return idRolesAcceso;
	}
	public void setIdRolesAcceso(long idRolesAcceso) {
		this.idRolesAcceso = idRolesAcceso;
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
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public RolAccesoDTO getRolAcceso() {
		return rolAcceso;
	}
	public void setRolAcceso(RolAccesoDTO rolAcceso) {
		this.rolAcceso = rolAcceso;
	}
	public String getTypeAcceso() {
		return typeAcceso;
	}
	public void setTypeAcceso(String typeAcceso) {
		this.typeAcceso = typeAcceso;
	}
	
	

}
