package com.sytecso.dto;

import java.io.Serializable;

public class PeriodoConsultaVectorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8016094521045960675L;
	
	private String anio;
	private String mes;
	private String  periodo;
	
	
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
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	

}
