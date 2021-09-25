package cz.martlin.jmop.player.cli.repl.exit;

public class OperationRejectedException extends RuntimeException {

	private static final long serialVersionUID = -137578049000014601L;

	public OperationRejectedException() {
	}

	public OperationRejectedException(String message) {
		super(message);
	}

	public OperationRejectedException(String message, Throwable cause) {
		super(message, cause);
	}

}
