package cz.martlin.jmop.player.cli.main;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.commands.InteractiveRootCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.DefaultPlayerFascadeBuilder;
import picocli.CommandLine;

public class JMOPCLI {

	public static void main(String[] args) throws JMOPMusicbaseException {
System.out.println("JMOPCLI.main()");

		JMOPPlayerFascade fascade = DefaultPlayerFascadeBuilder.createTesting();
		fascade.load();
		
		args = new String[] {"play", "foo", "bar"};
		CommandLine.run(new InteractiveRootCommand(fascade), args);
		
	}

}
