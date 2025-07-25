package com.sytecso.dto.usuario;

import java.io.Serializable;

public class UserAp  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rfc;
	private int status;
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	

}
