/**
 * 
 * Created-By: Sytecso
 * Date:       02/01/2018
 */
package com.sytecso.beans;

import java.io.Serializable;

public class UsuarioCuenta implements Serializable{

	private static final long serialVersionUID = -5580218045892855146L;
	private int idUsuarioCuenta;
    private String login;
    private String password;
    private String nivelCuenta;
    private int status;
    private boolean cmbpwd;

    public UsuarioCuenta() {
    }

	public int getIdUsuarioCuenta() {
		return idUsuarioCuenta;
	}

	public void setIdUsuarioCuenta(int idUsuarioCuenta) {
		this.idUsuarioCuenta = idUsuarioCuenta;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNivelCuenta() {
		return nivelCuenta;
	}

	public void setNivelCuenta(String nivelCuenta) {
		this.nivelCuenta = nivelCuenta;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public boolean isCmbpwd() {
		return cmbpwd;
	}

	public void setCmbpwd(boolean cmbpwd) {
		this.cmbpwd = cmbpwd;
	}

}
