package cz.martlin.jmop.core.sources.local;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.sources.SourceKind;

public class DefaultPlaylistLoaderTest {

	@Test
	public void test() throws IOException {
		final File file = File.createTempFile("playlist-", ".xspf");
		System.out.println("Working with file " + file.getAbsolutePath());
		
		final Bundle bundle = new Bundle(SourceKind.YOUTUBE, "testing bundle");

		String playlistName = "testing playlist";
		SourceKind kind = SourceKind.YOUTUBE;

		List<Track> tracks = Arrays.asList( //
				bundle.createTrack("123456", "foo", "Lorem ispum dolor sit amet."), //
				bundle.createTrack("aBcDeFg", "Nothing by Noone", "Just simply nothing."), //
				bundle.createTrack("xy42+99z", "Silence!", "24 hours of awesome silence.") //
		);
		
		Tracklist tracklist = new Tracklist(tracks);
		final PlaylistFileData inputData = new PlaylistFileData(playlistName, kind, tracklist);

		DefaultPlaylistLoader loader = new DefaultPlaylistLoader();

		loader.save(inputData, file);

		PlaylistFileData outputData = loader.load(bundle, file);

		System.out.println(outputData);
		
		assertEquals(outputData.toString(), inputData.toString());
		assertEquals(outputData, inputData);
		
		// second try
		
		final File secondFile = File.createTempFile("second-playlist-", ".xspf");
		System.out.println("And with " + file.getAbsolutePath());
		
		loader.save(outputData, secondFile);
		PlaylistFileData anotherData = loader.load(bundle, secondFile);
		
		assertEquals(inputData, anotherData);
		

		
	}

}
