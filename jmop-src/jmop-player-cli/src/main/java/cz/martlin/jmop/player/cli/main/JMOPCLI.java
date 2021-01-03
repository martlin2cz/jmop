package cz.martlin.jmop.player.cli.main;

import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.DefaultPlayerFascadeBuilder;

public class JMOPCLI {

	public static void main(String[] args) {
		JMOPPlayerFascade fascade = null;
		try {
			fascade = DefaultPlayerFascadeBuilder.createTesting();
			fascade.load();
		} catch (Exception e) {
			System.err.println("Could not initialize the JMOP: " + e.getMessage());
			System.exit(1);
		}

		try {
			JmopRepl repl = new JmopRepl(fascade);
			repl.runREPL();

		} catch (Exception e) {
			System.err.println("Could not run the REPL: " + e.getMessage());
			System.exit(2);
		}

		try {
			fascade.terminate();
		} catch (Exception e) {
			System.err.println("Could terminate the JMOP: " + e.getMessage());
			System.exit(3);
		}
	}

}
