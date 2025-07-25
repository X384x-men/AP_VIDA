package com.sytecso.component.exceptions;

public class UsuarioAccesoException extends Exception {
	private static final long serialVersionUID = -8456211475514501516L;

	public static class UsuarioExistsException extends BaseException {
		private static final long serialVersionUID = -1104332827897494804L;

		public UsuarioExistsException(String msg) {
			super(msg);
		}
	}
	public static class UsuarioNotExistsException extends BaseException {

		private static final long serialVersionUID = 850802001892635893L;

		public UsuarioNotExistsException(String msg) {
			super(msg);
		}
	}

	public static class UsuarioNotUpdatedException extends BaseException {
		private static final long serialVersionUID = 5069390037963405766L;
		public UsuarioNotUpdatedException(String msg) {
			super(msg);
		}
	}
	
	public static class PasswordNotUpdatedException extends BaseException {
		private static final long serialVersionUID = 3917146222417730788L;

		public PasswordNotUpdatedException(String msg) {
			super(msg);
		}
	}
}
