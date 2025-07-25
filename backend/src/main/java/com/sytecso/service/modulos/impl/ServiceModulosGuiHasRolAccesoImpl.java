package com.sytecso.service.modulos.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.modulos.DAOModulosGuiHasRolAcceso;
import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.component.exceptions.SytecsoController;
import com.sytecso.model.ModulosGui;
import com.sytecso.model.ModulosGuiHasRolesAcceso;
import com.sytecso.model.RolAcceso;
import com.sytecso.model.Seccion;
import com.sytecso.model.SeccionHasRolAcceso;
import com.sytecso.service.modulos.ServiceModulosGui;
import com.sytecso.service.modulos.ServiceModulosGuiHasRolAcceso;
import com.sytecso.service.rolAcceso.ServiceRolAcceso;
import com.sytecso.service.seccion.ServiceSeccionHasRolesAcceso;

@Service
public class ServiceModulosGuiHasRolAccesoImpl implements ServiceModulosGuiHasRolAcceso {
	@Autowired
	private DAOModulosGuiHasRolAcceso daoModulosGuiHasRolesAcceso;
	@Autowired
	private ServiceModulosGui serviceModulosGui;
	@Autowired
	private ServiceRolAcceso serviceRolAcceso;
	@Autowired
	private ServiceSeccionHasRolesAcceso serviceSeccionHasRolAcceso;

	@Override
	public boolean remove(ModulosGuiHasRolesAcceso modulos) {
		return this.daoModulosGuiHasRolesAcceso.remove(modulos);
	}

	@Override
	public boolean create(ModulosGuiHasRolesAcceso modulo) {
		return this.daoModulosGuiHasRolesAcceso.create(modulo);
	}

	@Override
	public void create(RolAcceso rol) {
		try {
			Set<SeccionHasRolAcceso> secciones = new HashSet<>();
			Set<ModulosGui> modulos = this.serviceModulosGui.findAll();
			Set<ModulosGuiHasRolesAcceso> rolHasModulos = new HashSet<>();
			for (Iterator<ModulosGui> iterator = modulos.iterator(); iterator.hasNext();) {
				ModulosGui modulosGui = iterator.next();
				if (!modulosGui.getUrl().equals("organizaciones?opt=4")
						&& !modulosGui.getUrl().equals("dashboard?opt=1")
						&& !modulosGui.getUrl().equals("dashboard?opt=2")
						&& !modulosGui.getUrl().equals("dashboard?opt=3")) {
					ModulosGuiHasRolesAcceso hasRol = new ModulosGuiHasRolesAcceso();
					hasRol.setIdModulos(modulosGui.getId());
					hasRol.setIdRolesAcceso(rol.getId());
					hasRol.setModulosGui(modulosGui);
					hasRol.setRolesAcceso(rol);
					rolHasModulos.add(hasRol);
					for (Iterator<Seccion> lstSeccion = modulosGui.getSeccion().iterator(); lstSeccion.hasNext();) {
						Seccion seccion = lstSeccion.next();
						secciones.add(new SeccionHasRolAcceso(seccion.getId(), rol.getId()));
					}
				}
			}
			if (!rolHasModulos.isEmpty() && !secciones.isEmpty())
				this.daoModulosGuiHasRolesAcceso.create(rolHasModulos, secciones);
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}

	}

	@Override
	public boolean create(SeccionRolDTO seccionRolDTO) {
		try {
			RolAcceso rolAcceso = this.serviceRolAcceso.findByRol(seccionRolDTO.getRol());
			if (rolAcceso == null)
				return false;
			ModulosGui modulosGui = this.serviceModulosGui.findModuloGuiByName(seccionRolDTO.getModulo());
			if (modulosGui == null)
				return false;
			ModulosGuiHasRolesAcceso hasRol = new ModulosGuiHasRolesAcceso();
			hasRol.setIdModulos(modulosGui.getId());
			hasRol.setIdRolesAcceso(rolAcceso.getId());
			if (this.daoModulosGuiHasRolesAcceso.create(hasRol)) {
				return this.serviceSeccionHasRolAcceso.addAll(seccionRolDTO);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return false;
	}

	@Override
	public boolean remove(SeccionRolDTO seccionRolDTO) {
		try {
			RolAcceso rolAcceso = this.serviceRolAcceso.findByRol(seccionRolDTO.getRol());
			if (rolAcceso == null)
				return false;
			ModulosGui modulosGui = this.serviceModulosGui.findModuloGuiByName(seccionRolDTO.getModulo());
			if (modulosGui == null)
				return false;
			ModulosGuiHasRolesAcceso hasRol = new ModulosGuiHasRolesAcceso();
			hasRol.setIdModulos(modulosGui.getId());
			hasRol.setIdRolesAcceso(rolAcceso.getId());
			if (this.remove(hasRol)) {
				return this.daoModulosGuiHasRolesAcceso.remove(hasRol);
			}
		} catch (Exception e) {
			SytecsoController.logClassAndMethodWithException(e);
		}
		return false;
	}

}
