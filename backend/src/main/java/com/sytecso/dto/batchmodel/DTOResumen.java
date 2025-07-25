package com.sytecso.dto.batchmodel;

import java.io.Serializable;

public class DTOResumen implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private DTOCargaBatchControl cargaBatch;
	private DTOCriterios criterios;
	private float saldoInicial;
	private float primasAportadas;
	private float interesesGanados;
	private float retiros;
	private float saldoFinal;
	private String dependencia;
	public float getPrimasAportadas() {
		return primasAportadas;
	}
	public void setPrimasAportadas(float primasAportadas) {
		this.primasAportadas = primasAportadas;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public DTOCargaBatchControl getCargaBatch() {
		return cargaBatch;
	}
	public void setCargaBatch(DTOCargaBatchControl cargaBatch) {
		this.cargaBatch = cargaBatch;
	}
	public DTOCriterios getCriterios() {
		return criterios;
	}
	public void setCriterios(DTOCriterios criterios) {
		this.criterios = criterios;
	}
	public float getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(float saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public float getInteresesGanados() {
		return interesesGanados;
	}
	public void setInteresesGanados(float interesesGanados) {
		this.interesesGanados = interesesGanados;
	}
	public float getRetiros() {
		return retiros;
	}
	public void setRetiros(float retiros) {
		this.retiros = retiros;
	}
	public float getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(float saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
}
