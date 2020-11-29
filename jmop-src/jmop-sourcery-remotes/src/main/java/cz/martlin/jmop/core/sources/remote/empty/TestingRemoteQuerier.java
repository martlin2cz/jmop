package cz.martlin.jmop.core.sources.remote.empty;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Metadata;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.remote.AbstractRemoteQuerier;
import javafx.util.Duration;

public class TestingRemoteQuerier extends AbstractRemoteQuerier {
	private Bundle NOPE_BUNDLE = new Bundle(null, null, (Metadata) null);

	private final BaseConfiguration config;
	private final List<Track> tracksData;
	private final Random rand;

	public TestingRemoteQuerier(BaseConfiguration config, int seed) {
		this.config = config;
		this.tracksData = new ArrayList<>();
		this.rand = new Random(seed);
	}

	public TestingRemoteQuerier add(String identifier, String title, String description, int minutes, int seconds) {
		Duration duration = DurationUtilities.createDuration(0, minutes, seconds);

		Metadata metadata = Metadata.createNew();
		Track track = NOPE_BUNDLE.createTrack(identifier, title, description, duration, metadata);
		tracksData.add(track);

		return this;
	}

	private Track pick() {
		int index = rand.nextInt(tracksData.size());
		return tracksData.get(index);
	}

	private Track renderTrack(Bundle bundle, Track data) {
		return bundle.createTrack( //
				data.getIdentifier(), //
				data.getTitle(), //
				data.getTitle(), //
				data.getDuration(), //
				data.getMetadata());
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<Track> runSearch(Bundle bundle, String query) throws JMOPSourceException {
		List<Track> result = new ArrayList<Track>(config.getSearchCount());

		for (int i = 0; i < config.getSearchCount(); i++) {
			Track trackData = pick();
			Track track = renderTrack(bundle, trackData);
			result.add(track);
		}

		return result;
	}

	@Override
	public Track runLoadNext(Track track) throws JMOPSourceException {
		Bundle bundle = track.getBundle();

		Track nextData = pick();
		Track next = renderTrack(bundle, nextData);

		return next;
	}

	@Override
	protected String createUrlOfSearchResult(String query) {
		return "http://localhost/?query=" + query;
	}

	@Override
	protected String createUrlOfTrack(Track track) {
		return "http://localhost/?track=" + track.getIdentifier();
	}

}
