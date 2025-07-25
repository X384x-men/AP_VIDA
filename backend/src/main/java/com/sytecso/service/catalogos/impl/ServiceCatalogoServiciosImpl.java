package com.sytecso.service.catalogos.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.catalogo.DAOCatalogoServicios;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.CatalogoServicios;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.service.catalogos.ServiceCatalogoServicios;

@Service
public class ServiceCatalogoServiciosImpl implements ServiceCatalogoServicios {
	@Autowired
	private DAOCatalogoServicios daoCatalogoServicios;

	@Override
	public Set<CatalogoServicios> findByNombreOrCreate(String[] catalogos) {
		if (catalogos.length > 0) {
			try {
				Set<CatalogoServicios> result = this.findByNombre(catalogos);
				if (!result.isEmpty() && result.size() == catalogos.length) {
					return result;
				} else {
					if (result.isEmpty()) {
						return this.create(catalogos);
					} else {
						List<String> current = new ArrayList<>(Arrays.asList(catalogos));
						List<String> filter = current
								.stream().distinct().filter(n -> result.stream().noneMatch(m->m.getNombre().equals(n)))
								.collect(Collectors.toList());
						result.addAll(this.create(filter.stream().toArray(String[]::new)));
						return result;
					}
				}

			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}
		}
		return new HashSet<>();
	}

	@Override
	public Set<CatalogoServicios> findByNombre(String[] catalogos) {
		try {
			return this.daoCatalogoServicios.findByNombre(catalogos);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<CatalogoServicios> create(String[] catalogos) {
		try {
			return this.daoCatalogoServicios.create(catalogos);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<CatalogoServicios> findByNombreOrCreate(PantallasAsignadas pantallas) {
		if (pantallas.getServicios().length > 0) {
			return this.findByNombreOrCreate(pantallas.getServicios());
		}
		return new HashSet<>();
	}

	@Override
	public Set<CatalogoServicios> create(List<CatalogoServicios> catalogos) {
		try {
			return this.daoCatalogoServicios.create(catalogos);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

}
