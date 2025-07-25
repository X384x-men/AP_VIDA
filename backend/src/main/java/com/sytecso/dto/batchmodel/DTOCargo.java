package com.sytecso.dto.batchmodel;

import java.io.Serializable;
import java.util.List;

public class DTOCargo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private DTOResumen resumen;
	private List<DTOResumen> resumenLista;
	private List<DTODetalle> detalles;
	private DTOCriterios criterio;
	private String retenedor;
	private String poliza;
	private String mensaje;
	public String getRetenedor() {
		return retenedor;
	}
	public void setRetenedor(String retenedor) {
		this.retenedor = retenedor;
	}
	public String getPoliza() {
		return poliza;
	}
	public void setPoliza(String poliza) {
		this.poliza = poliza;
	}
	public DTOCriterios getCriterio() {
		return criterio;
	}
	public void setCriterio(DTOCriterios criterio) {
		this.criterio = criterio;
	}
	public DTOResumen getResumen() {
		return resumen;
	}
	public void setResumen(DTOResumen resumen) {
		this.resumen = resumen;
	}
	public List<DTODetalle> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DTODetalle> detalles) {
		this.detalles = detalles;
	}
	public List<DTOResumen> getResumenLista() {
		return resumenLista;
	}
	public void setResumenLista(List<DTOResumen> resumenLista) {
		this.resumenLista = resumenLista;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
}
