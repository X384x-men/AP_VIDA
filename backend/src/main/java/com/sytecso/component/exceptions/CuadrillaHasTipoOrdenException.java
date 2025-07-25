package com.sytecso.component.exceptions;

public class CuadrillaHasTipoOrdenException extends Exception{

	private static final long serialVersionUID = 3740292788775950347L;
	public static class NotDeletedException extends BaseException{

		private static final long serialVersionUID = 4501506536957206280L;
		public NotDeletedException(String msg) {
			super(msg);
		}
	}
}
