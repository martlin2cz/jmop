package cz.martlin.jmop.core.sources.remote.youtube;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;

@Tag(value = "IDE_ONLY")
public class YoutubeQuerierTest {

	public static void main(String[] args) throws IOException {
		new YoutubeQuerierTest().test();
	}
	
	public class DefaultRemotesConfiguration implements BaseRemotesConfiguration {

		@Override
		public int getSearchCount() {
			return 5;
		}

	}

	@Test
	public void test() throws IOException {
		BaseRemotesConfiguration config = new DefaultRemotesConfiguration();

		BaseRemoteSourceQuerier querier = new YoutubeQuerier(config);

		String query = "sample testing sound";
		List<TrackData> searched = querier.search(query);

		assertFalse(searched.isEmpty());
		TrackData firstTrack = searched.get(0);
		TrackData secondTrack = searched.get(1);

		System.out.println("First track: " + firstTrack);
		System.out.println("Second track: " + secondTrack);

		Bundle bundle = null; //not needed now
		Track track = new Track(bundle, firstTrack.getIdentifier(), firstTrack.getTitle(), firstTrack.getDescription(),
				firstTrack.getDuration(), null, Metadata.createNew());
		
		TrackData next = querier.loadNext(track);
		assertNotNull(next);

		System.out.println("Next track: " + next);
	}

}
