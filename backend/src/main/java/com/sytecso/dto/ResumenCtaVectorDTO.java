package com.sytecso.dto;

import java.io.Serializable;
import java.util.List;



public class ResumenCtaVectorDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4856421148523415653L;
	
	private String numCuenta;
	private String periodoConsulta;
	private String anioConsulta;
	private String mesConsulta;
	private String codigoRFC;
	private String numPoliza;
	private String retenedor;
	private String dependencia;
	private String tasaPeriodo;
	private String totalDetalleMovimiento;
	private List<ResumenMovimientoVectorDTO> listResumenMovimiento;	
	private List<DetalleMovimientoVectorDTO> listDetalleMovimiento;	
	private List<PeriodoConsultaVectorDTO> listPeriodosConsulta;
	private String bodyHTML;
	
	
	public List<ResumenMovimientoVectorDTO> getListResumenMovimiento() {
		return listResumenMovimiento;
	}
	public void setListResumenMovimiento(List<ResumenMovimientoVectorDTO> listResumenMovimiento) {
		this.listResumenMovimiento = listResumenMovimiento;
	}
	public List<DetalleMovimientoVectorDTO> getListDetalleMovimiento() {
		return listDetalleMovimiento;
	}
	public void setListDetalleMovimiento(List<DetalleMovimientoVectorDTO> listDetalleMovimiento) {
		this.listDetalleMovimiento = listDetalleMovimiento;
	}
	public List<PeriodoConsultaVectorDTO> getListPeriodosConsulta() {
		return listPeriodosConsulta;
	}
	public void setListPeriodosConsulta(List<PeriodoConsultaVectorDTO> listPeriodosConsulta) {
		this.listPeriodosConsulta = listPeriodosConsulta;
	}
	public String getNumCuenta() {
		return numCuenta;
	}
	public void setNumCuenta(String numCuenta) {
		this.numCuenta = numCuenta;
	}
	public String getPeriodoConsulta() {
		return periodoConsulta;
	}
	public void setPeriodoConsulta(String periodoConsulta) {
		this.periodoConsulta = periodoConsulta;
	}
	public String getAnioConsulta() {
		return anioConsulta;
	}
	public void setAnioConsulta(String anioConsulta) {
		this.anioConsulta = anioConsulta;
	}
	public String getMesConsulta() {
		return mesConsulta;
	}
	public void setMesConsulta(String mesConsulta) {
		this.mesConsulta = mesConsulta;
	}
	public String getCodigoRFC() {
		return codigoRFC;
	}
	public void setCodigoRFC(String codigoRFC) {
		this.codigoRFC = codigoRFC;
	}
	public String getNumPoliza() {
		return numPoliza;
	}
	public void setNumPoliza(String numPoliza) {
		this.numPoliza = numPoliza;
	}
	public String getRetenedor() {
		return retenedor;
	}
	public void setRetenedor(String retenedor) {
		this.retenedor = retenedor;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	public String getTasaPeriodo() {
		return tasaPeriodo;
	}
	public void setTasaPeriodo(String tasaPeriodo) {
		this.tasaPeriodo = tasaPeriodo;
	}
	public String getTotalDetalleMovimiento() {
		return totalDetalleMovimiento;
	}
	public void setTotalDetalleMovimiento(String totalDetalleMovimiento) {
		this.totalDetalleMovimiento = totalDetalleMovimiento;
	}
	
	
	public String getBodyHTML() {
		return bodyHTML;
	}
	public void setBodyHTML(String bodyHTML) {
		this.bodyHTML = bodyHTML;
	}
	
	
	
	

	
	
	


}
