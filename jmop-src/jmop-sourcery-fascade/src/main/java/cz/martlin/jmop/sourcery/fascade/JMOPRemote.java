package cz.martlin.jmop.sourcery.fascade;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSource;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;
import cz.martlin.jmop.sourcery.engine.NewTrackAdder;
import cz.martlin.jmop.sourcery.engine.TrackFileDownloader;

public class JMOPRemote {
	
	private final NewTrackAdder adder;
	//TODO all the remaining remote-based actions performers
	private final TrackFileDownloader downloader;
	
	public JMOPRemote(BaseRemoteSource remote, BaseMusicbase musicbase) {
		this.adder = new NewTrackAdder(remote, musicbase);
		this.downloader = new TrackFileDownloader(remote, musicbase);
	}

	public Track add(Bundle bundle, String query, boolean download) throws JMOPSourceryException {
		return adder.add(bundle, query, download);
	}

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

	public void download(Track track) throws JMOPSourceryException {
		downloader.downloadAndSetFile(track);
	}

	public void downloadToFile(Track track) throws JMOPSourceryException {
		downloader.downloadToFile(track);
	}
	
	//TODO all the remaining remote-based actions

}
