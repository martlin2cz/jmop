package cz.martlin.jmop.player.cli.commands;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "playlists")
public class ListPlaylistsCommand extends AbstractCommand {

	public ListPlaylistsCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Bundle bundle = fascade.bundles().stream().findAny().get(); //FIXME
		
		Set<Playlist> playlists = fascade.playlists(bundle);
		System.out.println("Playlists:");
		for (Playlist playlist: playlists) {
			System.out.println(playlist.getName());
		}
	}

}
