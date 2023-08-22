package cz.martlin.jmop.core.misc;

import java.io.File;

/**
 * The abstract component allowing backend classes to interact with user, when
 * nescessary. Ask him something or something.
 * 
 * @author martin
 *
 */
public interface BaseUIInterractor {
	public String prompt(String message);

	public boolean confirm(String message);

	public File promptFile(String message, String extension);

	public void displayError(String message);

}
