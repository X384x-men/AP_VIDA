package com.sytecso.dto;

import java.util.Objects;

public class SimpleDTO {
	private Long id;
	private Long idEmpleado;
	private Long idProyecto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, idEmpleado, idProyecto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SimpleDTO)) {
			return false;
		}
		SimpleDTO other = (SimpleDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(idEmpleado, other.idEmpleado)
				&& Objects.equals(idProyecto, other.idProyecto);
	}

}
