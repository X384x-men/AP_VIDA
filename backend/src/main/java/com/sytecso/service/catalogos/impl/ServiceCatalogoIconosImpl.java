package com.sytecso.service.catalogos.impl;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.catalogo.DAOCatalogoIconos;
import com.sytecso.model.CatalogoIconos;
import com.sytecso.service.catalogos.ServiceCatalogoIconos;

@Service
public class ServiceCatalogoIconosImpl implements ServiceCatalogoIconos {
	@Autowired
	private DAOCatalogoIconos datoCatalogoIconos;

	@Override
	public CatalogoIconos getById(Long id) {
		return this.datoCatalogoIconos.getById(id);
	}

	@Override
	public CatalogoIconos findOrCreate(CatalogoIconos catalogoIconos) {
		if (catalogoIconos != null) {
			if (catalogoIconos.getId() != null) {
				catalogoIconos.setPantallaAsignada(new HashSet<>());
				return catalogoIconos;
			} else if (catalogoIconos.getId() == null) {
				CatalogoIconos catalogo = this.findIdIconoByNombre(catalogoIconos);
				if (catalogo != null) {
					return catalogo;
				} else {
					CatalogoIconos c = this.create(catalogoIconos);
					if (c != null) {
						return c;
					}
				}
			}
		}
		return null;
	}

	@Override
	public CatalogoIconos create(CatalogoIconos catalogo) {
		return this.datoCatalogoIconos.create(catalogo);
	}

	@Override
	public CatalogoIconos findIdIconoByNombre(CatalogoIconos catalogoIconos) {
		return this.datoCatalogoIconos.findIdIconoByNombre(catalogoIconos);
	}

}
