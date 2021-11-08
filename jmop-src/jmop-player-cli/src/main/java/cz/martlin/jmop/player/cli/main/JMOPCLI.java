package cz.martlin.jmop.player.cli.main;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.player.cli.repl.JmopRepl;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.dflt.DefaultJMOPPlayerBuilder;
import cz.martlin.jmop.player.fascade.dflt.config.ConstantDefaultFascadeConfig;
import cz.martlin.jmop.player.players.JavaFXMediaPlayer;

public class JMOPCLI {
	private static final Logger LOG = LoggerFactory.getLogger(JMOPCLI.class);
	
	public static void main(String[] args) {
		LOG.debug("Starting the CLI");
		
		File root = obtainRoot(args);
		
		JMOPPlayer jmop = null;
		try {
			LOG.debug("Preparing the JMOP");
			//jmop = DefaultJMOPPlayerBuilder.createTesting();
			jmop = DefaultJMOPPlayerBuilder.create(root, new JavaFXMediaPlayer(), new ConstantDefaultFascadeConfig(), new SimpleErrorReporter());
					
			
			LOG.debug("Loading the JMOP");
			jmop.config().load();
			
			LOG.debug("JMOP ready");
		} catch (Exception e) {
			LOG.error("Could initialize", e);
			System.err.println("Could not initialize the JMOP: " + e.getMessage());
			System.exit(1);
		}

		try {
			LOG.debug("Preparing the REPL");
			JmopRepl repl = new JmopRepl(jmop);

			LOG.debug("Starting the REPL");
			repl.runREPL();

			LOG.debug("REPL completed");
		} catch (Exception e) {
			LOG.error("Could not run REPL", e);
			System.err.println("Could not run the REPL: " + e.getMessage());
		}

		try {
			LOG.debug("Terminating the JMOP");
			jmop.config().terminate();
			
			LOG.debug("JMOP terminated");
		} catch (Exception e) {
			LOG.error("Could not terminate", e);
			System.err.println("Could terminate the JMOP: " + e.getMessage());
			System.exit(3);
		}
		
		JavaFXMediaPlayer.shutdownPlatform();
	}

	private static File obtainRoot(String[] args) {
		if (args.length > 0) {
			String arg = args[0];
			//TODO do the -h/--help properly!
			if (arg == "-h" || arg == "--help") {
				System.out.println("JMOP player");
				System.out.println("Usage: JMOP [ROOT DIR]");
				System.out.println("   if no ROOT DIR provided, cwd is used");
				System.exit(0);
			}
			
			File file = new File(arg);
			if (file.isDirectory()) {
				LOG.info("Will use the provided " + file + " as a root directory");
				return file;
			} else {
				LOG.warn("The " + file + " is not an existing directory");
				// let it fall back
			}
		}	
	
		File file = new File(System.getProperty("user.dir"));
		LOG.info("Using the cwd (" + file + ") as a root directory");
		return file;
	}

}
