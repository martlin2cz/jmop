package cz.martlin.jmop.core.player;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.SourceKind;

public class PlaylistTest {

	@Test
	public void test() {
		Bundle bundle = new Bundle(SourceKind.YOUTUBE, "testing bundle");
		final Track trackFoo = new Track(bundle, "foo", "Foo", "foo bar");
		final Track trackBar = new Track(bundle, "bar", "Bar", "bar baz");
		final Track trackBaz = new Track(bundle, "baz", "Baz", "baz aux");
		final Track trackAux = new Track(bundle, "aux", "Aux", "aux qux");
		final Track trackQux = new Track(bundle, "Qux", "Qux", "qux qux");
		
		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz);
		BasicPlaylistRuntime p = new BasicPlaylistRuntime(tracks);
		
		assertEquals(3, p.getRemaining().size());
		
		p.append(trackAux);
		p.append(trackQux);
		
		assertEquals(5, p.getRemaining().size());
		
		//TODO test Playlist
	}

}
