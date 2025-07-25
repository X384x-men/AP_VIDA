package com.sytecso.service.catalogos;

import com.sytecso.model.CatalogoIconos;

public interface ServiceCatalogoIconos {
	public CatalogoIconos getById(Long id);

	public CatalogoIconos findOrCreate(CatalogoIconos catalogoIconos);

	public CatalogoIconos create(CatalogoIconos catalogo);

	public CatalogoIconos findIdIconoByNombre(CatalogoIconos catalogoIconos);
}
