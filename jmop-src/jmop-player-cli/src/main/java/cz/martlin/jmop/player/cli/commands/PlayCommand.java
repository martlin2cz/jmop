package cz.martlin.jmop.player.cli.commands;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "play")
public class PlayCommand extends AbstractCommand {
	@Parameters(index = "0", arity = "0..2")
	private String bundleName;

	@Parameters(index = "1", arity = "0..2")
	private String playlistName;

	public PlayCommand() {
	}
	
	@Override
	protected void doRun() throws JMOPMusicbaseException {
		if (bundleName == null) {
			System.out.println("playing");
		}

		if (bundleName != null && playlistName == null) {
			System.out.println("playing bundle " + bundleName);
		}

		if (bundleName != null && playlistName != null) {
			System.out.println("playing playlist " + playlistName + " in bundle " + bundleName);
		}
	}

}
