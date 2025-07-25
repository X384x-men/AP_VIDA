package com.sytecso.component.exceptions;

public class RolAccesoException extends Exception {

	private static final long serialVersionUID = -7296464838463488224L;

	public static class RolNotUpdatedException extends BaseException {

		public RolNotUpdatedException(String msg) {
			super(msg);
		}

		private static final long serialVersionUID = 7278735435603920546L;

	}

	public static class RolNotCreatedException extends BaseException {

		public RolNotCreatedException(String msg) {
			super(msg);
		}

		private static final long serialVersionUID = 7278735435603920546L;

	}

	public static class NotRolesFoundException extends BaseException {

		public NotRolesFoundException(String msg) {
			super(msg);
		}

		private static final long serialVersionUID = 7278735435603920546L;

	}

	public static class RolNotAsocciateException extends BaseException {

		private static final long serialVersionUID = -2646720870093249611L;

		public RolNotAsocciateException(String msg) {
			super(msg);
		}
	}

	public static class RolCannotNotAsocciateViewException extends BaseException {

		private static final long serialVersionUID = -2646720870093249611L;

		public RolCannotNotAsocciateViewException(String msg) {
			super(msg);
		}
	}
	public static class RolExistsException extends BaseException {

		private static final long serialVersionUID = -2646720870093249611L;

		public RolExistsException(String msg) {
			super(msg);
		}
	} 
	public static class RolNotExistsException extends BaseException {

		private static final long serialVersionUID = -2646720870093249611L;

		public RolNotExistsException(String msg) {
			super(msg);
		}
	} 
}
