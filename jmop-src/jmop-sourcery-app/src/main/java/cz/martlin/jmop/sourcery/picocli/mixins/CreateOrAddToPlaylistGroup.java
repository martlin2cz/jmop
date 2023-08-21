package cz.martlin.jmop.sourcery.picocli.mixins;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryMusicbase;
import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import picocli.CommandLine.Option;

public class CreateOrAddToPlaylistGroup {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrAddToPlaylistGroup.class);

	@Option(names = { "--add-to-playlist", "-p" }, required = true)
	private String addToPlaylist;

	@Option(names = { "--add-to-existing-playlist", "-ap" }, required = true)
	private String addToExistingPlaylist;

	@Option(names = { "--add-to-new-playlist", "-P" }, required = true)
	private String addToNewPlaylist;

	public Playlist getPlaylist(Bundle bundle) {
		JMOPSourceryMusicbase musicbase = JMOPSourceryProvider.get().getSourcery().musicbase();

		if (addToPlaylist != null) {
			LOGGER.debug("Will add to existing or new playlist {}", addToPlaylist);
			if (musicbase.playlistOfName(bundle, addToPlaylist) != null) {
				return getPlaylistOrFail(bundle, addToPlaylist, musicbase);
			} else {
				return musicbase.createNewPlaylist(bundle, addToPlaylist);
			}
		}

		if (addToExistingPlaylist != null) {
			LOGGER.debug("Will add to playlist {} (if exists)", addToExistingPlaylist);
			return getPlaylistOrFail(bundle, addToExistingPlaylist, musicbase);
		}

		if (addToNewPlaylist != null) {
			LOGGER.debug("Will create playlist {}", addToNewPlaylist);
			return musicbase.createNewPlaylist(bundle, addToNewPlaylist);
		}

		throw new IllegalStateException("May never happen");
	}

	private Playlist getPlaylistOrFail(Bundle bundle, String name, JMOPSourceryMusicbase musicbase) {
		return Objects.requireNonNull( //
				musicbase.playlistOfName(bundle, name), //
				"Playlist " + name + " doesn't exist");
	}

	@Override
	public String toString() {
		return "CreateOrAddToPlaylistGroup [addToPlaylist=" + addToPlaylist + ", addToExistingPlaylist="
				+ addToExistingPlaylist + ", addToNewPlaylist=" + addToNewPlaylist + "]";
	}

	

}
