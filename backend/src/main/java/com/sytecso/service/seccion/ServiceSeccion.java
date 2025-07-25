package com.sytecso.service.seccion;

import java.util.List;
import java.util.Set;

import com.sytecso.dto.modulosgui.ModuloDTO;
import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.component.exceptions.MenuException.SeccionNotExistsException;
import com.sytecso.model.Seccion;

public interface ServiceSeccion {
	public List<Seccion> getAllByIdModulosGui(Long id);

	public List<ModuloDTO> getSeccionNotPresentByRolAndModulo(SeccionRolDTO seccionRolDTO);

	public Set<Seccion> findOrCreateSeccionByNombre(Set<Seccion> secciones);

	public Set<Seccion> findIdSeccionesByNombre(Set<Seccion> secciones);

	public Set<Seccion> create(Set<Seccion> secciones);

	public List<ModuloDTO> getSeccionPresentByRol(SeccionRolDTO seccionRolDTO);

	public List<ModuloDTO> getSeccionByRol(SeccionRolDTO seccionRolDTO, int value);

	public Seccion findSeccionByName(String seccion) throws SeccionNotExistsException;

	public void removeSeccioFromRol(SeccionRolDTO seccionRolDTO);
}
