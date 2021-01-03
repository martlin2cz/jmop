package cz.martlin.jmop.player.cli.main;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class CommandsReaderTest {

	@Test
	void testParsing() {
		InputStream source = createSource(//
				"foo bar", //
				"baz aux", //
				"lorem\\ ipsum dolor\\ sit \\ amet", //
				"42 99 101");

		CommandsReader reader = new CommandsReader(source);

		check(reader, "foo", "bar");
		check(reader, "baz", "aux");
		check(reader, "lorem ipsum", "dolor sit amet");
		check(reader, "42", "99", "101");
	}
	
	@Test
	@Timeout(unit = TimeUnit.SECONDS, value = 20)
	void testInteractive() {
		InputStream source = System.in;
		CommandsReader reader = new CommandsReader(source);
		
		System.out.println("Reading from stdin until the empty line.");
		while (true) {
			System.out.println("TYPE: ");
			String[] arguments = reader.waitAndGetNext();
			System.out.println(Arrays.toString(arguments));
			
			if (arguments.length == 0) {
				break;
			}
		}
		
	}

	///////////////////////////////////////////////////////////////////////////
	
	private void check(CommandsReader reader, String ...expectedArguments) {
		String[] arguments = reader.waitAndGetNext();
		
		assertEquals(Arrays.toString(arguments), Arrays.toString(expectedArguments));
		assertArrayEquals(expectedArguments, arguments);
	}

	private InputStream createSource(String... lines) {
		String input = Arrays.stream(lines).collect(Collectors.joining(System.lineSeparator()));
		byte[] buff = input.getBytes();
		return new ByteArrayInputStream(buff);
	}

}
