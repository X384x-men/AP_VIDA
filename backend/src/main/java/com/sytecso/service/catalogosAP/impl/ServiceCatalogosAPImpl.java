package com.sytecso.service.catalogosAP.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sytecso.dao.catalogosAP.DAOCatalogosAP;
import com.sytecso.dto.catalogosAP.DTOCatalogoConceptos;
import com.sytecso.dto.catalogosAP.DTOCatalogoDependencias;
import com.sytecso.dto.catalogosAP.DTOCatalogoUnidadAdministrativa;
import com.sytecso.service.catalogosAP.ServiceCatalogosAP;

@Service
public class ServiceCatalogosAPImpl implements ServiceCatalogosAP {
	
	@Autowired
	private DAOCatalogosAP daoCatalogosAP;

	@Override
	public List<DTOCatalogoDependencias> getDependencias() {
		return daoCatalogosAP.getDependencias();
	}

	@Override
	public List<DTOCatalogoUnidadAdministrativa> getUnidadesAdministrativas() {
		return daoCatalogosAP.getUnidadesAdministrativas();
	}

	@Override
	public List<DTOCatalogoConceptos> getConceptos() {
		return daoCatalogosAP.getConceptos();
	}

	@Override
	public DTOCatalogoDependencias getDependenciaUser(String rfc) {
		return daoCatalogosAP.getDependencyUser(rfc);
	}

	@Override
	public boolean updateCatalogoDependenciasDescripcion(int indice, String descripcion) {
		return daoCatalogosAP.updateCatalogoDependenciasString(indice, descripcion);
	}

	@Override
	public boolean updateCatalogoUnidadesAdministrativasDescripcion(int indice, String descripcion) {
		// TODO Auto-generated method stub
		return daoCatalogosAP.updateCatalogoUnidadesString(indice, descripcion);
	}

	@Override
	public boolean updateCatalogoConceptosDescripcion(int indice, String descripcion) {
		// TODO Auto-generated method stub
		return daoCatalogosAP.updateCatalogoConceptosString(indice, descripcion);
	}

	@Override
	public boolean updateCatalogoDependenciasStatus(int indice, int status) {
		// TODO Auto-generated method stub
		return daoCatalogosAP.updateCatalogoDependenciasStatus(indice, status);
	}

	@Override
	public boolean updateCatalogoUnidadesAdministrativasStatus(int indice, int status) {
		// TODO Auto-generated method stub
		return daoCatalogosAP.updateCatalogoUnidadesStatus(indice, status);
	}

	@Override
	public boolean updateCatalogoConceptosStatus(int indice, int status) {
		// TODO Auto-generated method stub
		return daoCatalogosAP.updateCatalogoConceptosStatus(indice, status);
	}

	@Override
	public boolean insertCatalogoDependenciasStatus(String descripcion) {
		// TODO Auto-generated method stub
		return daoCatalogosAP.insertCatalogoDependencia(descripcion);
	}

	@Override
	public boolean insertCatalogoUnidadesAdministrativasStatus(String descripcion) {
		// TODO Auto-generated method stub
		return daoCatalogosAP.inserCatalogoUnidadAdministratuva(descripcion);
	}

	@Override
	public boolean insertCatalogoConceptosStatus(String descripcion) {
		// TODO Auto-generated method stub
		return daoCatalogosAP.inserCatalogoCatalogoConceptos(descripcion);
	}
	


}
