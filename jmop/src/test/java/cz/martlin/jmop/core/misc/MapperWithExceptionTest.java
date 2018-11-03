package cz.martlin.jmop.core.misc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import cz.martlin.jmop.core.misc.MapperWithException.ExceptionInLoop;

public class MapperWithExceptionTest {

	@Test
	public void testNoException() {
		String[] inputArray = new String[] { "foo", "bar", "baz", "aux" };
		Stream<String> input = Stream.of(inputArray);

		try {
			Stream<String> output = MapperWithException.mapWithException(input, //
					(s) -> s.toUpperCase());

			List<String> outputList = output.collect(Collectors.toList());
			assertEquals("FOO", outputList.get(0));
			assertEquals("BAR", outputList.get(1));
			assertEquals("BAZ", outputList.get(2));
			assertEquals("AUX", outputList.get(3));
		} catch (ExceptionInLoop e) {
			assertNull(e);
		}
	}

	@Test
	public void testWithException() {
		String[] inputArray = new String[] { "foo", "", "", "au" };
		Stream<String> input = Stream.of(inputArray);

		try {
			Stream<String> output = MapperWithException.mapWithException(input, //
					(s) -> s.substring(1));

			fail("Should fail, but returned: " + output.collect(Collectors.joining(",")));
		} catch (ExceptionInLoop e) {
			assertEquals(2, e.getSuppressed().length);
		}
	}

}
