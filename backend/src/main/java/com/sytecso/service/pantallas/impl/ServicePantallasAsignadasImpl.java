package com.sytecso.service.pantallas.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.pantalla.DAOPantallasAsignadas;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.PantallasAsignadas;
import com.sytecso.service.pantallas.ServicePantallasAsignadas;

@Service
public class ServicePantallasAsignadasImpl implements ServicePantallasAsignadas {
	@Autowired private DAOPantallasAsignadas daoPantallasAsignadas;
	@Override
	public Set<PantallasAsignadas> findPantallasByNombre(Set<PantallasAsignadas> pantallas) {
		try {
			return this.daoPantallasAsignadas.findPantallasByNombre(pantallas);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<PantallasAsignadas> create(Set<PantallasAsignadas> pantallas) {
		try {
			return this.daoPantallasAsignadas.create(pantallas);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<PantallasAsignadas> findPantallasOrCreate(Set<PantallasAsignadas> pantallas) {
//		Set<PantallasAsignadas> result = this.findPantallasByNombre(pantallas);
//		if (!result.isEmpty() && result.size() == pantallas.size())
//			return pantallas;
//		else
			return this.create(pantallas);
	}

}
