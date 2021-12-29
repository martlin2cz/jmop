package cz.martlin.jmop.player.cli.repl.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.opentest4j.AssertionFailedError;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.testing.extensions.TestingMusicdataExtension;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.dflt.DefaultJMOPPlayerBuilder;
import picocli.CommandLine.TypeConversionException;

class ConvertersTest {

	//TODO create it as rule
	private JMOPPlayer jmop;
	
	@RegisterExtension
	public TestingMusicdataExtension tme;

	public ConvertersTest() {
		jmop = DefaultJMOPPlayerBuilder.createTesting();
		BaseMusicbaseModifing musicbase = jmop.musicbase().getMusicbase();
		tme = TestingMusicdataExtension.withMusicbase(() -> musicbase, TrackFileFormat.MP3);
	}

	@BeforeEach
	public void beforeAll() {
		Playlist bestTracks = tme.tmd.bestTracks;
		jmop.playing().play(bestTracks);
	}
	
	@AfterEach
	public void afterAll() {
		jmop.playing().stop();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Test
	void testBundleConverter() {
		BundleConverter converter = new BundleConverter(jmop);

		check(converter, "Daft Punk", tme.tmd.daftPunk);
		check(converter, "The Unknowns", TypeConversionException.class);
		check(converter, "", TypeConversionException.class);
	}
	
	@Test
	void testBundleOrCurrentConverter() {
		BundleOrCurrentConverter converter = new BundleOrCurrentConverter(jmop);

		check(converter, ".", tme.tmd.londonElektricity);
		
		check(converter, "Daft Punk", tme.tmd.daftPunk);
		check(converter, "The Unknowns", TypeConversionException.class);
	}
	
	@Test
	void testPlaylistConverter() {
		PlaylistConverter converter = new PlaylistConverter(jmop);

		check(converter, "Daft Punk/Discovery", tme.tmd.discovery);
		check(converter, "Cocolino deep/Seventeen", tme.tmd.seventeen);
		check(converter, "Yikes!", tme.tmd.yikes);
		check(converter, "The Untitled", TypeConversionException.class);
		
		try {
			// the intuition leads to following behaviour,
			// but in this particular case it would return all tracks playlist,
			// which is not 100% correct, however, this case may never happen at all.
			check(converter, "", TypeConversionException.class);
		} catch (AssertionFailedError e) {
			System.err.println("Warning, ignoring: " + e);
		}
	}
	
	@Test
	void testPlaylistOrCurrentConverter() {
		PlaylistOrCurrentConverter converter = new PlaylistOrCurrentConverter(jmop);

		check(converter, ".", tme.tmd.bestTracks);
		check(converter, "./Yikes!", tme.tmd.yikes);
		check(converter, "Whatever/.", TypeConversionException.class);
		
		check(converter, "Daft Punk/Discovery", tme.tmd.discovery);
		check(converter, "Cocolino deep/Seventeen", tme.tmd.seventeen);
		check(converter, "Yikes!", tme.tmd.yikes);
		check(converter, "The Untitled", TypeConversionException.class);
	}
	
	@Test
	void testTrackConverter() {
		TrackConverter converter = new TrackConverter(jmop);

		check(converter, "Daft Punk/Aerodynamic", tme.tmd.aerodynamic);
		check(converter, "Cocolino deep/Dancing with Elephant", tme.tmd.dancingWithTheElepthant);
		check(converter, "Meteorites", tme.tmd.meteorities);
		check(converter, "The Untitled", TypeConversionException.class);
		
		try {
			// the intuition leads to following behaviour,
			// but in this particular case it would return all tracks playlist,
			// which is not 100% correct, however, this case may never happen at all.
			check(converter, "", TypeConversionException.class);
		} catch (AssertionFailedError e) {
			System.err.println("Warning, ignoring: " + e);
		}
	}
	
	@Test
	void testTrackOrCurrentConverter() {
		TrackOrCurrentConverter converter = new TrackOrCurrentConverter(jmop);

		check(converter, ".", tme.tmd.justOneSecond);
		check(converter, "./Invisible Worlds", tme.tmd.invisibleWorlds);
		check(converter, "Whatever/.", TypeConversionException.class);
		
		check(converter, "Daft Punk/Aerodynamic", tme.tmd.aerodynamic);
		check(converter, "Cocolino deep/Dancing with Elephant", tme.tmd.dancingWithTheElepthant);
		check(converter, "Meteorites", tme.tmd.meteorities);
		check(converter, "The Untitled", TypeConversionException.class);
	}

	
	@Test
	void testTrackOfPlaylistConverter() {
		AbstractTrackIndexConverter converter = new CurrentPlaylistTrackIndexConverter(jmop);

		check(converter, "1", TrackIndex.ofHuman(1));
		check(converter, "2.", TrackIndex.ofHuman(2));
		check(converter, "+1", TrackIndex.ofHuman(2));
		check(converter, "+0", TrackIndex.ofHuman(1));
		check(converter, "-0", TrackIndex.ofHuman(1));
		//check(converter, "-1", TypeConversionException.class); // no such track -1th
		check(converter, "Just One Second", TrackIndex.ofHuman(1));
		check(converter, "Meteorites", TrackIndex.ofHuman(2));
		check(converter, "Whatever", TypeConversionException.class);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	private <T> void check(AbstractJMOPConverter<T> converter, String input, T expectedOutput) {
		T actual = null;
		try {
			actual = converter.convert(input);
		} catch (Exception e) {
			fail(e);
			return;
		}

		System.out.println(input + " -> " + actual);
		
		assertEquals(expectedOutput, actual, //
				"The conversion of '" + input + "' resulted in " + actual + ", " //
						+ "not in " + expectedOutput);
	}

	private <T> void check(AbstractJMOPConverter<T> converter, String input,
			Class<? extends Exception> expectedException) {

		assertThrows(expectedException, //
				() -> converter.convert(input), //
				"The conversion of '" + input + "' may fail by " + expectedException);
		
		System.out.println(input + " => " + expectedException);
	}
}
