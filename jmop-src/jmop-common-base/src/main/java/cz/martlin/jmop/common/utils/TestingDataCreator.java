package cz.martlin.jmop.common.utils;

import java.util.Arrays;
import java.util.Calendar;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

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
		return doCreateTrack(musicbase, bundle, "hello track", description, id, duration);
	}

	public static Track track(BaseMusicbaseModifing musicbase, Bundle bundle, String title) {
		String id = "id of " + title;
		String description = "description of " + title;
		Duration duration = DurationUtilities.createDuration(0, 3, 15);
		return doCreateTrack(musicbase, bundle, title, description, id, duration);
	}

	public static Track track(BaseMusicbaseModifing musicbase, Bundle bundle, String title, String description,
			String id, Duration duration) {
		return doCreateTrack(musicbase, bundle, title, description, id, duration);
	}

	///////////////////////////////////////////////////////////////////////////

	private static Bundle doCreateEmptyBundle(BaseMusicbaseModifing musicbase, String name) {
		try {
			return musicbase.createNewBundle(name);
		} catch (JMOPMusicbaseException e) {
			throw new RuntimeException(e);
		}
	}

	private static Playlist doCreateEmptyPlaylist(BaseMusicbaseModifing musicbase, Bundle bundle, String name) {
		try {
			return musicbase.createNewPlaylist(bundle, name);
		} catch (JMOPMusicbaseException e) {
			throw new RuntimeException(e);
		}
	}

	private static Track doCreateTrack(BaseMusicbaseModifing musicbase, Bundle bundle, String title, String description,
			String id, Duration duration) {
		try {
			TrackData data = new TrackData(id, title, description, duration);
			return musicbase.createNewTrack(bundle, data);
		} catch (JMOPMusicbaseException e) {
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	public static Playlist createTestingPlaylist(Bundle bundle) {
		return createTestingPlaylist(bundle, "queue");
	}

	@Deprecated
	public static Playlist createTestingPlaylist(Bundle bundle, String name) {
		Track foo = bundle.createTrack(FOO_TRACK_ID, "foo", "Lorem Ipsum", //
				DurationUtilities.createDuration(0, 3, 15), //
				metadata(3, 3, 3, 29, 9)); //

		Track bar = bundle.createTrack(BAR_TRACK_ID, "bar", "Karel Franta Pepa", //
				DurationUtilities.createDuration(0, 4, 59), //
				metadata(2, 11, 21, 11, 23)); //

		Playlist playlist = bundle.createPlaylist(name, 1, true, //
				metadata(3, 3, 3, 3, 3), //
				new Tracklist(Arrays.asList(foo, bar)));

		return playlist;
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
