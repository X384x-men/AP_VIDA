package com.sytecso.component.exceptions;

public class EvidenciaOrdenException extends Exception {

	private static final long serialVersionUID = -5240830545499371685L;

	public static class EvidenciaNotFoundException extends BaseException {

		private static final long serialVersionUID = 1504896519368597929L;

		public EvidenciaNotFoundException(String msg) {
			super(msg);
		}
	}

}
