package xbl.error;

public class NetworkException extends RuntimeException {

	public NetworkException(String message) {
		super(message);
	}

	public NetworkException(Throwable throwable) {
		super(throwable);
	}

	public NetworkException(String message, Throwable cause) {
		super(message, cause);
	}
}
