package com.sytecso.model;

import java.io.Serializable;
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
@Table(name = "ModulosGui")
public class ModulosGui extends MenuProperties implements Serializable {

	private static final long serialVersionUID = 6346160796459976849L;

	public ModulosGui() {

	}

	public ModulosGui(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	@Id
	@Column(name = "idModulosGui")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nombreModulo")
	private String nombre;
	
	
	@OneToMany(mappedBy = "modulosGui", cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE,
			CascadeType.PERSIST })
	@LazyCollection(LazyCollectionOption.TRUE)
	private Set<ModulosGuiHasRolesAcceso> modulosGuiHasRolesAccesos;

	@OneToMany(mappedBy = "modulosGui", cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE,
			CascadeType.PERSIST })
	@LazyCollection(LazyCollectionOption.TRUE)
	private Set<Seccion> seccion;

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE,
			CascadeType.PERSIST }, fetch = FetchType.LAZY)
	@JoinColumn(name = "catalogoIconos_idcatalogoIconos", referencedColumnName = "idcatalogoIconos", updatable = true, nullable = true, insertable = true)
	private CatalogoIconos catalogoIconos;

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

	@Override
	public CatalogoIconos getCatalogoIconos() {
		return catalogoIconos;
	}

	@Override
	public void setCatalogoIconos(CatalogoIconos catalogoIconos) {
		this.catalogoIconos = catalogoIconos;
	}

	
	public Set<Seccion> getSeccion() {
		return seccion;
	}

	public Set<ModulosGuiHasRolesAcceso> getModulosGuiHasRolesAccesos() {
		return modulosGuiHasRolesAccesos;
	}

	public void setModulosGuiHasRolesAccesos(Set<ModulosGuiHasRolesAcceso> modulosGuiHasRolesAccesos) {
		this.modulosGuiHasRolesAccesos = modulosGuiHasRolesAccesos;
	}

	public void setSeccion(Set<Seccion> seccion) {
		this.seccion = seccion;
	}
}
