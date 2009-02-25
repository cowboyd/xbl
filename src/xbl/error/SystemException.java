package xbl.error;

public class SystemException extends RuntimeException {
	public SystemException(String message) {
		super(message);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public static void check(boolean condition, String message) {
		if (!condition) {
			throw new SystemException(message);
		}
	}
}
