package cz.martlin.jmop.core.misc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javafx.util.Duration;

public class DurationUtilitiesTest {

	private static final double DELTA = 0;

	@Test 
	public void testCreateDurarion() {
		Duration first = DurationUtilities.createDuration(0, 0, 60);
		
		assertEquals(1.0 / 60.0, first.toHours(), DELTA);
		assertEquals(1.0, first.toMinutes(), DELTA);
		assertEquals(60.0, first.toSeconds(), DELTA);
		
		Duration second = DurationUtilities.createDuration(1, 30, 0);
		
		assertEquals(1.5, second.toHours(), DELTA);
		assertEquals(90, second.toMinutes(), DELTA);
		
	}
	
	@Test
	public void testParseDuration() {
		Duration expectedShort = DurationUtilities.createDuration(0, 0, 5);
		Duration actualShort = DurationUtilities.parseYoutubeDuration("PT5S");
		
		assertEquals(expectedShort, actualShort);
		
		Duration expectedMiddle = DurationUtilities.createDuration(0, 4, 5);
		Duration actualMiddle = DurationUtilities.parseYoutubeDuration("PT4M5S");
		
		assertEquals(expectedMiddle, actualMiddle);
		
		Duration expectedLong = DurationUtilities.createDuration(1, 50, 42);
		Duration actualLong = DurationUtilities.parseYoutubeDuration("PT1H50M42S");
		
		assertEquals(expectedLong, actualLong);
	}

	@Test
	public void testToHumanString() {
		Duration inputShort = DurationUtilities.createDuration(0, 0, 5);
		assertEquals("0:0:5", DurationUtilities.toHumanString(inputShort));
		
		Duration inputMiddle = DurationUtilities.createDuration(0, 11, 12);
		assertEquals("0:11:12", DurationUtilities.toHumanString(inputMiddle));
		
		Duration inputLong = DurationUtilities.createDuration(99, 1, 1);
		assertEquals("99:1:1", DurationUtilities.toHumanString(inputLong));
		
		Duration inputMega = DurationUtilities.createDuration(1, 58, 33);
		assertEquals("1:58:33", DurationUtilities.toHumanString(inputMega));
		
	}
	
}
