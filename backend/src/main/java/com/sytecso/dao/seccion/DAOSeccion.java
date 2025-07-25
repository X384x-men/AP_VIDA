package com.sytecso.dao.seccion;

import java.util.List;
import java.util.Set;

import com.sytecso.dto.modulosgui.ModuloDTO;
import com.sytecso.dto.seccion.SeccionRolDTO;
import com.sytecso.model.Seccion;

public interface DAOSeccion {
	public List<Seccion> getAllByIdModulosGui(Long id);

	public List<ModuloDTO> getSeccionNotPresentByRolAndModulo(SeccionRolDTO seccionRolDTO);

	public List<ModuloDTO> getSeccionPresentByRol(SeccionRolDTO seccionRolDTO);

	public Seccion findSeccionByName(String seccion);

	public Set<Seccion> findIdSeccionesByNombre(Set<Seccion> secciones);

	public Set<Seccion> create(Set<Seccion> secciones);

}
