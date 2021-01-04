package cz.martlin.jmop.player.cli.repl.commands.musicbase;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "track")
public class RenameTrackCommand extends AbstractCommand {

//	@Parameters(arity = "1", index = "0")
//	private Bundle bundle;
	
	@Parameters(arity = "1", index = "0")
	private Track track;
	
	@Parameters(arity = "1", index = "1")
	private String newTitle;
	
	
	public RenameTrackCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		fascade.renameTrack(track, newTitle);
	}

}
