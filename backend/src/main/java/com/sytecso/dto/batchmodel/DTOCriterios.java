package com.sytecso.dto.batchmodel;

import java.io.Serializable;

import com.sytecso.dto.catalogosAP.DTOCatalogoConceptos;
import com.sytecso.dto.catalogosAP.DTOCatalogoDependencias;

public class DTOCriterios implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String anio;
	private String mes;
	private DTOAsegurado asegurado;
	private String fecha;
	private DTOCatalogoDependencias catalogoDependencias;
	private String homoninimia;
	private String nombre;
	private int lineas;
	private DTOCatalogoConceptos concepto;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getHomoninima() {
		return homoninimia;
	}
	public void setHomoninimia(String homoninimia) {
		this.homoninimia = homoninimia;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public DTOCatalogoDependencias getCatalogoDependencias() {
		return catalogoDependencias;
	}
	public void setCatalogoDependencias(DTOCatalogoDependencias catalogoDependencias) {
		this.catalogoDependencias = catalogoDependencias;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getMes() {
		return mes;
	}
	public void setMes(String mes) {
		this.mes = mes;
	}
	public DTOAsegurado getAsegurado() {
		return asegurado;
	}
	public void setAsegurado(DTOAsegurado asegurado) {
		this.asegurado = asegurado;
	}
	public int getLineas() {
		return lineas;
	}
	public void setLineas(int lineas) {
		this.lineas = lineas;
	}
	public DTOCatalogoConceptos getConcepto() {
		return concepto;
	}
	public void setConcepto(DTOCatalogoConceptos concepto) {
		this.concepto = concepto;
	}
}
