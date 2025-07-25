package com.sytecso.component;

import java.io.Serializable;

public class DTOFile implements Serializable{

	private static final long serialVersionUID = 6169333846818481246L;
	
	public DTOFile() {
		
	}
	public DTOFile(Integer fila, String errorMessage) {
		super();
		this.fila = fila;
		this.errorMessage = errorMessage;
	}
	
	private Integer fila;
	private String errorMessage;
	private String sucessMessage;
	
	public DTOFile( String sucessMessage) {
		super();
		this.sucessMessage = sucessMessage;
		
	}
	
	public Integer getFila() {
		return fila;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setFila(Integer fila) {
		this.fila = fila;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSucessMessage() {
		return sucessMessage;
	}
	public void setSucessMessage(String sucessMessage) {
		this.sucessMessage = sucessMessage;
	}
	
	
}
