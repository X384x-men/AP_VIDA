package com.sytecso.dto.batchmodel;

import java.io.Serializable;
import java.util.List;

public class DTOReproceso implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Long> idFile;
	private boolean status;
	private String message;
	private String fileName;
	private boolean error;
	private String errorMsg;
	

	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public List<Long> getIdFile() {
		return idFile;
	}
	public void setIdFile(List<Long> idFile) {
		this.idFile = idFile;
	}
	
	

}
