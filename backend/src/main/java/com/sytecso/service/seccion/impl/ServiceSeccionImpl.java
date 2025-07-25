package com.sytecso.service.seccion.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.seccion.DAOSeccion;
import com.sytecso.dto.modulosgui.ModuloDTO;
import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.component.exceptions.MenuException;
import com.sytecso.component.exceptions.MenuException.SeccionNotExistsException;
import com.sytecso.component.exceptions.RolAccesoException.RolNotExistsException;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.ModulosGuiHasRolesAcceso;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.Seccion;
import com.sytecso.service.modulos.ServiceModulosGui;
import com.sytecso.service.modulos.ServiceModulosGuiHasRolAcceso;
import com.sytecso.service.rolAcceso.ServiceRolAcceso;
import com.sytecso.service.seccion.ServiceSeccion;
import com.sytecso.service.seccion.ServiceSeccionHasRolesAcceso;

@Service
public class ServiceSeccionImpl implements ServiceSeccion {
	@Autowired
	private DAOSeccion daoSeccion;
	@Autowired
	private ServiceRolAcceso serviceRolAcceso;
	@Autowired
	private ServiceModulosGuiHasRolAcceso serviceModulosGuiHasRolAcceso;
	@Autowired
	ServiceSeccionHasRolesAcceso serviceSeccionHasRolAcceso;
	@Autowired
	private ServiceModulosGui serviceModulosGui;

	@Override
	public List<Seccion> getAllByIdModulosGui(Long id) {
		return this.daoSeccion.getAllByIdModulosGui(id);
	}

	@Override
	public List<ModuloDTO> getSeccionNotPresentByRolAndModulo(SeccionRolDTO seccionRolDTO) {
		return this.daoSeccion.getSeccionNotPresentByRolAndModulo(seccionRolDTO);
	}

	@Override
	public List<ModuloDTO> getSeccionPresentByRol(SeccionRolDTO seccionRolDTO) {
		return this.daoSeccion.getSeccionPresentByRol(seccionRolDTO);
	}

	@Override
	public List<ModuloDTO> getSeccionByRol(SeccionRolDTO seccionRolDTO, int value) {
		switch (value) {
		case 1:
			return this.getSeccionNotPresentByRolAndModulo(seccionRolDTO);
		case 2:
			return this.getSeccionPresentByRol(seccionRolDTO);
		case 3:
			return this.serviceModulosGui.getModuloPresentByRol(seccionRolDTO.getRol());
		case 4:
		return this.serviceModulosGui.getModuloNotPresentByRol(seccionRolDTO.getRol());
		default:
			break;
		}
		return new ArrayList<>();
	}
	@Override
	public Seccion findSeccionByName(String seccion) throws SeccionNotExistsException {
		Seccion seccion2 = this.daoSeccion.findSeccionByName(seccion);
		if (seccion == null)
			throw new MenuException.SeccionNotExistsException("La seccion: ".concat(seccion).concat(" no existe"));
		return seccion2;
	}

	@Override
	public void removeSeccioFromRol(SeccionRolDTO seccionRolDTO) {
		try {
			ModulosGuiHasRolesAcceso modulo = findModulosGui(seccionRolDTO);
			this.serviceModulosGuiHasRolAcceso.remove(modulo);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
	}

	/**
	 * @param seccionRolDTO
	 * @return
	 * @throws SeccionNotExistsException 
	 * @throws RolNotExistsException 
	 */
	private ModulosGuiHasRolesAcceso findModulosGui(SeccionRolDTO seccionRolDTO) throws SeccionNotExistsException, RolNotExistsException {
		RolAcceso rolAcceso = this.serviceRolAcceso.findByRol(seccionRolDTO.getRol());
		Seccion seccion = findSeccionByName(seccionRolDTO.getSeccion());
		ModulosGuiHasRolesAcceso modulo = new ModulosGuiHasRolesAcceso();
		modulo.setIdModulos(seccion.getModulosGui().getId());
		modulo.setIdRolesAcceso(rolAcceso.getId());
		return modulo;
	}

	// TODO HACER PRUBEBAS AGREGANDO UNA NUEVA SECCION
	@SuppressWarnings("unchecked")
	@Override
	public Set<Seccion> findOrCreateSeccionByNombre(Set<Seccion> secciones) {
		Set<Seccion> find = this.findIdSeccionesByNombre(secciones);
		if (!find.isEmpty() && find.size() == secciones.size())
			return new HashSet<>();
		else {
			if (!find.isEmpty()) {
				Set<Seccion> result = (Set<Seccion>) find.stream().distinct().filter(secciones::contains)
						.collect(Collectors.toList());
				return this.create(result);
			} else {
				return this.create(secciones);
			}
		}
	}

	@Override
	public Set<Seccion> findIdSeccionesByNombre(Set<Seccion> secciones) {
		try {
			return this.daoSeccion.findIdSeccionesByNombre(secciones);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

	@Override
	public Set<Seccion> create(Set<Seccion> secciones) {
		try {
			return this.daoSeccion.create(secciones);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return new HashSet<>();
	}

}
