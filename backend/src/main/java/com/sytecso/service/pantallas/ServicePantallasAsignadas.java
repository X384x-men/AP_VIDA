package com.sytecso.service.pantallas;

import java.util.Set;

import com.sytecso.model.PantallasAsignadas;

public interface ServicePantallasAsignadas {
	public Set<PantallasAsignadas> findPantallasByNombre(Set<PantallasAsignadas> pantallas);

	public Set<PantallasAsignadas> create(Set<PantallasAsignadas> pantallas);

	public Set<PantallasAsignadas> findPantallasOrCreate(Set<PantallasAsignadas> pantallas);
}
