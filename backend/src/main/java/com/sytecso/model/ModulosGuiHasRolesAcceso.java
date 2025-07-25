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

import com.sytecso.model.fk.ModulosGuiHasRolesAccesoPK;

@Entity
@IdClass(ModulosGuiHasRolesAccesoPK.class)
@Table(name = "ModulosGui_has_rolesAcceso")
public class ModulosGuiHasRolesAcceso implements Serializable {

	private static final long serialVersionUID = 7226844352692350975L;
	@Id
	private Long idModulos;
	@Id
	private Long idRolesAcceso;
	@MapsId("rolesAcceso_idRolesAcceso")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "rolesAcceso_idRolesAcceso", referencedColumnName = "idRolesAcceso", nullable = false, updatable = true, insertable = true)
	private RolAcceso rolesAcceso;

	@MapsId("ModulosGui_idModulosGui")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "ModulosGui_idModulosGui", referencedColumnName = "idModulosGui", nullable = false, updatable = true, insertable = true)
	private ModulosGui modulosGui;

	public Long getIdModulos() {
		return idModulos;
	}

	public Long getIdRolesAcceso() {
		return idRolesAcceso;
	}

	public RolAcceso getRolesAcceso() {
		return rolesAcceso;
	}

	public void setIdModulos(Long idModulos) {
		this.idModulos = idModulos;
	}

	public void setIdRolesAcceso(Long idRolesAcceso) {
		this.idRolesAcceso = idRolesAcceso;
	}

	public void setRolesAcceso(RolAcceso rolesAcceso) {
		this.rolesAcceso = rolesAcceso;
	}

	public ModulosGui getModulosGui() {
		return modulosGui;
	}

	public void setModulosGui(ModulosGui modulosGui) {
		this.modulosGui = modulosGui;
	}

}
