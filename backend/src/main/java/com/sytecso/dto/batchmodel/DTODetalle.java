package com.sytecso.dto.batchmodel;

import java.io.Serializable;

public class DTODetalle implements Serializable  {
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;
	private DTOCargaBatchControl cargaBatch;
	private DTOCriterios criterio;
	private float deposito;
	private float intereses;
	private float retiros;
	private float saldo;
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
	public DTOCriterios getCriterio() {
		return criterio;
	}
	public void setCriterio(DTOCriterios criterio) {
		this.criterio = criterio;
	}

	public float getIntereses() {
		return intereses;
	}
	public void setIntereses(float intereses) {
		this.intereses = intereses;
	}
	public float getRetiros() {
		return retiros;
	}
	public void setRetiros(float retiros) {
		this.retiros = retiros;
	}
	public float getSaldo() {
		return saldo;
	}
	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	public void setDeposito(float deposito) {
		this.deposito = deposito;
	}
	public float getDeposito() {
		return deposito;
	}
}
