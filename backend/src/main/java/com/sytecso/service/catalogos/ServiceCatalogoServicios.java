package com.sytecso.service.catalogos;

import java.util.List;
import java.util.Set;

import com.sytecso.model.CatalogoServicios;
import com.sytecso.model.PantallasAsignadas;

public interface ServiceCatalogoServicios {

	public Set<CatalogoServicios> findByNombreOrCreate(PantallasAsignadas pantallas);

	public Set<CatalogoServicios> findByNombreOrCreate(String[] catalogos);

	public Set<CatalogoServicios> findByNombre(String[] catalogos);

	public Set<CatalogoServicios> create(String[] catalogos);

	public Set<CatalogoServicios> create(List<CatalogoServicios> catalogos);
}
