package com.sytecso.dto.usuario;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

	private static final long serialVersionUID = 6009681856397009440L;
	
	
	private Long idUsuario;
	private String FechaCreacion;
	private Integer Asociado;
	private Integer estatus;
	private Integer Empleado_idEmpleado;
	private Integer Proyecto_idProyecto;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getFechaCreacion() {
		return FechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		FechaCreacion = fechaCreacion;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	public Integer getEmpleado_idEmpleado() {
		return Empleado_idEmpleado;
	}

	public void setEmpleado_idEmpleado(Integer empleado_idEmpleado) {
		Empleado_idEmpleado = empleado_idEmpleado;
	}

	public Integer getProyecto_idProyecto() {
		return Proyecto_idProyecto;
	}

	public void setProyecto_idProyecto(Integer proyecto_idProyecto) {
		Proyecto_idProyecto = proyecto_idProyecto;
	}

	public Integer getAsociado() {
		return Asociado;
	}

	public void setAsociado(Integer asociado) {
		Asociado = asociado;
	}

}
