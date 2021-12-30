package cz.martlin.jmop.sourcery.engine;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSource;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;

public class NewTrackAdder {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseRemoteSourceQuerier querier;
	private final BaseDownloader downloader;
	
	private final BaseMusicbaseModifing musicbase;

	public NewTrackAdder(BaseRemoteSourceQuerier querier, BaseDownloader downloader, BaseMusicbaseModifing musicbase) {
		super();
		this.querier = querier;
		this.downloader = downloader;
		this.musicbase = musicbase;
	}
	
	public NewTrackAdder(BaseRemoteSource remote, BaseMusicbaseModifing musicbase) {
		super();
		this.querier = remote.querier();
		this.downloader = remote.downloader();
		this.musicbase = musicbase;
	}

	public Track add(Bundle bundle, String query, boolean download) throws JMOPSourceryException {
		LOG.info("Will add track to bundle {} by searching {}", bundle.getName(), query);
		
		TrackData searchedTrack = search(bundle, query);

		File temporaryDownloadFile = null;
		if (download) {
			try {
				temporaryDownloadFile = download(searchedTrack);
			} catch (Exception e) {
				LOG.warn("Donwload failed, adding anyway", e);
			}
		}
	
		if (temporaryDownloadFile != null) {
			return createTrackWithFile(bundle, searchedTrack, temporaryDownloadFile);
		} else {
			return createTrackWithNoFile(bundle, searchedTrack);
		}
	}

	private TrackData search(Bundle bundle, String query) throws JMOPSourceryException {
		LOG.debug("Searching '{}'", query, bundle.getName());

		List<TrackData> searchedTracks = querier.search(query);
		TrackData searchedTrack = searchedTracks.get(0);

		LOG.debug("Choosen track: {}", searchedTrack);
		return searchedTrack;
	}

	private File createTemporaryFile(TrackData searchedTrack) throws JMOPSourceryException {
		try {
			String extension = downloader.downloadFormat().fileExtension();
			File file = File.createTempFile("jmop-" + searchedTrack.getTitle() + "-", "." + extension);
			Files.delete(file.toPath());
			
			return file;
		} catch (IOException e) {
			throw new JMOPSourceryException("Could not create temporary download file", e);
		}
	}

	private File download(TrackData searchedTrack) throws JMOPSourceryException {
		LOG.debug("Downloading the track file of {}", searchedTrack.getTitle());

		URL url = searchedTrack.getURL();
		File temporaryDownloadFile = createTemporaryFile(searchedTrack);
		String urlStr = url.toExternalForm();
		downloader.download(urlStr, temporaryDownloadFile);

		LOG.debug("Downloaded the track file of {} to {}", //
				searchedTrack.getTitle(), temporaryDownloadFile.getAbsolutePath());
		return temporaryDownloadFile;
	}

	private Track createTrackWithFile(Bundle bundle, TrackData searchedTrack, File temporaryDownloadFile) {
		LOG.debug("Creating the track: {}", searchedTrack);

		Track track = musicbase.createNewTrack( //
				bundle, searchedTrack, TrackFileCreationWay.MOVE_FILE, temporaryDownloadFile);

		LOG.debug("Created the track: {}", track);
		return track;
	}

	private Track createTrackWithNoFile(Bundle bundle, TrackData searchedTrack) {
		LOG.debug("Creating the track: {}", searchedTrack);

		Track track = musicbase.createNewTrack(bundle, searchedTrack, TrackFileCreationWay.NO_FILE, null);

		LOG.debug("Created the track: {}", track);
		return track;
	}

}
