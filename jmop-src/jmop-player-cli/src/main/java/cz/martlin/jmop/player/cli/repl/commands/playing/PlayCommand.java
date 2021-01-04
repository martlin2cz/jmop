package cz.martlin.jmop.player.cli.repl.commands.playing;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.commands.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "play")
public class PlayCommand extends AbstractCommand {
	@Parameters(index = "0", arity = "0..1")
	private Playlist playlist;
	
	public PlayCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}



	@Override
	protected void doRun() throws JMOPMusicbaseException {
		if (playlist == null) {
			doPlayInCurrentPlaylist();
		} else {
			doPlayPlaylist(playlist);
		}

		
	}

	private void doPlayInCurrentPlaylist() throws JMOPMusicbaseException {
		if (fascade.currentStatus().isPaused()) {
			fascade.resume();
		} else {
			fascade.play();
			
			Track track = fascade.currentTrack();
			PrintUtil.print("Playing", track);
		}
	}

	private void doPlayPlaylist(Playlist playlist) throws JMOPMusicbaseException {
		fascade.startPlaying(playlist);
		
		Track track = fascade.currentTrack();
		PrintUtil.print("Playing", track);
	}

}
