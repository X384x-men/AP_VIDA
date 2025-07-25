package com.sytecso.dao.catalogosAP;

import java.util.List;

import com.sytecso.dto.catalogosAP.DTOCatalogoConceptos;
import com.sytecso.dto.catalogosAP.DTOCatalogoDependencias;
import com.sytecso.dto.catalogosAP.DTOCatalogoUnidadAdministrativa;

public interface DAOCatalogosAP {
	
	public List<DTOCatalogoDependencias> getDependencias();
	public List<DTOCatalogoUnidadAdministrativa> getUnidadesAdministrativas();
	public List<DTOCatalogoConceptos> getConceptos();
	public DTOCatalogoDependencias getDependencyUser(String rfc);
	public boolean updateCatalogoDependenciasString(int indice, String descripcion);
	public boolean updateCatalogoUnidadesString(int indice, String descripcion);
	public boolean updateCatalogoConceptosString(int indice, String descripcion);
	public boolean updateCatalogoDependenciasStatus(int indice,int status) ;
	public boolean updateCatalogoUnidadesStatus(int indice,int status);
	public boolean updateCatalogoConceptosStatus(int indice,int status);
	public boolean insertCatalogoDependencia(String descripcion);
	public boolean inserCatalogoUnidadAdministratuva(String descripcion);
	public boolean inserCatalogoCatalogoConceptos(String descripcion);
		
}
