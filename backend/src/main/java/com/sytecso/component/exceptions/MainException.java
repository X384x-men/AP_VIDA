package com.sytecso.component.exceptions;

public class MainException extends Exception {
	private static final long serialVersionUID = -3504146684143342737L;

	public static class GeneralException extends BaseException {
		private static final long serialVersionUID = 8763046354705164755L;

		public GeneralException() {
			super("");
		}

		public GeneralException(String msg) {
			super(msg);
		}
	}

	public static class DataAsociateExistsException extends BaseException {
		private static final long serialVersionUID = 980878552770645908L;

		public DataAsociateExistsException(String msg) {
			super(msg);
		}

	}
}
