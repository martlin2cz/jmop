package cz.martlin.jmop.sourcery.remote;

import java.io.IOException;

/**
 * The sourcery exception.
 * 
 * @author martin
 *
 */
public class JMOPSourceryException extends IOException {

	private static final long serialVersionUID = 8852826352619960469L;

	public JMOPSourceryException(String message, Throwable cause) {
		super(message, cause);
	}

	public JMOPSourceryException(String message) {
		super(message);
	}

}