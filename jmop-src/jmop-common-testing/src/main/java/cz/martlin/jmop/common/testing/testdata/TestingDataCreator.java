package cz.martlin.jmop.common.testing.testdata;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

/**
 * @author martin
 *
 */
public class TestingDataCreator {

	@Deprecated
	public static final String FOO_TRACK_ID = "1234";
	@Deprecated
	public static final String BAR_TRACK_ID = "9999";

	private TestingDataCreator() {
	}

	public static Bundle bundle(BaseMusicbaseModifing musicbase) {
		return doCreateEmptyBundle(musicbase, "FooBundle");
	}

	public static Bundle bundle(BaseMusicbaseModifing musicbase, String name) {
		return doCreateEmptyBundle(musicbase, name);
	}

	public static Playlist playlist(BaseMusicbaseModifing musicbase, Bundle bundle) {
		return doCreateEmptyPlaylist(musicbase, bundle, "lorem-bundle");
	}

	public static Playlist playlist(BaseMusicbaseModifing musicbase, Bundle bundle, String name) {
		return doCreateEmptyPlaylist(musicbase, bundle, name);
	}

	public static Track track(BaseMusicbaseModifing musicbase, Bundle bundle) {
		String id = "hello id";
		String description = "the description of the hello";
		Duration duration = DurationUtilities.createDuration(0, 2, 10);
		return doCreateTrack(musicbase, bundle, "hello track", description, id, duration, false);
	}

	public static Track track(BaseMusicbaseModifing musicbase, Bundle bundle, String title, boolean fileExisting) {
		String id = "id of " + title;
		String description = "description of " + title;
		Duration duration = DurationUtilities.createDuration(0, 3, 15);
		return doCreateTrack(musicbase, bundle, title, description, id, duration, fileExisting);
	}

	public static Track track(BaseMusicbaseModifing musicbase, Bundle bundle, String title, String description,
			String id, Duration duration, boolean fileExisting) {
		return doCreateTrack(musicbase, bundle, title, description, id, duration, fileExisting);
	}

	///////////////////////////////////////////////////////////////////////////

	private static Bundle doCreateEmptyBundle(BaseMusicbaseModifing musicbase, String name) {
		if (musicbase != null) {
			return musicbase.createNewBundle(name);
		} else {
			return new Bundle(name, Metadata.createNew());
		}
	}

	private static Playlist doCreateEmptyPlaylist(BaseMusicbaseModifing musicbase, Bundle bundle, String name) {
		if (musicbase != null) {
			return musicbase.createNewPlaylist(bundle, name);
		} else {
			return new Playlist(bundle, name, Metadata.createNew());
		}
	}

	private static Track doCreateTrack(BaseMusicbaseModifing musicbase, Bundle bundle, String title, String description,
			String id, Duration duration, boolean fileExisting) {
			TrackData data = new TrackData(id, title, description, duration);
			File trackFile;
			try {
				trackFile = fileExisting ? File.createTempFile(title, ".music") : null;
			} catch (IOException e) {
				throw new JMOPRuntimeException(e);
			}
			
			if (musicbase != null) {
				return musicbase.createNewTrack(bundle, data, TrackFileCreationWay.COPY_FILE, trackFile);
			} else {
				return new Track(bundle, id, title, description, duration, trackFile, Metadata.createNew());
			}
	}



	@Deprecated
	public static Bundle createEmptyTestingBundle() {
		String name = "box";
		SourceKind kind = SourceKind.YOUTUBE;
		Metadata metadata = metadata(5, 2, 2, 29, 9);
		Bundle bundle = new Bundle(kind, name, metadata);
		return bundle;
	}

	//////////////////////////////////////////////////////////////////////////////////////

	public static Metadata metadata(int numberOfPlays, int createdDay, int createdMonth, int lastPlayedDay,
			int lastPlayedMonth) {
		Calendar lastPlayed = datetime(lastPlayedDay, lastPlayedMonth);
		Calendar created = datetime(createdDay, createdMonth);
		Metadata metadata = Metadata.createExisting(created, lastPlayed, numberOfPlays);
		return metadata;
	}

	public static Calendar datetime(int dayAndMinute, int monthAndHour) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(2019, monthAndHour, dayAndMinute, monthAndHour, dayAndMinute, 30);

		return calendar;
	}

}
