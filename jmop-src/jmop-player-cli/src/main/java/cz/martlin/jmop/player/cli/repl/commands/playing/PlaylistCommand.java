package cz.martlin.jmop.player.cli.repl.commands.playing;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.commands.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ScopeType;

@Command(name = "playlist", subcommands = {//
	PlaylistAddTrackCommand.class, //
	PlaylistInsertTrackCommand.class, //
	PlaylistRemoveTrackCommand.class, //
})
public class PlaylistCommand extends AbstractCommand {

	@Parameters(arity =  "0..1", index = "0", scope = ScopeType.INHERIT)
	private Playlist playlist;
	
	public PlaylistCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	public Playlist getPlaylist() {
		if (playlist == null) {
			return fascade.currentPlaylist();
		} else {
			return playlist;
		}
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Playlist playlist = getPlaylist();
		print(playlist);
	}

	private static void print(Playlist playlist) {
		for (int i = 0; i < playlist.getTracks().count(); i++) {
			Track track = playlist.getTracks().getTrack(i);
			
			if (i != playlist.getCurrentTrackIndex()) {
				PrintUtil.print(i, "", track, "(", track.getDuration(), ")");
			} else {
				PrintUtil.print(">", "", track, "(", track.getDuration(), ")");
			}
		}
	}
}
