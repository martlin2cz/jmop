package cz.martlin.jmop.sourcery.fascade;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.sourcery.engine.NewTrackAdder;
import cz.martlin.jmop.sourcery.engine.TrackFileDownloader;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSource;
import cz.martlin.jmop.sourcery.remote.JMOPSourceryException;

/**
 * The jmop sourcery remotes fascade.
 * @author martin
 *
 */
public class JMOPRemote {
	
	/**
	 * New tracks adder.
	 */
	private final NewTrackAdder adder;
	
	/**
	 * The downloader( ideally the generic one).
	 */
	//TODO all the remaining remote-based actions performers
	private final TrackFileDownloader downloader;
	
	/**
	 * Creates.
	 * 
	 * @param remote
	 * @param musicbase
	 */
	public JMOPRemote(BaseRemoteSource remote, BaseMusicbase musicbase) {
		this.adder = new NewTrackAdder(remote, musicbase);
		this.downloader = new TrackFileDownloader(remote, musicbase);
	}

	/**
	 * Adds the track by the searching.
	 * 
	 * @param bundle
	 * @param query
	 * @param download
	 * @return
	 * @throws JMOPSourceryException
	 */
	public Track add(Bundle bundle, String query, boolean download) throws JMOPSourceryException {
		return adder.add(bundle, query, download);
	}

	/**
	 * Adds the tracks by the given list of queries.
	 * 
	 * @param bundle
	 * @param queries
	 * @param download
	 * @return
	 * @throws JMOPSourceryException
	 */
	public List<Track> add(Bundle bundle, List<String> queries, boolean download) throws JMOPSourceryException {
		List<Track> result = new ArrayList<>(queries.size());
		
		JMOPSourceryException ex = null;
		for (String query : queries) {
			try {
				Track track = adder.add(bundle, query, download);
				result.add(track);
			} catch (Exception e) {
				if (ex == null) {
					ex = new JMOPSourceryException("One or more adds failed.");
					ex.addSuppressed(e);
				}
			} 
		}
		
		if (ex != null) {
			throw ex;
		}
		
		return result;
	}

	/**
	 * Downloads track file and sets the file to the track.
	 * 
	 * @param track
	 * @throws JMOPSourceryException
	 */
	public void download(Track track) throws JMOPSourceryException {
		downloader.downloadAndSetFile(track);
	}

	/**
	 * Downloads track file into the track's file.
	 * @param track
	 * @throws JMOPSourceryException
	 */
	public void downloadToFile(Track track) throws JMOPSourceryException {
		downloader.downloadToFile(track);
	}
	
	//TODO all the remaining remote-based actions

}
