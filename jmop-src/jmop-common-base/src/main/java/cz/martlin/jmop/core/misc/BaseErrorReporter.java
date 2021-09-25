package cz.martlin.jmop.core.misc;

public interface BaseErrorReporter {

	/**
	 * Reports some application error. The error might be like environment or
	 * user one, not the internal bug within the application (in such cases use
	 * {@link #internal(Exception)}).
	 * 
	 * In fact logs and shows error dialog (if possible).
	 * 
	 * @param reason
	 * @param e
	 */
	void report(String reason, Exception e);

	/**
	 * Reports internal error (typically error caught by general "catch
	 * Exception" statement).
	 * 
	 * In fact logs and shows error dialog (if possible).
	 * 
	 * @param e
	 */
	void internal(Exception e);

}