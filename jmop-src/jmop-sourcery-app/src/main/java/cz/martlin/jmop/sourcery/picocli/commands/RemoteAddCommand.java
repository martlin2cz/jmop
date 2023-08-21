package cz.martlin.jmop.sourcery.picocli.commands;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;
import cz.martlin.jmop.sourcery.fascade.JMOPRemote;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryMusicbase;
import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import cz.martlin.jmop.sourcery.picocli.misc.Service;
import cz.martlin.jmop.sourcery.picocli.mixins.CreateOrAddToPlaylistGroup;
import cz.martlin.jmop.sourcery.picocli.mixins.CreateOrUseBundleGroup;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "add")
public class RemoteAddCommand implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteAddCommand.class);

	@Option(names = { "--service", "-s" }, required = true)
	private Service service;

	@ArgGroup(multiplicity = "1")
	private CreateOrUseBundleGroup bundleArgs;

	@Option(names = { "--download", "-D" }, required = false)
	private boolean download = true;

	@Option(names = { "--no-download", "-n" }, required = false)
	private boolean noDownload = false;

	@ArgGroup(multiplicity = "0..*")
	private List<CreateOrAddToPlaylistGroup> playlistsArgs;

	@Parameters(descriptionKey = "QUERY", arity = "1..*")
	private List<String> queries;

	public RemoteAddCommand() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void run() {
		LOGGER.debug("Searching for {} to download", queries);

		try {
			Bundle bundle = bundleArgs.getBundle();
			JMOPRemote remote = service.pickRemote();
			
			List<Track> tracks = remote.add(bundle, queries, download);
			
			LOGGER.debug("Searched tracks {}", tracks);
			playlistThem(bundle, tracks);
		} catch (JMOPSourceryException e) {
			LOGGER.error("The add command failed", e);
		}
	}

	private void playlistThem(Bundle bundle, List<Track> tracks) {
		if (playlistsArgs == null) {
			// "add to playlist" options not specified at all
			return;
		}
		JMOPSourceryMusicbase musicbase = JMOPSourceryProvider.get().getSourcery().musicbase();

		for (CreateOrAddToPlaylistGroup playlistArgs : playlistsArgs) {
			Playlist playlist = playlistArgs.getPlaylist(bundle);
			PlaylistModifier modifier = musicbase.modifyPlaylist(playlist);

			for (Track track : tracks) {
				LOGGER.debug("Adding track {} to playlist {}", track.getTitle(), playlist.getName());
				modifier.append(track);
			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "RemoteAddCommand [service=" + service + ", bundleArgs=" + bundleArgs + ", download=" + download
				+ ", noDownload=" + noDownload + ", playlistsArgs=" + playlistsArgs + ", queries=" + queries + "]";
	}
}
