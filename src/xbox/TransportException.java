package xbox;

public class TransportException extends RuntimeException {
	public TransportException(Exception e) {
		super(e);
	}
}
