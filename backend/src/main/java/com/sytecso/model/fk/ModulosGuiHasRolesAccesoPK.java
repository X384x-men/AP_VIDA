package com.sytecso.model.fk;

import java.io.Serializable;

import javax.persistence.Column;

public class ModulosGuiHasRolesAccesoPK implements Serializable {

	private static final long serialVersionUID = -9091511103473166431L;
	@Column(name = "ModulosGui_idModulosGui")
	private Long idModulos;
	@Column(name = "rolesAcceso_idRolesAcceso")
	private Long idRolesAcceso;

	public Long getIdModulos() {
		return idModulos;
	}

	public Long getIdRolesAcceso() {
		return idRolesAcceso;
	}

	public void setIdModulos(Long idModulos) {
		this.idModulos = idModulos;
	}

	public void setIdRolesAcceso(Long idRolesAcceso) {
		this.idRolesAcceso = idRolesAcceso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idModulos == null) ? 0 : idModulos.hashCode());
		result = prime * result + ((idRolesAcceso == null) ? 0 : idRolesAcceso.hashCode());
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
		ModulosGuiHasRolesAccesoPK other = (ModulosGuiHasRolesAccesoPK) obj;
		if (idModulos == null) {
			if (other.idModulos != null)
				return false;
		} else if (!idModulos.equals(other.idModulos))
			return false;
		if (idRolesAcceso == null) {
			if (other.idRolesAcceso != null)
				return false;
		} else if (!idRolesAcceso.equals(other.idRolesAcceso))
			return false;
		return true;
	}
	
}
