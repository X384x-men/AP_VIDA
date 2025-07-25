package com.sytecso.component.exceptions;

public class OrganizacionException extends Exception {
	
	private static final long serialVersionUID = -5291396635633231272L;


	public static class OrganizationEmptyException extends BaseException {
		private static final long serialVersionUID = 5922828761928821187L;

		public OrganizationEmptyException(String msg) {
			super(msg);
		}

	}

	public static class OrganizacionExistsException extends BaseException {
		private static final long serialVersionUID = 2013387227020062892L;

		public OrganizacionExistsException(String msg) {
			super(msg);
		}

	}
	
	public static class OrganizacionCreateException extends BaseException {

		private static final long serialVersionUID = -5480533650771959557L;

		public OrganizacionCreateException(String msg) {
			super(msg);
		}
	}
	
}
