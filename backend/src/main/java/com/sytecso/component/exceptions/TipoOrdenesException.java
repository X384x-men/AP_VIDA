package com.sytecso.component.exceptions;

public class TipoOrdenesException extends Exception{

	private static final long serialVersionUID = 119886406216095406L;
	public static class NotTipoOrdenesFoundException extends BaseException{
		private static final long serialVersionUID = 2033751340142465686L;

		public NotTipoOrdenesFoundException(String msg) {
			super(msg);
		}
		
	}
}
