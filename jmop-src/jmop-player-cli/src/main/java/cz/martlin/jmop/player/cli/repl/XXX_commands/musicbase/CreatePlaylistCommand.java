package cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.XXX_commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "playlist")
public class CreatePlaylistCommand extends AbstractCommand {
	@Parameters(arity = "1", index = "0")
	private Bundle bundle;

	@Parameters(arity = "1", index = "1")
	private String name;

	public CreatePlaylistCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		fascade.createNewPlaylist(bundle, name);
	}

}
