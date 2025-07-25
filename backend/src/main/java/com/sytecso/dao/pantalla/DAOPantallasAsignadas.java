package com.sytecso.dao.pantalla;

import java.util.Set;

import com.sytecso.model.PantallasAsignadas;

public interface DAOPantallasAsignadas {
	public Set<PantallasAsignadas> create(Set<PantallasAsignadas> pantallas);

	public Set<PantallasAsignadas> findPantallasByNombre(Set<PantallasAsignadas> pantallas);
}
