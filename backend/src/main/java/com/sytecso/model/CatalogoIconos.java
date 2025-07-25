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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "catalogoIconos")
public class CatalogoIconos implements Serializable {

	private static final long serialVersionUID = -1537182737711629902L;

	@Id
	@Column(name = "idcatalogoIconos")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombreIcono")
	private String nombre;

	@OneToMany(mappedBy = "catalogoIconos", cascade = { CascadeType.REFRESH, CascadeType.REMOVE,
			CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private Set<ModulosGui> modulosGui;

	@OneToMany(mappedBy = "catalogoIconos", cascade = { CascadeType.REFRESH, CascadeType.REMOVE,
			CascadeType.PERSIST }, fetch = FetchType.LAZY)
	private Set<Seccion> seccion;

	@OneToMany(mappedBy = "catalogoIconos", cascade = { CascadeType.REFRESH, CascadeType.REMOVE, CascadeType.PERSIST })
	@LazyCollection(LazyCollectionOption.TRUE)
	private Set<PantallasAsignadas> pantallaAsignada;

	public CatalogoIconos() {

	}

	public CatalogoIconos(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public CatalogoIconos(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public Set<ModulosGui> getModulosGui() {
		return modulosGui;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setModulosGui(Set<ModulosGui> modulosGui) {
		this.modulosGui = modulosGui;
	}

	public Set<PantallasAsignadas> getPantallaAsignada() {
		return pantallaAsignada;
	}

	public void setPantallaAsignada(Set<PantallasAsignadas> pantallaAsignada) {
		this.pantallaAsignada = pantallaAsignada;
	}

	public Set<Seccion> getSeccion() {
		return seccion;
	}

	public void setSeccion(Set<Seccion> seccion) {
		this.seccion = seccion;
	}

}
