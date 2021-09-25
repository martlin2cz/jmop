package cz.martlin.jmop.common.storages.playlists;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.misc.TrackIndex;
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
	void testTrackIndex() {
		TrackIndex input = TrackIndex.ofHuman(42);
		String text = converters.trackIndexToText(input);

		TrackIndex output = converters.textToTrackIndex(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	@Test
	void testNullTrackIndex() {
		TrackIndex input = null;
		String text = converters.trackIndexToText(input);

		TrackIndex output = converters.textToTrackIndex(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}

	@Test
	void testDate() {
		LocalDateTime input = LocalDateTime.now().withNano(0);
		String text = converters.dateToText(input);

		LocalDateTime output = converters.textToDate(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	@Test
	void testNullDate() {
		LocalDateTime input = null;
		String text = converters.dateToText(input);

		LocalDateTime output = converters.textToDate(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	
	@Test
	void testDuration() {
		Duration input = DurationUtilities.createDuration(1, 59, 42);
		String text = converters.durationToText(input);

		Duration output = converters.textToDuration(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	@Test
	void testNullDuration() {
		Duration input = null;
		String text = converters.durationToText(input);

		Duration output = converters.textToDuration(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	
	@Test
	void testNumber() {
		int input = 42;
		String text = converters.numberToText(input);

		int output = converters.textToNumber(text);
		System.out.println(input + " -> " + text + " -> " + output);
		
		assertEquals(input, output);
	}
	

}
