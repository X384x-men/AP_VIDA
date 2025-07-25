package com.sytecso.dto.empleado;

import java.io.Serializable;

public class EmpleadoAPDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4856421148523415653L;
	private long idEmpleado;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String calle;
	private String colonia; 
	private int noInt;
	private int noExt;
	private int cp;
	private String rfc;
	private String curp;
	private String sexo;
	private long telCasa;
	private long telMovil; 
	private String mail;
	private String noEmpleado; 
	private long cuenta;
	private String fechaNacimiento;
	private String dependencia;
	private String unidadAdministrativa;
	private String fechaIngresoSeguro;
	private String banco;
	private String psw;
	private String fechaCreacion;
	private long idUsuarioAcceso;
	private String estado;
	private String tipoCuenta;
	private String pswValidate;
	
	private int estatus;
	private String fechaCambioEstatus;
	
	private int tipoAnalista;
	public int getIdUnidad() {
		return idUnidad;
	}
	public void setIdUnidad(int idUnidad) {
		this.idUnidad = idUnidad;
	}
	public int getIdidDependencia() {
		return ididDependencia;
	}
	public void setIdidDependencia(int ididDependencia) {
		this.ididDependencia = ididDependencia;
	}
	public String getDependenciaCatalogo() {
		return dependenciaCatalogo;
	}
	public void setDependenciaCatalogo(String dependenciaCatalogo) {
		this.dependenciaCatalogo = dependenciaCatalogo;
	}
	public String getUnidadCatalogo() {
		return unidadCatalogo;
	}
	public void setUnidadCatalogo(String unidadCatalogo) {
		this.unidadCatalogo = unidadCatalogo;
	}
	private int idUnidad;
	private int ididDependencia;
	private String dependenciaCatalogo;
	private String unidadCatalogo;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public int getNoInt() {
		return noInt;
	}
	public void setNoInt(int noInt) {
		this.noInt = noInt;
	}
	public int getNoExt() {
		return noExt;
	}
	public void setNoExt(int noExt) {
		this.noExt = noExt;
	}
	public int getCp() {
		return cp;
	}
	public void setCp(int cp) {
		this.cp = cp;
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getNoEmpleado() {
		return noEmpleado;
	}
	public void setNoEmpleado(String noEmpleado) {
		this.noEmpleado = noEmpleado;
	}
	
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getDependencia() {
		return dependencia;
	}
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	public String getFechaIngresoSeguro() {
		return fechaIngresoSeguro;
	}
	public void setFechaIngresoSeguro(String fechaIngresoSeguro) {
		this.fechaIngresoSeguro = fechaIngresoSeguro;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public long getIdUsuarioAcceso() {
		return idUsuarioAcceso;
	}
	public void setIdUsuarioAcceso(long idUsuarioAcceso) {
		this.idUsuarioAcceso = idUsuarioAcceso;
	}
	public long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public long getCuenta() {
		return cuenta;
	}
	public void setCuenta(long cuenta) {
		this.cuenta = cuenta;
	}
	public long getTelCasa() {
		return telCasa;
	}
	public void setTelCasa(long telCasa) {
		this.telCasa = telCasa;
	}
	public long getTelMovil() {
		return telMovil;
	}
	public void setTelMovil(long telMovil) {
		this.telMovil = telMovil;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	public String getPswValidate() {
		return pswValidate;
	}
	public void setPswValidate(String pswValidate) {
		this.pswValidate = pswValidate;
	}
	public int getEstatus() {
		return estatus;
	}
	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}
	public String getFechaCambioEstatus() {
		return fechaCambioEstatus;
	}
	public void setFechaCambioEstatus(String fechaCambioEstatus) {
		this.fechaCambioEstatus = fechaCambioEstatus;
	}
	public int getTipoAnalista() {
		return tipoAnalista;
	}
	public void setTipoAnalista(int tipoAnalista) {
		this.tipoAnalista = tipoAnalista;
	}
	
}
