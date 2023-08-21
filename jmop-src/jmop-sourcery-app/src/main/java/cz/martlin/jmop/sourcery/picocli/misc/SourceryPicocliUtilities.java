package cz.martlin.jmop.sourcery.picocli.misc;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryMusicbase;
import cz.martlin.jmop.sourcery.picocli.mixins.CreateOrAddToPlaylistGroup;

/**
 * Various small utils for the picocli-based sourcery app.
 * 
 * @author martin
 *
 */
public class SourceryPicocliUtilities {

	private static final Logger LOGGER = LoggerFactory.getLogger(SourceryPicocliUtilities.class);

	/**
	 * Adds the given tracks into the given playlists (or creating them first if
	 * requested).
	 * 
	 * @param bundle
	 * @param tracks
	 * @param playlistsArgs can be null
	 */
	public static void playlistThem(Bundle bundle, List<Track> tracks, List<CreateOrAddToPlaylistGroup> playlistsArgs) {
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
}
