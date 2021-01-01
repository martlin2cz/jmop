package cz.martlin.jmop.player.cli.commands;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.DefaultPlayerFascadeBuilder;
import picocli.CommandLine.Command;

@Command(subcommands = { //
		ListBundlesCommand.class, //
		ListPlaylistsCommand.class, //
		PlayCommand.class })
public class InteractiveRootCommand implements Runnable {

	private final JMOPPlayerFascade fascade;

	public InteractiveRootCommand(JMOPPlayerFascade fascade) {
		super();
		this.fascade = fascade;
	}

	public JMOPPlayerFascade fascade() {
		return fascade;
	}

	/////////////////////////////////////////////////////////////////
	
	@Override
	public void run() {
		System.out.println("InteractiveRootCommand.run()");
	}

	@Command(name = "tracks")
	@Deprecated
	public void listTracks() throws JMOPMusicbaseException {
		JMOPPlayerFascade fascade = DefaultPlayerFascadeBuilder.createTesting();
		fascade.load();
		Bundle bundle = fascade.bundles().stream().findAny().get(); // FIXME

		System.out.println("Tracks:");
		Set<Track> tracks = fascade.tracks(bundle);
		for (Track track : tracks) {
			System.out.println(track.getTitle());
		}
	}
}
