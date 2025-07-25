package com.sytecso.component.exceptions;

public class ProyectoException extends Exception {
	
	private static final long serialVersionUID = -5291396635633231272L;


	public static class ProyectoEmptyException extends BaseException {
		private static final long serialVersionUID = 5922828761928821187L;

		public ProyectoEmptyException(String msg) {
			super(msg);
		}

	}

	public static class ProyectoExistsException extends BaseException {
		private static final long serialVersionUID = 2013387227020062892L;

		public ProyectoExistsException(String msg) {
			super(msg);
		}

	}
	
	public static class ProyectoCreateException extends BaseException {

		private static final long serialVersionUID = -5480533650771959557L;

		public ProyectoCreateException(String msg) {
			super(msg);
		}
	}
	
	public static class ProyectoUpdateException extends BaseException {

		private static final long serialVersionUID = -5480533650771959557L;

		public ProyectoUpdateException(String msg) {
			super(msg);
		}
	}
}
