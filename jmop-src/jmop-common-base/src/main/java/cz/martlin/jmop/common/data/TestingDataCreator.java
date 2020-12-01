package cz.martlin.jmop.common.data;

import java.util.Arrays;
import java.util.Calendar;

import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.SourceKind;

public class TestingDataCreator {

	public static final String FOO_TRACK_ID = "1234";
	public static final String BAR_TRACK_ID = "9999";

	private TestingDataCreator() {
	}

	public static Bundle createTestingBundle() {
		Bundle bundle = createEmptyTestingBundle();

		Track foo = bundle.createTrack(FOO_TRACK_ID, "foo", "Lorem Ipsum", //
				DurationUtilities.createDuration(0, 3, 15), //
				metadata(3, 3, 3, 29, 9)); //

		@SuppressWarnings("unused")
		Playlist allTracks = bundle.createPlaylist("all tracks", 4, false, //
				metadata(2, 2, 2, 2, 2), //
				new Tracklist(Arrays.asList(foo)));

		return bundle;
	}

	public static Playlist createTestingPlaylist(Bundle bundle) {
		return createTestingPlaylist(bundle, "queue");
	}
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
