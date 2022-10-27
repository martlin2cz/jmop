package cz.martlin.jmop.sourcery.engine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.sourcery.local.BaseTracksImpoter;

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
	private final BaseTracksImpoter fileOrDirImporter;

	/**
	 * Creates.
	 * 
	 * @param musicbaseModifing
	 * @param filesFormat
	 * @param fileOrDirImpoter
	 */
	public TracksImporter(BaseMusicbaseModifing musicbaseModifing, TrackFileFormat filesFormat,
			BaseTracksImpoter fileOrDirImpoter) {
		super();
		this.musicbaseModifing = musicbaseModifing;
		this.filesFormat = filesFormat;
		this.fileOrDirImporter = fileOrDirImpoter;
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
	public List<Track> importFrom(File dirOrFile, Bundle toBundle, TrackFileCreationWay createFiles,
			boolean recursive) {
		LOGGER.info("Importing tracks from {}, recursive? {}, format: {}", //
				dirOrFile.getAbsolutePath(), recursive, filesFormat);

		List<TrackData> datas = fileOrDirImporter.importTracks(dirOrFile, recursive, filesFormat);

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
