package com.sytecso.dto.errors;



public class ErrorDetails {
	private String timestamp;
	private String meesage;
	private String details;

	public ErrorDetails(String timestamp, String meesage, String details) {
		super();
		this.timestamp = timestamp;
		this.meesage = meesage;
		this.details = details;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public String getMeesage() {
		return meesage;
	}
	public String getDetails() {
		return details;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public void setMeesage(String meesage) {
		this.meesage = meesage;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
}
