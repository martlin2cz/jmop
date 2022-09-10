package cz.martlin.jmop.common.testing.testdata;

import java.net.URI;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

/**
 * The testing musicdata preparing the data just as a data objects, in no
 * storage or musicbase.
 * 
 * @author martin
 *
 */
public class SimpleTestingMusicdata extends AbstractTestingMusicdata {

	public SimpleTestingMusicdata() {
		super();
		
		prepare();
	}
	
	public SimpleTestingMusicdata(TrackFileFormat trackFileOrNot) {
		super(trackFileOrNot);
		
		prepare();
	}
	
	@Override
	protected Bundle createTheBundle(String name) {
		return TestingDataCreator.bundle(null, name);
	}

	@Override
	protected Playlist createThePlaylist(Bundle bundle, String name) {
		return TestingDataCreator.playlist(null, bundle, name);
	}

	@Override
	protected Track createTheTrack(Bundle bundle, String title, String description, Duration duration, TrackFileFormat trackFileOrNot,
			URI uri) {

		return TestingDataCreator.track(null, bundle, title, description, duration, uri, trackFileOrNot);
	}

	@Override
	protected Track deleteTheTrack(Track track) {
		return null;
	}

	@Override
	protected Playlist deleteThePlaylist(Playlist playlist) {
		return null;
	}

	@Override
	protected Bundle deleteTheBundle(Bundle bundle) {
		return null;
	}

}
