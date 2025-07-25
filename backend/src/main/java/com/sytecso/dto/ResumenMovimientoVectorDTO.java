package com.sytecso.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResumenMovimientoVectorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 339121172413218676L;
	private String descripcion;
	private BigDecimal saldoInicial;
	private BigDecimal primasAportadas;
	private BigDecimal interesGanado;
	private BigDecimal retiros;
	private BigDecimal saldoFinal;
	
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public BigDecimal getPrimasAportadas() {
		return primasAportadas;
	}
	public void setPrimasAportadas(BigDecimal primasAportadas) {
		this.primasAportadas = primasAportadas;
	}
	public BigDecimal getInteresGanado() {
		return interesGanado;
	}
	public void setInteresGanado(BigDecimal interesGanado) {
		this.interesGanado = interesGanado;
	}
	public BigDecimal getRetiros() {
		return retiros;
	}
	public void setRetiros(BigDecimal retiros) {
		this.retiros = retiros;
	}
	public BigDecimal getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(BigDecimal saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	
	
	

}
