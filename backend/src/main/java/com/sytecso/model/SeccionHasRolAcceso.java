package com.sytecso.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.sytecso.model.fk.SeccionHasRolAccesoPK;

@Entity
@IdClass(SeccionHasRolAccesoPK.class)
@Table(name = "seccion_has_rolesAcceso")
public class SeccionHasRolAcceso implements Serializable {
	public SeccionHasRolAcceso() {

	}

	public SeccionHasRolAcceso(Long idSeccion, Long idRolesAcceso) {
		super();
		this.idSeccion = idSeccion;
		this.idRolesAcceso = idRolesAcceso;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8674977196605970858L;
	@Id
	private Long idSeccion;
	@Id
	private Long idRolesAcceso;

	@MapsId("rolesAcceso_idRolesAcceso")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "rolesAcceso_idRolesAcceso", referencedColumnName = "idRolesAcceso", nullable = false, updatable = true, insertable = true)
	private RolAcceso rolesAcceso;

	@MapsId("seccion_idSeccion")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "seccion_idSeccion", referencedColumnName = "idSeccion", nullable = false, updatable = true, insertable = true)
	private Seccion seccion;

	/**
	 * @return the idSeccion
	 */
	public Long getIdSeccion() {
		return idSeccion;
	}

	/**
	 * @return the idRolesAcceso
	 */
	public Long getIdRolesAcceso() {
		return idRolesAcceso;
	}

	/**
	 * @return the rolesAcceso
	 */
	public RolAcceso getRolesAcceso() {
		return rolesAcceso;
	}

	/**
	 * @return the seccion
	 */
	public Seccion getSeccion() {
		return seccion;
	}

	/**
	 * @param idSeccion the idSeccion to set
	 */
	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
	}

	/**
	 * @param idRolesAcceso the idRolesAcceso to set
	 */
	public void setIdRolesAcceso(Long idRolesAcceso) {
		this.idRolesAcceso = idRolesAcceso;
	}

	/**
	 * @param rolesAcceso the rolesAcceso to set
	 */
	public void setRolesAcceso(RolAcceso rolesAcceso) {
		this.rolesAcceso = rolesAcceso;
	}

	/**
	 * @param seccion the seccion to set
	 */
	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

}
