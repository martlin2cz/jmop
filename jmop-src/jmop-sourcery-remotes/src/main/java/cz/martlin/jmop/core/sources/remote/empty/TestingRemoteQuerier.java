package cz.martlin.jmop.core.sources.remote.empty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteQuerier;
import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;
import javafx.util.Duration;

public class TestingRemoteQuerier extends AbstractRemoteQuerier {
	private final BaseRemotesConfiguration config;
	private final List<TrackData> tracksData;
	private final Random rand;

	public TestingRemoteQuerier(BaseRemotesConfiguration config, int seed) {
		this.config = config;
		this.tracksData = new ArrayList<>();
		this.rand = new Random(seed);
	}

	public TestingRemoteQuerier add(String identifier, String title, String description, int minutes, int seconds) {
		Duration duration = DurationUtilities.createDuration(0, minutes, seconds);

		TrackData trackData = new TrackData(identifier, title, description, duration);
		tracksData.add(trackData);

		return this;
	}

	private TrackData pick() {
		int index = rand.nextInt(tracksData.size());
		return tracksData.get(index);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<TrackData> search(String query) throws JMOPSourceryException {
		List<TrackData> result = new ArrayList<>(config.getSearchCount());

		for (int i = 0; i < config.getSearchCount(); i++) {
			TrackData trackData = pick();
			result.add(trackData);
		}

		return result;
	}

	@Override
	public TrackData loadNext(Track track) throws JMOPSourceryException {
		TrackData nextData = pick();
		
		return nextData;
	}

//	@Override
//	protected String createUrlOfSearchResult(String query) {
//		return "http://localhost/?query=" + query;
//	}

	@Override
	public String createUrlOfTrack(Track track) {
		return "http://localhost/?track=" + track.getSource();
	}

}
