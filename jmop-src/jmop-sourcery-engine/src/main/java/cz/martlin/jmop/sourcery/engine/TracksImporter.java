package cz.martlin.jmop.sourcery.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.sourcery.local.BaseTracksFromDirOrFileImporter;
import cz.martlin.jmop.sourcery.local.BaseTracksFromFileImporter;

/**
 * The tracks importer. Component responsible for importing the tracks already
 * existing in the computer filesystem.
 * 
 * @author martin
 *
 */
public class TracksImporter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TracksImporter.class);

	private final BaseMusicbaseModifing musicbaseModifing;

	private final TrackFileFormat filesFormat;
	private final BaseTracksFromDirOrFileImporter fileOrDirImporter;
	private final BaseTracksFromFileImporter fromPlaylistImporter; 

	/**
	 * Creates.
	 * 
	 * @param musicbaseModifing
	 * @param filesFormat
	 * @param fileOrDirImpoter
	 */
	public TracksImporter(BaseMusicbaseModifing musicbaseModifing, TrackFileFormat filesFormat,
			BaseTracksFromDirOrFileImporter fileOrDirImpoter, BaseTracksFromFileImporter fromPlaylistImporter) {
		super();
		this.musicbaseModifing = musicbaseModifing;
		this.filesFormat = filesFormat;
		this.fileOrDirImporter = fileOrDirImpoter;
		this.fromPlaylistImporter = fromPlaylistImporter;
	}

	/**
	 * Imports the tracks from given dir (optinally recursive) or file into the
	 * given bundle, with specified how to manipulate the track files.
	 * 
	 * @param dirOrFile
	 * @param toBundle
	 * @param createFiles
	 * @param recursive
	 * @return
	 */
	public List<Track> importFromDirOrFile(File dirOrFile, Bundle toBundle, TrackFileCreationWay createFiles,
			boolean recursive) {
		LOGGER.info("Importing tracks from {}, recursive? {}, format: {}", //
				dirOrFile.getAbsolutePath(), recursive, filesFormat);

		List<TrackData> datas;
		try {
			datas = fileOrDirImporter.importTracks(dirOrFile, recursive, filesFormat);
		} catch (Exception e) {
			LOGGER.error("Tracks import failed", e);
			return Collections.emptyList();
		}

		return addTracks(toBundle, createFiles, datas);
	}
	
	/**
	 * Imports tracks from given playlist file.
	 * @param playlistFile
	 * @param toBundle
	 * @param createFiles
	 * @return
	 */
	public List<Track> importFromPlaylistFile(File playlistFile, Bundle toBundle, TrackFileCreationWay createFiles) {
		LOGGER.info("Importing tracks from playlist {}, ", //
				playlistFile.getAbsolutePath());

		List<TrackData> datas;
		try {
			datas = fromPlaylistImporter.importTracks(playlistFile);
		} catch (Exception e) {
			LOGGER.error("Tracks import failed", e);
			return Collections.emptyList();
		}

		return addTracks(toBundle, createFiles, datas);
	}


	private List<Track> addTracks(Bundle toBundle, TrackFileCreationWay createFiles, List<TrackData> datas) {
		List<Track> tracks = new ArrayList<>(datas.size());
		for (TrackData trackData : datas) {
			LOGGER.debug("Adding track {}", trackData.getTitle());

			try {
				File trackFile = trackData.getTrackFile();
				Track track = musicbaseModifing.createNewTrack(toBundle, trackData, createFiles, trackFile);
				tracks.add(track);
			} catch (Exception e) {
				LOGGER.error("Track import failed", e);
			}
		}

		return tracks;
	}
}
