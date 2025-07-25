package com.sytecso.component.exceptions;

public class CuentaClienteException extends Exception {
	private static final long serialVersionUID = -1112133092272998547L;

	public static class NotClientesFoundException extends BaseException {

		private static final long serialVersionUID = -9120066849179063331L;

		public NotClientesFoundException(String msg) {
			super(msg);
		}

	}
}
