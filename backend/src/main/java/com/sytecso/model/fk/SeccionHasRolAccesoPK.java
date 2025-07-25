package com.sytecso.model.fk;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class SeccionHasRolAccesoPK implements Serializable {

	private static final long serialVersionUID = -7095667612232965368L;
	@Column(name = "rolesAcceso_idRolesAcceso")
	private Long idRolesAcceso;
	@Column(name = "seccion_idSeccion")
	private Long idSeccion;

	/**
	 * @return the idRolesAcceso
	 */
	public Long getIdRolesAcceso() {
		return idRolesAcceso;
	}

	/**
	 * @return the idSeccion
	 */
	public Long getIdSeccion() {
		return idSeccion;
	}

	/**
	 * @param idRolesAcceso the idRolesAcceso to set
	 */
	public void setIdRolesAcceso(Long idRolesAcceso) {
		this.idRolesAcceso = idRolesAcceso;
	}

	/**
	 * @param idSeccion the idSeccion to set
	 */
	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idRolesAcceso, idSeccion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof SeccionHasRolAccesoPK))
			return false;
		SeccionHasRolAccesoPK other = (SeccionHasRolAccesoPK) obj;
		return Objects.equals(idRolesAcceso, other.idRolesAcceso) && Objects.equals(idSeccion, other.idSeccion);
	}

}
