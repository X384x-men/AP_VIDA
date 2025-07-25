package com.sytecso.component.exceptions;

public class AlmacenesException extends Exception {
	
	private static final long serialVersionUID = -5291396635633231272L;


	public static class AlmacenEmptyException extends BaseException {
		private static final long serialVersionUID = 5922828761928821187L;

		public AlmacenEmptyException(String msg) {
			super(msg);
		}

	}

	
	
}
