package cz.martlin.jmop.player.cli.repl.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser;
import cz.martlin.jmop.player.cli.repl.converters.BundledItemPathParser.BundledItemName;
import picocli.CommandLine.TypeConversionException;

class BundledItemPathParserTest {

	private static final String ALL_TRACKS = "ALL_THE_TRACKS";

	@Test
	void testParse() {
		BundledItemPathParser parser = new BundledItemPathParser(ALL_TRACKS);

		check("foo", ALL_TRACKS, parser, "foo");
		check("foo", ALL_TRACKS, parser, "foo/");
		check("foo", "bar", parser, "foo/bar");

		assertThrows(TypeConversionException.class, () -> parser.parse(""));
		assertThrows(TypeConversionException.class, () -> parser.parse("/bar"));
		assertThrows(TypeConversionException.class, () -> parser.parse("foo/bar/baz"));

		check("lorem ipsum", "dolor sit amet", parser, "lorem ipsum/dolor sit amet");
		// FIXME: check("disco", "the best of 98/99", converter, "disco/the best of
		// 98/99");
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void check(String expectedBundleName, String expectedItemName, BundledItemPathParser parser, String input) {
		BundledItemName bpn = parser.parse(input);

		assertEquals(expectedBundleName, bpn.bundleName);
		assertEquals(expectedItemName, bpn.itemName);
	}

}
