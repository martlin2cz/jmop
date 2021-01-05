package cz.martlin.jmop.player.cli.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.player.cli.repl.JmopRepl;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.DefaultPlayerFascadeBuilder;

public class JMOPCLI {
	private static final Logger LOG = LoggerFactory.getLogger(JMOPCLI.class);
	
	public static void main(String[] args) {
		LOG.debug("Starting the CLI");
		
		JMOPPlayerFascade fascade = null;
		try {
			LOG.debug("Preparing the JMOP");
			fascade = DefaultPlayerFascadeBuilder.createTesting();
			
			LOG.debug("Loading the JMOP");
			fascade.load();
			
			LOG.debug("JMOP ready");
		} catch (Exception e) {
			LOG.error("Could initialize", e);
			System.err.println("Could not initialize the JMOP: " + e.getMessage());
			System.exit(1);
		}

		try {
			LOG.debug("Preparing the REPL");
			JmopRepl repl = new JmopRepl(fascade);

			LOG.debug("Starting the REPL");
			repl.runREPL();

			LOG.debug("REPL completed");
		} catch (Exception e) {
			LOG.error("Could not run REPL", e);
			System.err.println("Could not run the REPL: " + e.getMessage());
		}

		try {
			LOG.debug("Terminating the JMOP");
			fascade.terminate();
			
			LOG.debug("JMOP terminated");
		} catch (Exception e) {
			LOG.error("Could not terminate", e);
			System.err.println("Could terminate the JMOP: " + e.getMessage());
			System.exit(3);
		}
	}

}
