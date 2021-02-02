package cz.martlin.jmop.core.exceptions;

/**
 * The any kind of unexpected operation, which seems to be unrecoverable, 
 * thus cannot be handled and has to be thrown.
 * 
 * @author martin
 *
 */
public class JMOPRuntimeException extends RuntimeException {
	private static final long serialVersionUID = -3485128620366511395L;

	public JMOPRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public JMOPRuntimeException(String message) {
		super(message);
	}

}
