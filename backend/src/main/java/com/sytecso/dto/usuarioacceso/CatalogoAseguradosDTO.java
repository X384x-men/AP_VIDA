package com.sytecso.dto.usuarioacceso;

import java.io.Serializable;

public class CatalogoAseguradosDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1137304902726892213L;
	
	private long idcat_Asegurados;
	private String rfc;
	private String curp;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombre;
	
	
	public long getIdcat_Asegurados() {
		return idcat_Asegurados;
	}
	public void setIdcat_Asegurados(long idcat_Asegurados) {
		this.idcat_Asegurados = idcat_Asegurados;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
