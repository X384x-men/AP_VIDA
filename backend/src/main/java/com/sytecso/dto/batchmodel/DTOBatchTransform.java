package com.sytecso.dto.batchmodel;

import java.io.Serializable;
import java.util.List;

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
