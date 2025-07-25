package com.sytecso.dto.usuarioacceso;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class UsuarioAccesoDTO implements Serializable {

	private static final long serialVersionUID = -2342829155268949902L;

	public UsuarioAccesoDTO() {
		super();
	}

	public UsuarioAccesoDTO(Long id) {
		this.id = id;
	}

	public UsuarioAccesoDTO(String usuario) {
		this.usuario = usuario;
	}

	public UsuarioAccesoDTO(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}

	public UsuarioAccesoDTO(Long id, String usuario) {
		this.id=id;
		this.usuario = usuario;
	}

	public UsuarioAccesoDTO(String usuario, String password, String rol) {
		super();
		this.usuario = usuario;
		this.password = password;
		this.rol = rol;
	}
	
	private Long id;
	@NotBlank(message = "{msg.usuario.acceso.usuario}")
	private String usuario;
	@NotBlank(message = "{msg.usuario.acceso.password}")
	private String password;
	private String rol;
	private String tipoAcceso;
	private String descripcion;
	private int status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getTipoAcceso() {
		return tipoAcceso;
	}

	public void setTipoAcceso(String tipoAcceso) {
		this.tipoAcceso = tipoAcceso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
