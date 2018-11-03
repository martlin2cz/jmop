package cz.martlin.jmop.core.misc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;

public class ErrorReporter {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public ErrorReporter() {
	}

	public void report(Exception e) {
		LOG.error("An error occured", e);

		GuiComplexActionsPerformer.showErrorDialog("Error occured", "The operation could not be completed.");
	}

	public void internal(Exception e) {
		LOG.error("An internal error occured", e);

		GuiComplexActionsPerformer.showErrorDialog("Internal error occured",
				"Internal error occured, probably by bug in the application. "
						+ "If problem persist, close and reopen the application.");
	}

}
