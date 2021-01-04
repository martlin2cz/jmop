package cz.martlin.jmop.player.cli.repl;

import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.Arrays;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.DefaultPlayerFascadeBuilder;
import picocli.CommandLine;

public class AbstractReplTest {

	protected JMOPPlayerFascade fascade;

	public AbstractReplTest() {
		super();
	}

	protected CommandLine prepareCL() {
		fascade = DefaultPlayerFascadeBuilder.createTesting();
		try {
			fascade.load();
		} catch (JMOPMusicbaseException e) {
			e.printStackTrace();
			assumeFalse(e != null);
		}
		
		AbstractRepl repl = new JmopRepl(fascade);
		return repl.createStandaloneCommandline();
	}

	protected void exec(CommandLine cl, String... command) {
		System.out.println("======= EXECUTING " + Arrays.toString(command) + " =======");
	
		cl.execute(command);
	
	}

}