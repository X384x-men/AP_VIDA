package com.sytecso.dto.batchmodel;

import java.io.Serializable;
import java.util.List;

public class DTOPDF implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DTOAsegurado asegurado;
	private List<DTOCargo> cargos;
	public DTOAsegurado getAsegurado() {
		return asegurado;
	}
	public void setAsegurado(DTOAsegurado asegurado) {
		this.asegurado = asegurado;
	}
	public List<DTOCargo> getCargos() {
		return cargos;
	}
	public void setCargos(List<DTOCargo> cargos) {
		this.cargos = cargos;
	}
	
	
	
}
