package cz.martlin.jmop.player.cli.commands;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "playlists")
public class ListPlaylistsCommand extends AbstractCommand {

	@Parameters(arity = "0..1")
	private Bundle bundle;
	
	public ListPlaylistsCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Set<Playlist> playlists = fascade.playlists(bundle);
		
		if (bundle != null) {
		PrintUtil.print("Playlists in " + bundle.getName() + ":");
		} else {
			PrintUtil.print("All playlists:");
		}
		for (Playlist playlist: playlists) {
			PrintUtil.printPlaylistName(playlist, bundle == null);
		}
		PrintUtil.emptyLine();
	}

}
