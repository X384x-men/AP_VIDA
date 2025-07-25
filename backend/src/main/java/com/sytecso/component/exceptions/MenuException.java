package com.sytecso.component.exceptions;

public class MenuException extends Exception {

	private static final long serialVersionUID = -8208385675800497295L;

	public static class SeccionNotExistsException extends BaseException {
		public SeccionNotExistsException(String msg) {
			super(msg);
		}

		private static final long serialVersionUID = 6455384879385220757L;
	}

	public static class SeccionNotCreatedException extends BaseException {

		private static final long serialVersionUID = -3148704383437549170L;

		public SeccionNotCreatedException(String msg) {
			super(msg);
		}

	}

	public static class SeccionNotRemovedException extends BaseException {

		private static final long serialVersionUID = -5759376923904615806L;

		public SeccionNotRemovedException(String msg) {
			super(msg);
		}

	}
}
