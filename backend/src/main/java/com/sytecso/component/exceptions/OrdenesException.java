package com.sytecso.component.exceptions;

import com.sytecso.component.DTOFile;

public class OrdenesException extends Exception {
	private static final long serialVersionUID = 1869015946930287499L;

	public static class NotLocationFoud extends BaseException {
		private static final long serialVersionUID = -8970323017283693992L;

		public NotLocationFoud(DTOFile dtoFileError) {
			super(dtoFileError);
		}
	}

	public static class NotOrdenCreatedException extends BaseException {
		private static final long serialVersionUID = -9141401934486419416L;

		public NotOrdenCreatedException(DTOFile dtoFileError) {
			super(dtoFileError);
		}
	}

	public static class InsufficientItemsException extends BaseException {
		private static final long serialVersionUID = -9141401934486419416L;

		public InsufficientItemsException(DTOFile dtoFileError) {
			super(dtoFileError);
		}
	}
	
	public static class ItemsNotCreatedException extends BaseException {
		private static final long serialVersionUID = -9141401934486419416L;

		public ItemsNotCreatedException(DTOFile dtoFileError) {
			super(dtoFileError);
		}
	}
}
