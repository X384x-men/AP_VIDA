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

import com.sytecso.model.fk.PantallaAsignadaHasCatalogoServiciosPK;

@Entity
@IdClass(PantallaAsignadaHasCatalogoServiciosPK.class)
@Table(name = "pantallas_asignadas_has_catalogoServicios")
public class PantallaAsignadaHasCatalogoServicios implements Serializable {

	private static final long serialVersionUID = 8091275006483780328L;
	@Id
	private Long idPantallaAsignada;
	@Id
	private Long idCatalogoServicios;

	@MapsId("catalogoServicios_idcatalogoServicios")
	@ManyToOne(cascade = { CascadeType.ALL, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "catalogoServicios_idcatalogoServicios", updatable = true, insertable = true, nullable = false)
	private CatalogoServicios catalogoServicios;

	@MapsId("pantallas_asignadas_pantallas_asignadasId")
	@ManyToOne(cascade = { CascadeType.ALL, CascadeType.REFRESH }, fetch = FetchType.LAZY)
	@JoinColumn(name = "pantallas_asignadas_pantallas_asignadasId", updatable = true, insertable = true, nullable = false)
	private PantallasAsignadas pantallasAsignadas;

	public Long getIdPantallaAsignada() {
		return idPantallaAsignada;
	}

	public void setIdPantallaAsignada(Long idPantallaAsignada) {
		this.idPantallaAsignada = idPantallaAsignada;
	}

	public Long getIdCatalogoServicios() {
		return idCatalogoServicios;
	}

	public void setIdCatalogoServicios(Long idCatalogoServicios) {
		this.idCatalogoServicios = idCatalogoServicios;
	}

	public CatalogoServicios getCatalogoServicios() {
		return catalogoServicios;
	}

	public void setCatalogoServicios(CatalogoServicios catalogoServicios) {
		this.catalogoServicios = catalogoServicios;
	}

	public PantallasAsignadas getPantallasAsignadas() {
		return pantallasAsignadas;
	}

	public void setPantallasAsignadas(PantallasAsignadas pantallasAsignadas) {
		this.pantallasAsignadas = pantallasAsignadas;
	}

}
