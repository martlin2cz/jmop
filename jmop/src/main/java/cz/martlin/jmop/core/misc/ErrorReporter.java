package cz.martlin.jmop.core.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;

/**
 * Central point for all error reports within the JMOP application.
 * 
 * @author martin
 *
 */
public class ErrorReporter {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public ErrorReporter() {
		super();
	}

	/**
	 * Reports some application error. The error might be like environment or
	 * user one, not the internal bug within the application (in such cases use
	 * {@link #internal(Exception)}).
	 * 
	 * In fact logs and shows error dialog (if possible).
	 * 
	 * @param e
	 */
	public void report(Exception e) {
		LOG.error("An error occured", e);

		GuiComplexActionsPerformer.showErrorDialog("Error occured", "The operation could not be completed.");
	}

	/**
	 * Reports internal error (typically error caught by general "catch
	 * Exception" statement).
	 * 
	 * In fact logs and shows error dialog (if possible).
	 * 
	 * @param e
	 */
	public void internal(Exception e) {
		LOG.error("An internal error occured", e);

		GuiComplexActionsPerformer.showErrorDialog("Internal error occured",
				"Internal error occured, probably by bug in the application. "
						+ "If problem persist, close and reopen the application.");
	}

}
