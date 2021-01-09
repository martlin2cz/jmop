package cz.martlin.jmop.player.cli.repl.commands.playing;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

@Command(name = "insert")
public class PlaylistInsertTrackCommand extends AbstractCommand {

	@ParentCommand
	private PlaylistCommand parent;
	
	@Parameters(arity =  "1", index = "0")
	private int position;
	
	@Parameters(arity =  "1", index = "1")
	private Track track;

	public PlaylistInsertTrackCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}
	
	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Playlist playlist = parent.getPlaylist();
		
		PlaylistModifier modifier = new PlaylistModifier(playlist); //TODO use fascade?
		modifier.insertBefore(track, position);
		//TODO make it autosave
	}
	

}
