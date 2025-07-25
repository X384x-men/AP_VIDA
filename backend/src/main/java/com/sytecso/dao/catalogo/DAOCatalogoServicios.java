package com.sytecso.dao.catalogo;

import java.util.List;
import java.util.Set;

import com.sytecso.model.CatalogoServicios;

public interface DAOCatalogoServicios {
	public Set<CatalogoServicios> findByNombre(String[] catalogos);

	public Set<CatalogoServicios> create(String[] catalogos);
	public Set<CatalogoServicios> create(List<CatalogoServicios> catalogos);
}
