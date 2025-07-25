package com.sytecso.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class DetalleMovimientoVectorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4220000918075848617L;
	
	private String concepto;
	private String fechaMov;
	private BigDecimal impDeposito;
	private BigDecimal impIntereses;
	private BigDecimal impRetencion;
	private BigDecimal impSaldo;
	
	
	
	public String getConcepto() {
		return concepto;
	}
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	public String getFechaMov() {
		return fechaMov;
	}
	public void setFechaMov(String fechaMov) {
		this.fechaMov = fechaMov;
	}
	public BigDecimal getImpDeposito() {
		return impDeposito;
	}
	public void setImpDeposito(BigDecimal impDeposito) {
		this.impDeposito = impDeposito;
	}
	public BigDecimal getImpIntereses() {
		return impIntereses;
	}
	public void setImpIntereses(BigDecimal impIntereses) {
		this.impIntereses = impIntereses;
	}
	public BigDecimal getImpRetencion() {
		return impRetencion;
	}
	public void setImpRetencion(BigDecimal impRetencion) {
		this.impRetencion = impRetencion;
	}
	public BigDecimal getImpSaldo() {
		return impSaldo;
	}
	public void setImpSaldo(BigDecimal impSaldo) {
		this.impSaldo = impSaldo;
	}
	
	
	

}
