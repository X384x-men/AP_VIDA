package com.sytecso.service.pantallas;

import java.util.Set;

import com.sytecso.model.PantallaAsignadaHasCatalogoServicios;
import com.sytecso.model.PantallasAsignadas;

public interface ServicePantallasHasCatalogoServicio {

	public Set<PantallaAsignadaHasCatalogoServicios> findOrCreate(PantallasAsignadas pantallas, String[] servicios);

	public Set<PantallaAsignadaHasCatalogoServicios> findByIdPantallaAsignada(PantallasAsignadas pantallas);

	public Set<PantallaAsignadaHasCatalogoServicios> create(PantallasAsignadas pantallas, String[] servicios);
}
