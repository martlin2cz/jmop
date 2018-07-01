package cz.martlin.jmop.core.player;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public class PlaylistTest {

	@Test
	public void test() {
		final Track trackFoo = new Track(new TrackIdentifier(SourceKind.YOUTUBE, "foo"), "Foo", "foo bar");
		final Track trackBar = new Track(new TrackIdentifier(SourceKind.YOUTUBE, "bar"), "Bar", "bar baz");
		final Track trackBaz = new Track(new TrackIdentifier(SourceKind.YOUTUBE, "baz"), "Baz", "baz aux");
		final Track trackAux = new Track(new TrackIdentifier(SourceKind.YOUTUBE, "aux"), "Aux", "aux qux");
		final Track trackQux = new Track(new TrackIdentifier(SourceKind.YOUTUBE, "Qux"), "Qux", "qux qux");
		
		List<Track> tracks = Arrays.asList(trackFoo, trackBar, trackBaz);
		BasicPlaylist p = new BasicPlaylist(tracks);
		
		assertEquals(3, p.getRemaining().size());
		
		p.append(trackAux);
		p.append(trackQux);
		
		assertEquals(5, p.getRemaining().size());
		
		//TODO test Playlist
	}

}
