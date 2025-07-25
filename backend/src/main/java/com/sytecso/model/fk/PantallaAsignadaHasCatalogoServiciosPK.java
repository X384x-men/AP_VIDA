package com.sytecso.model.fk;

import java.io.Serializable;

import javax.persistence.Column;

public class PantallaAsignadaHasCatalogoServiciosPK implements Serializable {

	private static final long serialVersionUID = -3142466928298887256L;
	@Column(name = "pantallas_asignadas_pantallas_asignadasId")
	private Long idPantallaAsignada;
	@Column(name = "catalogoServicios_idcatalogoServicios")
	private Long idCatalogoServicios;

	public Long getIdPantallaAsignada() {
		return idPantallaAsignada;
	}

	public void setIdPantallaAsignada(Long idPantallaAsignada) {
		this.idPantallaAsignada = idPantallaAsignada;
	}

	public Long getIdCatalogoServicios() {
		return idCatalogoServicios;
	}

	public void setIdCatalogoServicios(Long idCatalogoServicios) {
		this.idCatalogoServicios = idCatalogoServicios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCatalogoServicios == null) ? 0 : idCatalogoServicios.hashCode());
		result = prime * result + ((idPantallaAsignada == null) ? 0 : idPantallaAsignada.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PantallaAsignadaHasCatalogoServiciosPK other = (PantallaAsignadaHasCatalogoServiciosPK) obj;
		if (idCatalogoServicios == null) {
			if (other.idCatalogoServicios != null)
				return false;
		} else if (!idCatalogoServicios.equals(other.idCatalogoServicios))
			return false;
		if (idPantallaAsignada == null) {
			if (other.idPantallaAsignada != null)
				return false;
		} else if (!idPantallaAsignada.equals(other.idPantallaAsignada))
			return false;
		return true;
	}

}
