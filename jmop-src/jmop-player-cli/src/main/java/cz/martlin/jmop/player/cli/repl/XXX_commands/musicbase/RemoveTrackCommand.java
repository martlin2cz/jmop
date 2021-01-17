package cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.XXX_commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "track")
public class RemoveTrackCommand extends AbstractCommand {

	@Parameters(arity = "1", index = "0")
	private Track track;
	
	public RemoveTrackCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		fascade.removeTrack(track);
	}

}
