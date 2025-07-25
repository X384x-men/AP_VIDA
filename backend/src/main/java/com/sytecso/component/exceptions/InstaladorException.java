package com.sytecso.component.exceptions;

public class InstaladorException extends Exception {
	private static final long serialVersionUID = -7663941813263994919L;

	public static class NotInstaladorDeletedException extends BaseException {
		private static final long serialVersionUID = 3406095189274141721L;

		public NotInstaladorDeletedException(String msg) {
			super(msg);
		}
	}

	public static class RolInstaladorAlreadyExistsException extends BaseException {
		private static final long serialVersionUID = 7851989656546856627L;

		public RolInstaladorAlreadyExistsException(String msg) {
			super(msg);
		}
	}

	public static class NumeroEmpleadoAlreadyExistsException extends BaseException {

		private static final long serialVersionUID = -7175951236973960765L;

		public NumeroEmpleadoAlreadyExistsException(String msg) {
			super(msg);
		}
	}

	public static class RolNotValidException extends BaseException {
		private static final long serialVersionUID = 854132611793401115L;

		public RolNotValidException(String msg) {
			super(msg);
		}
	}

	public static class NumeroEmpleadoNotValidException extends BaseException {
		private static final long serialVersionUID = 8497018985147885169L;

		public NumeroEmpleadoNotValidException(String msg) {
			super(msg);
		}
	}

	public static class EmptyRolException extends BaseException {
		private static final long serialVersionUID = -9009282168924108903L;

		public EmptyRolException(String msg) {
			super(msg);
		}
	}

	public static class EmptyNumeroEmpleadoException extends BaseException {
		private static final long serialVersionUID = -6146417575457833879L;

		public EmptyNumeroEmpleadoException(String msg) {
			super(msg);
		}
	}

	public static class NotInstaladorUpdatedException extends BaseException {

		private static final long serialVersionUID = 1767957344153647017L;

		public NotInstaladorUpdatedException(String msg) {
			super(msg);
		}
	}
	public static class NotInstaladoresFoudException extends BaseException {
		private static final long serialVersionUID = -1374700780180383354L;
		public NotInstaladoresFoudException(String msg) {
			super(msg);
		}
	}
	public static class RolNotUpdatedException extends BaseException {
		private static final long serialVersionUID = -3901012624828657473L;
		public RolNotUpdatedException(String msg) {
			super(msg);
		}
	}
}
