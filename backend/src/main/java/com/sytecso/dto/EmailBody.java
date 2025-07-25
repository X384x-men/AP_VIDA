package com.sytecso.dto;

import java.io.Serializable;

public class EmailBody implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4856421148523415653L;
	private String email;
	private String rfc;
	private String content;
	private String subject;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	
	@Override
	public String toString() {
		return "EmailBody [email=" + email + ", RFC=" + rfc + " ]";
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
}
