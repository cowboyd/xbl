package xbl.error;

public class NotSignedInException extends RuntimeException {
	public NotSignedInException(String message) {
		super(message);
	}

	public static void check(boolean condition, String message) {
		if (!condition) throw new NotSignedInException(message);
	}
}
