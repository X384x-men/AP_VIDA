package com.sytecso.dto.usuarioacceso;

import java.io.Serializable;

public class DTOUsuarioAcceso  implements Serializable{
	

	private static final long serialVersionUID = 1546508141483812411L;
	private Long idUsuario;
	private String usuario;
	private String password;
	
	public String getUsuario() {
		return usuario;
	}
	public String getPassword() {
		return password;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}	
	
	
}
