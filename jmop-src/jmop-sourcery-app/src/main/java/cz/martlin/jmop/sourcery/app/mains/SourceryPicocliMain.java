package cz.martlin.jmop.sourcery.app.mains;

import java.io.File;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.sourcery.config.BaseJMOPSourceryConfig;
import cz.martlin.jmop.sourcery.config.TestingConstantSourceryConfiguration;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryBuilder;
import cz.martlin.jmop.sourcery.picocli.commands.structural.SourceryRootCommand;
import cz.martlin.jmop.sourcery.picocli.converters.BundleConverter;
import cz.martlin.jmop.sourcery.picocli.misc.SourceryCommandsFactory;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

public class SourceryPicocliMain {

	public static void main(String[] args) {
		CommandLine cl = createCommandLine();
		
		int exitCode = cl.execute(args);
		System.exit(exitCode);
	}

	private static CommandLine createCommandLine() {
		SourceryRootCommand command = new SourceryRootCommand();
		
		CommandLine cl = new CommandLine(command);
		cl.registerConverter(Bundle.class, new BundleConverter());
		//TODO playlist converter in here
		cl.setCaseInsensitiveEnumValuesAllowed(true);
		return cl;
	}

	
}
