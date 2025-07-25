package com.sytecso.component.exceptions;

public class DireccionClienteException extends Exception {
	private static final long serialVersionUID = -1112133092272998547L;

	public static class NotDireccionClientesFoundException extends BaseException {

		private static final long serialVersionUID = -9120066849179063331L;

		public NotDireccionClientesFoundException(String msg) {
			super(msg);
		}

	}
}
