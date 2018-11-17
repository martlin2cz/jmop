package cz.martlin.jmop.core.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.gui.local.Msg;
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
		LOG.error("An error occured", e); //$NON-NLS-1$

		GuiComplexActionsPerformer.showErrorDialog(Msg.get("Error_occured"), Msg.get("The_operation_could_not_be_completed")); //$NON-NLS-1$ //$NON-NLS-2$
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
		LOG.error("An internal error occured", e); //$NON-NLS-1$

		GuiComplexActionsPerformer.showErrorDialog(Msg.get("Internal_error_occured"), //$NON-NLS-1$
				Msg.get("Internal_error_occured_probably_by_bug_") //$NON-NLS-1$
						+ Msg.get("If_problem_persist_")); //$NON-NLS-1$
	}

}
