package cz.martlin.jmop.core.sources.local;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

public class DefaultPlaylistLoaderTest {

	@Test
	public void test() throws IOException {
		final File file = File.createTempFile("playlist-", ".xspf");
		System.out.println("Working with file " + file.getAbsolutePath());
		
		final Bundle bundle = new Bundle(SourceKind.YOUTUBE, "testing bundle");

		String playlistName = "testing playlist";
		SourceKind kind = SourceKind.YOUTUBE;
		
		Duration duration1 = DurationUtilities.createDuration(0, 0, 11);
		Duration duration2 = DurationUtilities.createDuration(0, 3, 15);
		Duration duration3 = DurationUtilities.createDuration(1, 50, 42);
		
		List<Track> tracks = Arrays.asList( //
				bundle.createTrack("123456", "foo", "Lorem ispum dolor sit amet.", duration1), //
				bundle.createTrack("aBcDeFg", "Nothing by Noone", "Just simply nothing.", duration2), //
				bundle.createTrack("xy42+99z", "Silence!", "24 hours of awesome silence.", duration3) //
		);
		
		Tracklist tracklist = new Tracklist(tracks);
		final PlaylistFileData inputData = new PlaylistFileData(bundle.getName(), playlistName, kind, tracklist);

		DefaultPlaylistLoader loader = new DefaultPlaylistLoader();

		loader.save(inputData, file);

		PlaylistFileData outputData = loader.load(bundle, file, false);

		System.out.println(outputData);
		
		assertEquals(outputData.toString(), inputData.toString());
		assertEquals(outputData, inputData);
		
		// second try
		
		final File secondFile = File.createTempFile("second-playlist-", ".xspf");
		System.out.println("And with " + file.getAbsolutePath());
		
		loader.save(outputData, secondFile);
		PlaylistFileData anotherData = loader.load(bundle, secondFile, false);
		
		assertEquals(inputData, anotherData);
		

		
	}

}
