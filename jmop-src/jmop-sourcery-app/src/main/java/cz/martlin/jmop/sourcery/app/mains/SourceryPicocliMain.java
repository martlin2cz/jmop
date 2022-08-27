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
import cz.martlin.jmop.sourcery.picocli.commands.SourceryRootCommand;
import cz.martlin.jmop.sourcery.picocli.converters.BundleConverter;
import cz.martlin.jmop.sourcery.picocli.misc.SourceryCommandsFactory;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

public class SourceryPicocliMain {

	public static void main(String[] args) {
		File root = new File("/tmp/jmop-v1.2"); //FIXME
		JMOPSourcery jmopSourcery = createJMOP(root);
			
		CommandLine cl = createCommandLine(jmopSourcery);
		
		int exitCode = cl.execute(args);
		System.exit(exitCode);
	}

	private static CommandLine createCommandLine(JMOPSourcery jmopSourcery) {
		SourceryRootCommand command = new SourceryRootCommand();
		
		IFactory delegeeFactory = CommandLine.defaultFactory();
		IFactory factory = new SourceryCommandsFactory(delegeeFactory, jmopSourcery);
		
		CommandLine cl = new CommandLine(command, factory);
		cl.registerConverter(Bundle.class, new BundleConverter(jmopSourcery));
		//TODO playlist converter in here
		cl.setCaseInsensitiveEnumValuesAllowed(true);
		return cl;
	}

	//TODO move to SourceriesMainsUtil
	protected static JMOPSourcery createJMOP(File root) {
		BaseErrorReporter reporter = new SimpleErrorReporter();
		BaseProgressListener listener = new PrintingListener(System.out);
		
		BaseJMOPSourceryConfig config = new TestingConstantSourceryConfiguration();
		
		JMOPSourcery jmopSourcery = JMOPSourceryBuilder.create(root, reporter, config, listener);
		jmopSourcery.config().load();
		
		return jmopSourcery;
		
	}
}
