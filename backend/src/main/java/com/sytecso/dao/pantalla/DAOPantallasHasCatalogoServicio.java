package com.sytecso.dao.pantalla;

import java.util.Set;

import com.sytecso.model.PantallaAsignadaHasCatalogoServicios;
import com.sytecso.model.PantallasAsignadas;

public interface DAOPantallasHasCatalogoServicio {
	public Set<PantallaAsignadaHasCatalogoServicios> findByIdPantallaAsignada(PantallasAsignadas pantallas);

	public Set<PantallaAsignadaHasCatalogoServicios> create(PantallasAsignadas pantallas, String[] servicios);
	public PantallaAsignadaHasCatalogoServicios findByIdCatalogoAndIdPantalla(Long idCatalogo,Long idPantalla); 
}
