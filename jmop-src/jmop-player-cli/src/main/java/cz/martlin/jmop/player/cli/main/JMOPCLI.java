package cz.martlin.jmop.player.cli.main;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.commands.InteractiveRootCommand;
import cz.martlin.jmop.player.cli.misc.InteractiveCommandsFactory;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.DefaultPlayerFascadeBuilder;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

public class JMOPCLI {

	public static void main(String[] args) throws JMOPMusicbaseException {
System.out.println("JMOPCLI.main()");

		JMOPPlayerFascade fascade = DefaultPlayerFascadeBuilder.createTesting();
		fascade.load();
		
		InteractiveRootCommand command = new InteractiveRootCommand(fascade);
		IFactory factory = new InteractiveCommandsFactory(fascade);
		
		CommandLine cl = new CommandLine(command, factory);
		cl.execute("bundles");
		cl.execute("playlists");
		cl.execute("tracks");
		cl.execute("play", "foo", "bar");
		
	}

}
