package cz.martlin.jmop.sourcery.engine;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.sourcery.remote.BaseDownloader;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSource;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.sourcery.remote.JMOPSourceryException;

/**
 * Component performing download of track(s).
 * 
 * @author martin
 *
 */
public class TrackFileDownloader {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	/**
	 * The querier.
	 */
	private final BaseRemoteSourceQuerier querier;
	/**
	 * The downloader.
	 */
	private final BaseDownloader downloader;

	/**
	 * The musicbase.
	 */
	private final BaseMusicbaseModifing musicbaseModifing;

	public TrackFileDownloader(BaseRemoteSource remote, BaseMusicbaseModifing musicbaseModifing) {
		super();
		this.querier = remote.querier();
		this.downloader = remote.downloader();
		this.musicbaseModifing = musicbaseModifing;
	}

	public TrackFileDownloader(BaseRemoteSourceQuerier querier, BaseDownloader downloader,
			BaseMusicbaseModifing musicbaseModifing) {
		super();
		this.querier = querier;
		this.downloader = downloader;
		this.musicbaseModifing = musicbaseModifing;
	}

	/**
	 * Downloads the given (already musicbase track) and updates its track file into
	 * the downloaded file.
	 * 
	 * @param track
	 * @throws JMOPSourceryException
	 */
	public void downloadAndSetFile(Track track) throws JMOPSourceryException {
		LOG.info("Will add and download track file for {} (and set the file)", track.getTitle());

		File file = createTemporaryFile(track);
		String url = obtainURL(track);

		downloader.download(url, file);
		musicbaseModifing.specifyTrackFile(track, TrackFileCreationWay.MOVE_FILE, file);
	}

	/**
	 * Downloads the given track (Already musicbase track) and moves the downloaded
	 * track file into one specified in the track.
	 * 
	 * @param track
	 * @throws JMOPSourceryException
	 */
	public void downloadToFile(Track track) throws JMOPSourceryException {
		LOG.info("Will add and download track file for {} into the specified track file", track.getTitle());

		File file = track.getFile();
		String url = obtainURL(track);

		downloader.download(url, file);
		musicbaseModifing.specifyTrackFile(track, TrackFileCreationWay.MOVE_FILE, file);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Obtains the download url of the track.
	 * 
	 * @param track
	 * @return
	 * @throws JMOPSourceryException
	 */
	private String obtainURL(Track track) throws JMOPSourceryException {
		String title = track.getTitle();

		if (track.getSource() != null) {
			LOG.debug("Using track {} existing source url: {}", title, track.getSource());
			return track.getSource().toASCIIString();
		}

		LOG.debug("Searching track {} to pick the remote URL", title);
		List<TrackData> searchedTracks = querier.search(title);

		TrackData searchedTrack = searchedTracks.get(0);

		URI trackUri = searchedTrack.getURI();
		String urlStr = trackUri.toASCIIString();

		LOG.debug("The track {}'s corresponding URL is {}", title, urlStr);
		return urlStr;
	}

	/**
	 * Creates the temporary donwload file.
	 * 
	 * @param track
	 * @return
	 * @throws JMOPSourceryException
	 */
	private File createTemporaryFile(Track track) throws JMOPSourceryException {
		try {
			String extension = downloader.downloadFormat().fileExtension();
			File file = File.createTempFile("jmop-" + track.getTitle() + "-", "." + extension);
			Files.delete(file.toPath());

			LOG.debug("Created temporary download file {}", file);
			return file;
		} catch (IOException e) {
			throw new JMOPSourceryException("Could not create temporary download file", e);
		}
	}

}