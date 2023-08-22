package cz.martlin.jmop.core.sources.remote.empty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteQuerier;
import cz.martlin.jmop.sourcery.remote.BaseRemotesConfiguration;
import cz.martlin.jmop.sourcery.remote.JMOPSourceryException;
import javafx.util.Duration;

/**
 * The testing qiuerier. Specify the track datas and it then randomly picks
 * some.
 * 
 * @author martin
 *
 */
public class TestingRemoteQuerier extends AbstractRemoteQuerier {
	/**
	 * The configuration.
	 * 
	 */
	private final BaseRemotesConfiguration config;
	/**
	 * The pool of tracks to pick the resul from.
	 */
	private final List<TrackData> tracksData;
	private final Random rand;

	public TestingRemoteQuerier(BaseRemotesConfiguration config, int seed) {
		this.config = config;
		this.tracksData = new ArrayList<>();
		this.rand = new Random(seed);
	}

	/**
	 * Appends new testing data.
	 * 
	 * @param identifier
	 * @param title
	 * @param description
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public TestingRemoteQuerier add(String identifier, String title, String description, int minutes, int seconds) {
		Duration duration = DurationUtilities.createDuration(0, minutes, seconds);

		TrackData trackData = new TrackData(title, description, duration, null, null);
		tracksData.add(trackData);

		return this;
	}

	/**
	 * Picks random track from the pool.
	 * 
	 * @return
	 */
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
