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

@Command(name = "remove")
public class PlaylistRemoveTrackCommand extends AbstractCommand {

	@ParentCommand
	private PlaylistCommand parent;
	
	@Parameters(arity =  "0..1", index = "0")
	private Track track;
	
	@Parameters(arity =  "0..1", index = "1")
	private int index;
	
	//TODO remove by index
	
	public PlaylistRemoveTrackCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}
	
	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Playlist playlist = parent.getPlaylist();
		
		PlaylistModifier modifier = new PlaylistModifier(playlist); //TODO use fascade?
		if (track != null) {
			modifier.removeAll(track);
		} else {
			modifier.remove(index);
		}
		
		//TODO make it autosave
	}
	

}
