package com.sytecso.service.catalogosAP;

import java.util.List;

import com.sytecso.dto.catalogosAP.DTOCatalogoConceptos;
import com.sytecso.dto.catalogosAP.DTOCatalogoDependencias;
import com.sytecso.dto.catalogosAP.DTOCatalogoUnidadAdministrativa;


public interface ServiceCatalogosAP {
	
	public List<DTOCatalogoDependencias> getDependencias();
	public List<DTOCatalogoUnidadAdministrativa> getUnidadesAdministrativas();
	public List<DTOCatalogoConceptos> getConceptos();
	public DTOCatalogoDependencias getDependenciaUser(String rfc);
	public boolean updateCatalogoDependenciasDescripcion(int indice, String descripcion);
	public boolean updateCatalogoUnidadesAdministrativasDescripcion(int indice, String descripcion);
	public boolean updateCatalogoConceptosDescripcion(int indice, String descripcion);
	public boolean updateCatalogoDependenciasStatus(int indice, int status);
	public boolean updateCatalogoUnidadesAdministrativasStatus(int indice,  int status);
	public boolean updateCatalogoConceptosStatus(int indice,  int status);
	public boolean insertCatalogoDependenciasStatus(String descripcion);
	public boolean insertCatalogoUnidadesAdministrativasStatus(String descripcion);
	public boolean insertCatalogoConceptosStatus(String descripcion);

}
