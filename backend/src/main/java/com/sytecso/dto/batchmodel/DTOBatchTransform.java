package com.sytecso.dto.batchmodel;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class DTOBatchTransform implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private List<DTOResumen> batchResumen;
	private List<DTODetalle> batchDetalle;
	private List<DTOResumen> batchResumenSinRFC;
	private List<DTODetalle> batchDetalleSinRFC;
	private boolean status;
	private boolean tipo;
	private Set<String> rfcLista;
	private Set<String> rfcListaInvalidos;
	private Set<DTOAsegurado> asegurados;
	
	
	
	
	public Set<DTOAsegurado> getAsegurados() {
		return asegurados;
	}
	public void setAsegurados(Set<DTOAsegurado> asegurados) {
		this.asegurados = asegurados;
	}
	public Set<String> getRfcListaInvalidos() {
		return rfcListaInvalidos;
	}
	public void setRfcListaInvalidos(Set<String> rfcListaInvalidos) {
		this.rfcListaInvalidos = rfcListaInvalidos;
	}
	public Set<String> getRfcLista() {
		return rfcLista;
	}
	public void setRfcLista(Set<String> rfcLista) {
		this.rfcLista = rfcLista;
	}
	public List<DTOResumen> getBatchResumen() {
		return batchResumen;
	}
	public void setBatchResumen(List<DTOResumen> batchResumen) {
		this.batchResumen = batchResumen;
	}
	public List<DTODetalle> getBatchDetalle() {
		return batchDetalle;
	}
	public void setBatchDetalle(List<DTODetalle> batchDetalle) {
		this.batchDetalle = batchDetalle;
	}
	public boolean isTipo() {
		return tipo;
	}
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
	public List<DTOResumen> getBatchResumenSinRFC() {
		return batchResumenSinRFC;
	}
	public void setBatchResumenSinRFC(List<DTOResumen> batchResumenSinRFC) {
		this.batchResumenSinRFC = batchResumenSinRFC;
	}
	public List<DTODetalle> getBatchDetalleSinRFC() {
		return batchDetalleSinRFC;
	}
	public void setBatchDetalleSinRFC(List<DTODetalle> batchDetalleSinRFC) {
		this.batchDetalleSinRFC = batchDetalleSinRFC;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	

}
