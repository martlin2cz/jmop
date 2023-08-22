package cz.martlin.jmop.sourcery.fascade;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.sourcery.engine.PlaylistImporter;
import cz.martlin.jmop.sourcery.engine.TracksImporter;
import cz.martlin.jmop.sourcery.local.BasePlaylistImporter;
import cz.martlin.jmop.sourcery.local.BaseTracksFromDirOrFileImporter;
import cz.martlin.jmop.sourcery.local.BaseTracksFromFileImporter;

/**
 * The sourcery fascade responsible for local sourcering (importing from
 * elsewhere in the computer).
 * 
 * @author martin
 *
 */
public class JMOPLocal {

	/**
	 * The tracks importer.
	 */
	private final TracksImporter tracksImporter;
	/**
	 * The playlists importer.
	 */
	private final PlaylistImporter playlistImporter;

	/**
	 * Creates.
	 * 
	 * @param musicbase
	 * @param trackFormat
	 * @param tracksFromDirImpoter
	 * @param tracksFromPlaylistImpoter
	 * @param playlistFromPlaylistImpoter
	 */
	public JMOPLocal(BaseMusicbase musicbase, TrackFileFormat trackFormat, BaseTracksFromDirOrFileImporter tracksFromDirImpoter, BaseTracksFromFileImporter tracksFromPlaylistImpoter, BasePlaylistImporter playlistFromPlaylistImpoter) {
		super();
		this.tracksImporter = new TracksImporter(musicbase, trackFormat, tracksFromDirImpoter, tracksFromPlaylistImpoter);
		this.playlistImporter = new PlaylistImporter(musicbase, playlistFromPlaylistImpoter);
	}

	/**
	 * Import tracks from given dir or file into the given bundle.
	 * 
	 * @param dirOrFile
	 * @param bundle
	 * @param recursive
	 * @param createFiles
	 * @return
	 */
	public List<Track> importTracksFromDirOrFile(File dirOrFile, Bundle bundle, boolean recursive,
			TrackFileCreationWay createFiles) {
		return tracksImporter.importFromDirOrFile(dirOrFile, bundle, createFiles, recursive);
	}

	/**
	 * Import tracks from given dirs and/or files into the given bundle.
	 * 
	 * @param dirsOrFiles
	 * @param bundle
	 * @param recursive
	 * @param createFiles
	 * @return
	 */
	public List<Track> importTracksFromDirsOrFiles(List<File> dirsOrFiles, Bundle bundle, boolean recursive,
			TrackFileCreationWay createFiles) {
		return dirsOrFiles.stream() //
				.flatMap(df -> tracksImporter.importFromDirOrFile(df, bundle, createFiles, recursive).stream()) //
				.collect(Collectors.toList());
	}

	/**
	 * Import tracks from given playlist files into the given bundle.
	 * 
	 * @param files
	 * @param bundle
	 * @param createFiles
	 * @return
	 */
	public List<Track> importTracksFromPlaylist(File file, Bundle bundle, TrackFileCreationWay createFiles) {
		return tracksImporter.importFromPlaylistFile(file, bundle, createFiles);
	}
	
	/**
	 * Import tracks from given playlist files into the given bundle.
	 * 
	 * @param files
	 * @param bundle
	 * @param createFiles
	 * @return
	 */
	public Playlist importPlaylistFromPlaylist(File file, Bundle bundle, TrackFileCreationWay createFiles) {
		return playlistImporter.importPlaylist(file, bundle, createFiles);
	}
}

