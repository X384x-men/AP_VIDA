package com.sytecso.dto.usuario;

import java.util.Objects;

import com.sytecso.dto.SimpleDTO;

public class UserDTO extends SimpleDTO {
	private String fechaCreacion;
	private Integer asociado;
	private Integer estatus;

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Integer getAsociado() {
		return asociado;
	}

	public void setAsociado(Integer asociado) {
		this.asociado = asociado;
	}

	public Integer getEstatus() {
		return estatus;
	}

	public void setEstatus(Integer estatus) {
		this.estatus = estatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(asociado, estatus, fechaCreacion);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof UserDTO)) {
			return false;
		}
		UserDTO other = (UserDTO) obj;
		return Objects.equals(asociado, other.asociado) && Objects.equals(estatus, other.estatus)
				&& Objects.equals(fechaCreacion, other.fechaCreacion);
	}

}
