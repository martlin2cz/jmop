package cz.martlin.jmop.common.testing.testdata;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
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
	
	@Override
	protected Bundle createTheBundle(String name) {
		return TestingDataCreator.bundle(null, name);
	}

	@Override
	protected Playlist createThePlaylist(Bundle bundle, String name) {
		return TestingDataCreator.playlist(null, bundle, name);
	}

	@Override
	protected Track createTheTrack(Bundle bundle, String title, String description, String id, Duration duration,
			boolean fileExisting) {

		return TestingDataCreator.track(null, bundle, title, description, id, duration, false);
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
