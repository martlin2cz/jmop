package cz.martlin.jmop.sourcery.engine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.sourcery.local.BasePlaylistImporter;
import cz.martlin.jmop.sourcery.local.BasePlaylistImporter.PlaylistData;

/**
 * The importer of the playlist from ... well, just from some external playlist
 * file. Nothing else implemented.
 * 
 * @author martin
 *
 */
public class PlaylistImporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlaylistImporter.class);

	private final BaseMusicbase musicbase;
	private final BasePlaylistImporter importer;

	public PlaylistImporter(BaseMusicbase musicbase, BasePlaylistImporter importer) {
		super();
		this.musicbase = musicbase;
		this.importer = importer;
	}

	public Playlist importPlaylist(File playlistFile, Bundle inBundle, TrackFileCreationWay createTracks) {
		LOGGER.debug("Importing playlist from {}", playlistFile);

		PlaylistData playlistData;
		try {
			playlistData = importer.load(playlistFile, createTracks);
		} catch (IOException e) {
			LOGGER.error("Playlist load failed", e);
			return null;
		}

		String name = playlistData.getName();
		Playlist playlist = musicbase.createNewPlaylist(inBundle, name);

		List<TrackData> trackDatas = playlistData.getTracks();
		addTracks(trackDatas, inBundle, playlist, createTracks);

		return playlist;
	}

	private void addTracks(List<TrackData> trackDatas, Bundle bundle, Playlist playlist,
			TrackFileCreationWay createTrack) {
		for (TrackData trackData : trackDatas) {
			Track track = processTrack(bundle, trackData, createTrack);
			playlist.addTrack(track);
		}

		musicbase.playlistUpdated(playlist);

	}

	private Track processTrack(Bundle bundle, TrackData trackData, TrackFileCreationWay createTrack) {
		String title = trackData.getTitle();
		Track track = musicbase.tracks(bundle).stream() //
				.filter(t -> t.getTitle().equals(title)) //
				.findAny().orElse(null);

		if (track == null) {
			LOGGER.debug("Creating new track {}", title);
			return musicbase.createNewTrack(bundle, trackData, createTrack, trackData.getTrackFile());
		} else {
			LOGGER.debug("Using existing track {}", title);
			return track;
		}
	}

}
