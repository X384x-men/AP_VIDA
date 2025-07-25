package com.sytecso.component.exceptions;

public class CuadrillasException extends Exception {

	private static final long serialVersionUID = 824884027426101630L;

	public static class NotCuadrillaRegistrerForOrdenException extends BaseException {
		private static final long serialVersionUID = 1504678510850934313L;

		public NotCuadrillaRegistrerForOrdenException(String msg) {
			super(msg);
		}
	}

	public static class NotCuadrillaFoundForZone extends BaseException {
		private static final long serialVersionUID = -7640812517877097048L;

		public NotCuadrillaFoundForZone(String msg) {
			super(msg);
		}

	}
	public static class NotCuadrillaFound extends BaseException {

		private static final long serialVersionUID = -6354801069143100298L;

		public NotCuadrillaFound(String msg) {
			super(msg);
		}

	}

	public static class FueraRangoException extends BaseException {
		private static final long serialVersionUID = 4398081313903494256L;

		public FueraRangoException(String msg) {
			super(msg);
		}

	}

	public static class NotTipoOrdenesFoundException extends BaseException {

		private static final long serialVersionUID = 552436901754928246L;

		public NotTipoOrdenesFoundException(String msg) {
			super(msg);
		}

	}

	public static class NotUserFoundException extends BaseException {

		private static final long serialVersionUID = -1519174540074690196L;

		public NotUserFoundException(String msg) {
			super(msg);
		}

	}

	public static class PlacasOrNumeroAlreadyExistsException extends BaseException {

		private static final long serialVersionUID = 8241229828858485988L;

		public PlacasOrNumeroAlreadyExistsException(String msg) {
			super(msg);
		}

	}
}
