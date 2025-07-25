package com.sytecso.dao.catalogo;

import com.sytecso.model.CatalogoIconos;

public interface DAOCatalogoIconos {
	public CatalogoIconos getById(Long id);

	public CatalogoIconos create(CatalogoIconos catalogo);

	public CatalogoIconos findIdIconoByNombre(CatalogoIconos catalogo);
}
