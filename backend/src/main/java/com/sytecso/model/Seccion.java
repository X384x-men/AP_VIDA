package com.sytecso.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "seccion")
public class Seccion extends MenuProperties implements Serializable {

	private static final long serialVersionUID = 6798489468330374413L;

	public Seccion() {
		super();
	}

	public Seccion(Long id,  String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public Seccion(Long id, String nombre, ModulosGui modulosGui) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.modulosGui = modulosGui;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idseccion")
	private Long id;
	
	@Column(name = "nombre")
	private String nombre;


	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE,
			CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "catalogoIconos_idcatalogoIconos", referencedColumnName = "idcatalogoIconos", updatable = true, nullable = true, insertable = true)
	private CatalogoIconos catalogoIconos;

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE,
			CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "ModulosGui_idModulosGui", referencedColumnName = "idModulosGui", nullable = false, updatable = true, insertable = true)
	private ModulosGui modulosGui;

	@OneToMany(mappedBy = "seccion", cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE,
			CascadeType.PERSIST })
	@LazyCollection(LazyCollectionOption.TRUE)
	private Set<PantallasAsignadas> pantallasAsignadas;

	@OneToMany(mappedBy = "seccion")
	@LazyCollection(LazyCollectionOption.TRUE)
	private List<SeccionHasRolAcceso> seccionHasRolesAcceso;

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public ModulosGui getModulosGui() {
		return modulosGui;
	}

	public void setModulosGui(ModulosGui modulosGui) {
		this.modulosGui = modulosGui;
	}

	public Set<PantallasAsignadas> getPantallasAsignadas() {
		return pantallasAsignadas;
	}

	public void setPantallasAsignadas(Set<PantallasAsignadas> pantallasAsignadas) {
		this.pantallasAsignadas = pantallasAsignadas;
	}

	@Override
	public CatalogoIconos getCatalogoIconos() {
		return catalogoIconos;
	}

	@Override
	public void setCatalogoIconos(CatalogoIconos catalogoIconos) {
		this.catalogoIconos = catalogoIconos;
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

}
