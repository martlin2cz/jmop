package cz.martlin.jmop.core.misc;

public class ExternalProgramException extends Exception {

	private static final long serialVersionUID = -6400388738367283071L;

	public ExternalProgramException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExternalProgramException(String message) {
		super(message);
	}

}
