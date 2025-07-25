package com.sytecso.dto;

import java.io.Serializable;

public class VectorDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8011210911013932586L;
	private String responseCd;
	private String responseMsg;
	private ResumenCtaVectorDTO dsResumen;
	
	
	
	public String getResponseCd() {
		return responseCd;
	}
	public void setResponseCd(String responseCd) {
		this.responseCd = responseCd;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public ResumenCtaVectorDTO getDsResumen() {
		return dsResumen;
	}
	public void setDsResumen(ResumenCtaVectorDTO dsResumen) {
		this.dsResumen = dsResumen;
	}
	
	
	
}
