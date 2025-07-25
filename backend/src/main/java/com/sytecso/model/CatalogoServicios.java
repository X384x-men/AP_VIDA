package com.sytecso.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "catalogoServicios")
public class CatalogoServicios implements Serializable {

	private static final long serialVersionUID = -2703234473884797362L;
	@Id
	@Column(name = "idCatalogoServicios")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nombreServicio")
	private String nombre;
	@OneToMany(mappedBy = "catalogoServicios", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.TRUE)
	private Set<PantallaAsignadaHasCatalogoServicios> pantallaAsignadaHasCatalogoServicios;

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


}
