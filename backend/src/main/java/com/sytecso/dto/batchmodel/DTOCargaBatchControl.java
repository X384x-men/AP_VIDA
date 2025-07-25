package com.sytecso.dto.batchmodel;

import java.io.Serializable;

public class DTOCargaBatchControl  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombreArchivo;
	private int totalRegistros;
	private int registrosRechazados;
	private int regristrosValidos;
	private String fechaCarga;
	private String tipo;
	private long id;
	private DTOBatchTransform batchInfo;
	private boolean processStatus;
	private String mensaje;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public int getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	public int getRegistrosRechazados() {
		return registrosRechazados;
	}
	public void setRegistrosRechazados(int registrosRechazados) {
		this.registrosRechazados = registrosRechazados;
	}
	public int getRegristrosValidos() {
		return regristrosValidos;
	}
	public void setRegristrosValidos(int regristrosValidos) {
		this.regristrosValidos = regristrosValidos;
	}
	public String getFechaCarga() {
		return fechaCarga;
	}
	public void setFechaCarga(String fechaCarga) {
		this.fechaCarga = fechaCarga;
	}
	public DTOBatchTransform getBatchInfo() {
		return batchInfo;
	}
	public void setBatchInfo(DTOBatchTransform batchInfo) {
		this.batchInfo = batchInfo;
	}
	public boolean isProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(boolean processStatus) {
		this.processStatus = processStatus;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	
}
