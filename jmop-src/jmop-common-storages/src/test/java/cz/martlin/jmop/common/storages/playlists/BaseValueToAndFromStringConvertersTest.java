package cz.martlin.jmop.common.storages.playlists;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.conv.BaseValueToAndFromStringConverters;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

abstract class BaseValueToAndFromStringConvertersTest {

	private BaseValueToAndFromStringConverters converters;

	@BeforeEach
	void setUp() throws Exception {
		converters = obtainConverters();
	}

	protected abstract BaseValueToAndFromStringConverters obtainConverters();

	@AfterEach
	void tearDown() throws Exception {
		converters = null;
	}

	@Test
	void testTrackIndex() throws Exception {
		TrackIndex input = TrackIndex.ofHuman(42);
		String text = converters.trackIndexToTextMapper().valueOrNullToText(input);

		TrackIndex output = converters.textToTrackIndexMapper().textOrNullToValue(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	@Test
	void testNullTrackIndex() throws Exception {
		TrackIndex input = null;
		String text = converters.trackIndexToTextMapper().valueOrNullToText(input);

		TrackIndex output = converters.textToTrackIndexMapper().textOrNullToValue(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}

	@Test
	void testDate() throws Exception {
		LocalDateTime input = LocalDateTime.now().withNano(0);
		String text = converters.dateToTextMapper().valueOrNullToText(input);

		LocalDateTime output = converters.textToDateMapper().textOrNullToValue(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	@Test
	void testNullDate() throws Exception {
		LocalDateTime input = null;
		String text = converters.dateToTextMapper().valueOrNullToText(input);

		LocalDateTime output = converters.textToDateMapper().textOrNullToValue(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	
	@Test
	void testDuration() throws Exception {
		Duration input = DurationUtilities.createDuration(1, 59, 42);
		String text = converters.durationToTextMapper().valueOrNullToText(input);

		Duration output = converters.textToDurationMapper().textOrNullToValue(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	@Test
	void testNullDuration() throws Exception {
		Duration input = null;
		String text = converters.durationToTextMapper().valueOrNullToText(input);

		Duration output = converters.textToDurationMapper().textOrNullToValue(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	@Test
	void testNumber() throws Exception {
		int input = 42;
		String text = converters.numberToTextMapper().valueOrNullToText(input);

		int output = converters.textToNumberMapper().textOrNullToValue(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	

}
