package cz.martlin.jmop.core.misc;

public class JMOPSourceException extends Exception {

	private static final long serialVersionUID = 6881006825304322873L;

	public JMOPSourceException(String message, Throwable cause) {
		super(message, cause);
	}

	public JMOPSourceException(String message) {
		super(message);
	}

	public JMOPSourceException(Throwable cause) {
		super(cause);
	}

}
