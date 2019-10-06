package cz.martlin.jmop.core.sources.locals.testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.TestingDataCreator;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public class TestingTracksSourceTest {

	TestingTracksSource tracks;

	public TestingTracksSourceTest() {
		super();
	}

	@Before
	public void before() {
		tracks = new TestingTracksSource();
	}

	@After
	public void after() throws JMOPSourceException {
		tracks.cleanup();
	}

	@Test
	public void test() throws JMOPSourceException {
		TestingTracksSource tracks = new TestingTracksSource();

		Bundle bundle = TestingDataCreator.createTestingBundle();
		Track fooTrack = bundle.getTrack("1234");

		File fileFooWavCache = tracks.fileOfTrack(fooTrack, TrackFileLocation.CACHE, TrackFileFormat.WAV);
		System.out.println("The file is: " + fileFooWavCache.getAbsolutePath());

		assertFalse(tracks.exists(fooTrack, TrackFileLocation.CACHE, TrackFileFormat.WAV));
		tracks.create(fooTrack, TrackFileLocation.CACHE, TrackFileFormat.WAV);
		assertTrue(tracks.exists(fooTrack, TrackFileLocation.CACHE, TrackFileFormat.WAV));
		tracks.deleteIfExists(fooTrack, TrackFileLocation.CACHE, TrackFileFormat.WAV);
		assertFalse(tracks.exists(fooTrack, TrackFileLocation.CACHE, TrackFileFormat.WAV));
		
		//TODO test move
	}

}
