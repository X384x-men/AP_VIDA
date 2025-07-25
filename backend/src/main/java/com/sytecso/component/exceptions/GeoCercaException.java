package com.sytecso.component.exceptions;

public class GeoCercaException extends Exception {
	private static final long serialVersionUID = 5293592753645239421L;
	public static class CuadrillaNotHaveGeoCerca extends BaseException{
		public CuadrillaNotHaveGeoCerca(String msg) {
			super(msg);
		}

		private static final long serialVersionUID = 6455384879385220757L;
		
	}
	public static class GeoCercaNotHaveCuadrilla extends BaseException {
		private static final long serialVersionUID = 5244463332498983485L;
		public GeoCercaNotHaveCuadrilla(String msg) {
			super(msg);
		}
	}
	public static class DataNotExits extends BaseException {
		private static final long serialVersionUID = -764767624843813464L;
		public DataNotExits(String msg) {
			super(msg);
		}
		
		
	}
}
