package com.sytecso.component;

public class EventMessage {
	public EventMessage() {
		
	}
	public EventMessage(String message) {
		super();
		this.message = message;
	}

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
