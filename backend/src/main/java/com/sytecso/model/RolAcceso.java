package com.sytecso.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "rolesAcceso")
public class RolAcceso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1850195755039075672L;

	public RolAcceso() {
		super();
	}

	public RolAcceso(String descripcion, @NotBlank String nombre) {
		super();
		this.descripcion = descripcion;
		this.nombre = nombre;
	}

	public RolAcceso(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public RolAcceso(Long id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	@Id
	@Column(name = "idRolesAcceso")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "DescripcionRol")
	private String descripcion;

	@Column(name = "nombreRol")
	@NotBlank
	private String nombre;

	@OneToMany(mappedBy = "rolesAcceso")
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<UsuarioAcceso> usuariosAcceso;

	@OneToMany(mappedBy = "rolesAcceso")
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<ModulosGuiHasRolesAcceso> modulosGuiHasRolesAcceso;

	@OneToMany(mappedBy = "rolesAcceso")
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<SeccionHasRolAcceso> seccionHasRolesAcceso;

	public Long getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<UsuarioAcceso> getUsuariosAcceso() {
		return usuariosAcceso;
	}

	public void setUsuariosAcceso(List<UsuarioAcceso> usuariosAcceso) {
		this.usuariosAcceso = usuariosAcceso;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<ModulosGuiHasRolesAcceso> getModulosGuiHasRolesAcceso() {
		return modulosGuiHasRolesAcceso;
	}

	public void setModulosGuiHasRolesAcceso(List<ModulosGuiHasRolesAcceso> modulosGuiHasRolesAcceso) {
		this.modulosGuiHasRolesAcceso = modulosGuiHasRolesAcceso;
	}

	/**
	 * @return the seccionHasRolesAcceso
	 */
	public List<SeccionHasRolAcceso> getSeccionHasRolesAcceso() {
		return seccionHasRolesAcceso;
	}

	/**
	 * @param seccionHasRolesAcceso the seccionHasRolesAcceso to set
	 */
	public void setSeccionHasRolesAcceso(List<SeccionHasRolAcceso> seccionHasRolesAcceso) {
		this.seccionHasRolesAcceso = seccionHasRolesAcceso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descripcion, id, modulosGuiHasRolesAcceso, nombre, seccionHasRolesAcceso, usuariosAcceso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RolAcceso))
			return false;
		RolAcceso other = (RolAcceso) obj;
		return Objects.equals(descripcion, other.descripcion) && Objects.equals(id, other.id)
				&& Objects.equals(modulosGuiHasRolesAcceso, other.modulosGuiHasRolesAcceso)
				&& Objects.equals(nombre, other.nombre)
				&& Objects.equals(seccionHasRolesAcceso, other.seccionHasRolesAcceso)
				&& Objects.equals(usuariosAcceso, other.usuariosAcceso);
	}

}
