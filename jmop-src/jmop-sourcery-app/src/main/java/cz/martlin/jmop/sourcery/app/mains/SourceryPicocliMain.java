package cz.martlin.jmop.sourcery.app.mains;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.sourcery.picocli.commands.structural.SourceryRootCommand;
import cz.martlin.jmop.sourcery.picocli.converters.BundleConverter;
import picocli.CommandLine;

/**
 * The main	sourcery executable.
 * 
 * @author martin
 *
 */
public class SourceryPicocliMain {

	public static void main(String[] args) {
		CommandLine cl = createCommandLine();
		
		int exitCode = cl.execute(args);
		System.exit(exitCode);
	}

	/**
	 * Constructs the picocli command line.
	 * 
	 * @return
	 */
	private static CommandLine createCommandLine() {
		SourceryRootCommand command = new SourceryRootCommand();
		
		CommandLine cl = new CommandLine(command);
		cl.registerConverter(Bundle.class, new BundleConverter());
		//TODO playlist converter in here
		cl.setCaseInsensitiveEnumValuesAllowed(true);
		return cl;
	}

	
}
