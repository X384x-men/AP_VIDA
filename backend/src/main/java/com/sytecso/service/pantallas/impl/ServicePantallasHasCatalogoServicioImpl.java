package com.sytecso.service.pantallas.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.pantalla.DAOPantallasHasCatalogoServicio;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.PantallaAsignadaHasCatalogoServicios;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.service.pantallas.ServicePantallasHasCatalogoServicio;

@Service
public class ServicePantallasHasCatalogoServicioImpl implements ServicePantallasHasCatalogoServicio {
	@Autowired
	private DAOPantallasHasCatalogoServicio daoPantallasAsignadasHasCatalogoServicios;

	@Override
	public Set<PantallaAsignadaHasCatalogoServicios> findOrCreate(PantallasAsignadas pantallas, String[] servicios) {
		if (pantallas != null) {
			try {
				Set<PantallaAsignadaHasCatalogoServicios> result = this.findByIdPantallaAsignada(pantallas);
				if (!result.isEmpty() && result.size() == pantallas.getPantallaAsignadaHasCatalogoServicios().size()) {
					return result;
				} else if (servicios.length > 0) {
					return this.create(pantallas, servicios);
				}

			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}

		}
		return new HashSet<>();
	}

	@Override
	public Set<PantallaAsignadaHasCatalogoServicios> findByIdPantallaAsignada(PantallasAsignadas pantallas) {
		try {
			return this.daoPantallasAsignadasHasCatalogoServicios.findByIdPantallaAsignada(pantallas);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<PantallaAsignadaHasCatalogoServicios> create(PantallasAsignadas pantallas, String[] servicios) {
		if (pantallas != null && servicios.length > 0) {
			try {
				return this.daoPantallasAsignadasHasCatalogoServicios.create(pantallas, servicios);
			} catch (Exception e) {
				SytecsoController.logClassAndMethodWithException(e);
			}

		}
		return new HashSet<>();
	}

}
