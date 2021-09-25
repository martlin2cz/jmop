package cz.martlin.jmop.core.misc;

import java.io.IOException;
import java.sql.SQLException;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * General exception reporting something bad happened to some JMOP musicbase.
 * This can mean either some missing resource (the owning bundle was removed in
 * the meanime) or some other general failuer, like the invalid format of the input file.
 * 
 * Yes, it is kind of combination of the {@link IOException} and {@link SQLException}.
 * 
 * @author martin
 * @deprecated replaced by {@link JMOPPersistenceException} and 
 */
@Deprecated
public class JMOPMusicbaseException extends Exception {

	private static final long serialVersionUID = 6881006825304322873L;

	public JMOPMusicbaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public JMOPMusicbaseException(String message) {
		super(message);
	}

	public JMOPMusicbaseException(Throwable cause) {
		super(cause);
	}

}
