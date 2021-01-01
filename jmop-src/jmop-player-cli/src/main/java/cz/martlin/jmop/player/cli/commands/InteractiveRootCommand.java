package cz.martlin.jmop.player.cli.commands;

import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "jmop-cli", //
		subcommands = { //
				ListBundlesCommand.class, //
				ListPlaylistsCommand.class, //
				ListTracksCommand.class, //
				StatusCommand.class, //
				PlayCommand.class })
public class InteractiveRootCommand implements Runnable {

	private final JMOPPlayerFascade fascade;

	public InteractiveRootCommand(JMOPPlayerFascade fascade) {
		super();
		this.fascade = fascade;
	}

	public JMOPPlayerFascade fascade() {
		return fascade;
	}

	/////////////////////////////////////////////////////////////////

	@Override
	public void run() {
		System.out.println("InteractiveRootCommand.run()");
	}

}
