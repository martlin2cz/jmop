package cz.martlin.jmop.core.exceptions;

/**
 * Trigger when something bad happens in relation to the IO.
 * The one, which when happens, has to be properly handled. 
 * 
 * @author martin
 *
 */
public class JMOPPersistenceException extends Exception {
	private static final long serialVersionUID = -2343551525256531294L;

	public JMOPPersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public JMOPPersistenceException(String message) {
		super(message);
	}

}
