package cz.martlin.jmop.core.misc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import javafx.util.Duration;

public class DurationUtilitiesTest {

	private static final double DELTA = 0.001;

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
	public void testParseYoutubeDuration() {
		Duration expectedShort = DurationUtilities.createDuration(0, 0, 5);
		Duration actualShort = DurationUtilities.parseYoutubeDuration("PT5S"); //$NON-NLS-1$

		assertEquals(expectedShort, actualShort);

		Duration expectedMiddle = DurationUtilities.createDuration(0, 4, 5);
		Duration actualMiddle = DurationUtilities.parseYoutubeDuration("PT4M5S"); //$NON-NLS-1$

		assertEquals(expectedMiddle, actualMiddle);

		Duration expectedLong = DurationUtilities.createDuration(1, 50, 42);
		Duration actualLong = DurationUtilities.parseYoutubeDuration("PT1H50M42S"); //$NON-NLS-1$

		assertEquals(expectedLong, actualLong);

		Duration expectedFailurar = DurationUtilities.createDuration(1, 25, 0);
		Duration actualFailurar = DurationUtilities.parseYoutubeDuration("PT1H25M"); //$NON-NLS-1$

		assertEquals(expectedFailurar, actualFailurar);
			
		
		// #3
		Duration expectedBuggy = DurationUtilities.createDuration(1, 0, 1);
		Duration actualBuggy = DurationUtilities.parseYoutubeDuration("PT1H1S"); //$NON-NLS-1$

		assertEquals(expectedBuggy, actualBuggy);
	}

	@Test
	public void testToHumanString() {
		Duration inputShort = DurationUtilities.createDuration(0, 0, 5);
		assertEquals("0:05", DurationUtilities.toHumanString(inputShort)); //$NON-NLS-1$

		Duration inputMiddle = DurationUtilities.createDuration(0, 11, 12);
		assertEquals("11:12", DurationUtilities.toHumanString(inputMiddle)); //$NON-NLS-1$

		Duration inputLong = DurationUtilities.createDuration(99, 1, 1);
		assertEquals("99:01:01", DurationUtilities.toHumanString(inputLong)); //$NON-NLS-1$

		Duration inputMega = DurationUtilities.createDuration(1, 58, 33);
		assertEquals("1:58:33", DurationUtilities.toHumanString(inputMega)); //$NON-NLS-1$

	}

}
