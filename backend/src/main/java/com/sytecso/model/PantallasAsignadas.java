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
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "pantallas_asignadas")
public class PantallasAsignadas extends MenuProperties implements Serializable {

	private static final long serialVersionUID = 8448323631461464231L;
	@Id
	@Column(name = "pantallas_asignadasId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "nombrePantalla")
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL, CascadeType.REFRESH })
	@JoinColumn(name = "catalogoIconos_idcatalogoIconos", updatable = true, nullable = true, insertable = true)
	private CatalogoIconos catalogoIconos;

	@OneToMany(mappedBy = "pantallasAsignadas", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.TRUE)
	private Set<PantallaAsignadaHasCatalogoServicios> pantallaAsignadaHasCatalogoServicios;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE,
			CascadeType.PERSIST})
	@JoinColumn(name = "seccion_idseccion", updatable = true, nullable = true, insertable = true)
	private Seccion seccion;

	@Transient
	private String[] servicios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<PantallaAsignadaHasCatalogoServicios> getPantallaAsignadaHasCatalogoServicios() {
		return pantallaAsignadaHasCatalogoServicios;
	}

	public void setPantallaAsignadaHasCatalogoServicios(
			Set<PantallaAsignadaHasCatalogoServicios> pantallaAsignadaHasCatalogoServicios) {
		this.pantallaAsignadaHasCatalogoServicios = pantallaAsignadaHasCatalogoServicios;
	}

	public Seccion getSeccion() {
		return seccion;
	}

	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}

	@Override
	public CatalogoIconos getCatalogoIconos() {
		return catalogoIconos;
	}

	@Override
	public void setCatalogoIconos(CatalogoIconos catalogoIconos) {
		this.catalogoIconos = catalogoIconos;
	}

	public String[] getServicios() {
		return servicios;
	}

	public void setServicios(String[] servicios) {
		this.servicios = servicios;
	}

}
