package com.sytecso.component.exceptions;

import com.sytecso.component.DTOFile;

public class BatchException extends Exception{

	private static final long serialVersionUID = -5809482028682387545L;
	
	public static class BlankSpaceException extends BaseException {
		private static final long serialVersionUID = 2114651944895850666L;
		public BlankSpaceException(DTOFile dtoFileError) {
			super(dtoFileError);
		}
	}
	public static class HeaderException extends BaseException {
		private static final long serialVersionUID = -3748063392437763446L;

		public HeaderException(String message) {
			super(message);
		}
	}
	public static class FileEmptyException extends BaseException {
		private static final long serialVersionUID = 6084071945842584116L;

		public FileEmptyException(String message) {
			super(message);
		}
	}
	public static class NotResultsException extends BaseException {
		
		private static final long serialVersionUID = 1322152877459889704L;

		public NotResultsException(String message) {
			super(message);
		}
	}
	public static class NotRowsInsertedException extends BaseException {

		private static final long serialVersionUID = -2456570767770784946L;

		public NotRowsInsertedException(String message) {
			super(message);
		}
	}
}
