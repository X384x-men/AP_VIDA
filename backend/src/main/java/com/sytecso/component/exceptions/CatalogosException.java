package com.sytecso.component.exceptions;

public class CatalogosException extends Exception {

	private static final long serialVersionUID = 824884027426101630L;

	public static class NotCatalogosRegistrerException extends BaseException {
		private static final long serialVersionUID = 566963562658376462L;

		public NotCatalogosRegistrerException(String msg) {
			super(msg);
		}
	}

	public static class NotCatalogoFound extends BaseException {

		private static final long serialVersionUID = 1243323803482531401L;

		public NotCatalogoFound(String msg) {
			super(msg);
		}

	}
	

	
}
