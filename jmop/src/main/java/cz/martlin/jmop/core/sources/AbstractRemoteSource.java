package cz.martlin.jmop.core.sources;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.stream.StreamSeed;
import cz.martlin.jmop.core.tracks.Track;

public abstract class AbstractRemoteSource<RQT, RST> implements BaseSourceImpl {

	public AbstractRemoteSource() {
	}
	
	public Track getInitialTrack(StreamSeed seed) throws JMOPSourceException {
		RQT request = createGetInitialTrackRequest(seed);
		RST response = invokeRequest(request);

		return inferTrack(response);
	}

	public Track getNextTrack(Track track) throws JMOPSourceException {
		RQT request = createGetNextTrackRequest(track);
		RST response = invokeRequest(request);

		return inferTrack(response);
	}

	public abstract RQT createGetInitialTrackRequest(StreamSeed seed);

	public abstract RQT createGetNextTrackRequest(Track track);

	public abstract RST invokeRequest(RQT request);

	public abstract Track inferTrack(RST response);

}
