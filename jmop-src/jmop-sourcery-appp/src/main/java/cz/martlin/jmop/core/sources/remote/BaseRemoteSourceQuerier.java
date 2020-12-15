package cz.martlin.jmop.core.sources.remote;

import java.net.URL;
import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.SimpleShortOperation;

public interface BaseRemoteSourceQuerier {
	public SimpleShortOperation<String, List<Track>> search(Bundle bundle, String query) throws JMOPMusicbaseException;

	public SimpleShortOperation<Track, Track> loadNext(Track track) throws JMOPMusicbaseException;

	public URL urlOfTrack(Track track);
	
	public URL urlOfSearchResult(String query);

}
