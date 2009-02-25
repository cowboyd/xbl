package xbox;


public class NotSignedInException extends RuntimeException {
	public NotSignedInException() {
		super("Not Signed In");
	}
}
