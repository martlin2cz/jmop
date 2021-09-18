package cz.martlin.jmop.core.sources.remote.youtube;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.misc.ops.BaseShortOperation;
import cz.martlin.jmop.core.operation.PrintingListener;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;

public class YoutubeQuerierTest {

	@Test
	public void test() throws Exception {
		BaseConfiguration config = new ConstantConfiguration();
		InternetConnectionStatus connection = new InternetConnectionStatus(config);

		BaseRemoteSourceQuerier querier = new YoutubeQuerier(config, connection);

		Bundle bundle = new Bundle(SourceKind.YOUTUBE, "bundle");
		String query = "sample testing sound";

		assertEquals("https://www.youtube.com/results?search_query=sample+testing+sound", //
				querier.urlOfSearchResult(query).toExternalForm());

		BaseProgressListener listener = new PrintingListener(System.out);

		BaseShortOperation<String, List<Track>> searchOperation = querier.search(bundle, query);
		List<Track> tracks = searchOperation.run(listener);

		assertFalse(tracks.isEmpty());
		Track firstTrack = tracks.get(0);
		Track secondTrack = tracks.get(1);

		System.out.println("First track: " + firstTrack);
		System.out.println("Second track: " + secondTrack);

		assertEquals("https://www.youtube.com/watch?v=" + firstTrack.getIdentifier(), //
				querier.urlOfTrack(firstTrack).toExternalForm());

		BaseShortOperation<Track, Track> nextOperation = querier.loadNext(firstTrack);
		Track nextTrack = nextOperation.run(listener);
		assertNotNull(nextTrack);

		System.out.println("Next track: " + nextTrack);
	}

}
