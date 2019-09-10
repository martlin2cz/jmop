package cz.martlin.jmop.core.misc;

import java.io.File;

public interface BaseUIInterractor {
	public String prompt(String message);

	public boolean confirm(String message);

	public File promptFile(String message, String extension);

	public void displayError(String message);

}
