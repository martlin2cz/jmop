package cz.martlin.jmop.core.misc;

import java.io.IOException;

/**
 * General exception reporting something bad happened to some JMOP (re)source.
 * This could be for instance some download failure or corrupted file on disk.
 * Yes, it is simillar to {@link IOException}.
 * 
 * @author martin
 *
 */
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
