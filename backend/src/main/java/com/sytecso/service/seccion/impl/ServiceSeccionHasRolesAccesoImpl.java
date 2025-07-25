package com.sytecso.service.seccion.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.seccion.DAOSeccionHasRolesAcceso;
import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.component.exceptions.MenuException.SeccionNotCreatedException;
import com.sytecso.component.exceptions.MenuException.SeccionNotExistsException;
import com.sytecso.component.exceptions.MenuException.SeccionNotRemovedException;
import com.sytecso.component.exceptions.RolAccesoException.RolNotExistsException;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.Seccion;
import com.sytecso.model.SeccionHasRolAcceso;
import com.sytecso.service.modulos.ServiceModulosGui;
import com.sytecso.service.rolAcceso.ServiceRolAcceso;
import com.sytecso.service.seccion.ServiceSeccion;
import com.sytecso.service.seccion.ServiceSeccionHasRolesAcceso;

@Service
public class ServiceSeccionHasRolesAccesoImpl implements ServiceSeccionHasRolesAcceso {
	@Autowired
	private DAOSeccionHasRolesAcceso daoSeccionHasRolAcceso;
	@Autowired
	private ServiceSeccion serviceSeccion;
	@Autowired
	private ServiceRolAcceso serviceRolAcceso;
	@Autowired
	private ServiceModulosGui serviceModulosGui;

	@Override
	public boolean create(SeccionRolDTO seccionRolDTO)
			throws SeccionNotExistsException, RolNotExistsException, SeccionNotCreatedException {
		SeccionHasRolAcceso seccion = createSeccionHasRol(seccionRolDTO);
		if (seccion != null) {
			if (this.daoSeccionHasRolAcceso.create(seccion))
				return true;
			throw new SeccionNotCreatedException("Ocurrio un error al agregar");
		}

		throw new SeccionNotExistsException("La seccion: ".concat(seccionRolDTO.getSeccion())
				.concat(" no pertenece al modulo: ").concat(seccionRolDTO.getModulo()));
	}

	/**
	 * @param seccionRolDTO
	 * @throws RolNotExistsException
	 * @throws SeccionNotExistsException
	 */
	private SeccionHasRolAcceso createSeccionHasRol(SeccionRolDTO seccionRolDTO)
			throws RolNotExistsException, SeccionNotExistsException {
		RolAcceso rol = this.serviceRolAcceso.findByRol(seccionRolDTO.getRol());
		Seccion seccion = this.serviceSeccion.findSeccionByName(seccionRolDTO.getSeccion());
		ModulosGui modulosGui = seccion.getModulosGui();
		if (modulosGui.getNombre().equals(seccionRolDTO.getModulo())) {
			SeccionHasRolAcceso seccionHasRolAcceso = new SeccionHasRolAcceso();
			seccionHasRolAcceso.setIdRolesAcceso(rol.getId());
			seccionHasRolAcceso.setIdSeccion(seccion.getId());
			return seccionHasRolAcceso;
		}
		return null;
	}

	@Override
	public boolean remove(SeccionRolDTO seccionRolDTO)
			throws SeccionNotExistsException, RolNotExistsException, SeccionNotRemovedException {
		SeccionHasRolAcceso seccion = this.createSeccionHasRol(seccionRolDTO);
		if (seccion != null) {
			if (this.daoSeccionHasRolAcceso.remove(seccion))
				return true;
			throw new SeccionNotRemovedException("Ocurrio un error al remover la seccion: "
					.concat(seccionRolDTO.getSeccion()).concat(" del modulo: ").concat(seccionRolDTO.getModulo())
					.concat(" para el rol: ").concat(seccionRolDTO.getRol()));
		}

		throw new SeccionNotExistsException("La seccion: ".concat(seccionRolDTO.getSeccion())
				.concat(" no pertenece al modulo: ").concat(seccionRolDTO.getModulo()));
	}

	@Override
	public boolean addAll(SeccionRolDTO seccionRolDTO)
			throws SeccionNotExistsException, RolNotExistsException, SeccionNotCreatedException {
		RolAcceso rol = this.serviceRolAcceso.findByRol(seccionRolDTO.getRol());
		ModulosGui modulosGui = this.serviceModulosGui.findAllInnerModuloGuiByName(seccionRolDTO.getModulo());
		Set<Seccion> secciones = modulosGui.getSeccion();
		Set<SeccionHasRolAcceso> seccionHasRoles = new HashSet<>();
		for (Iterator<Seccion> iterator = secciones.iterator(); iterator.hasNext();) {
			Seccion seccion = iterator.next();
			seccionHasRoles.add(new SeccionHasRolAcceso(seccion.getId(), rol.getId()));
		}
		if (!seccionHasRoles.isEmpty())
			return this.daoSeccionHasRolAcceso.create(seccionHasRoles);
		return false;
	}

	@Override
	public boolean remove(String columnName, long id) {
		return this.daoSeccionHasRolAcceso.removeById(columnName, id);
	}

	@Override
	public boolean create(Set<SeccionHasRolAcceso> secciones) {
		return this.daoSeccionHasRolAcceso.create(secciones);
	}

	@Override
	public List<SeccionHasRolAcceso> findByAuthority(String authority) {
		return new ArrayList<>(this.daoSeccionHasRolAcceso.findByAuthority(authority));
	}

}
