package cz.martlin.jmop.player.cli.repl.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.player.cli.repl.converters.CoupleOrNotParser.CoupleOrNot;

class CoupleOrNotParserTest {

	@Test
	void testParse() {
		CoupleOrNotParser parser = new CoupleOrNotParser();

		check("", null, parser, "");
		check("", "", parser, "/");
		
		check("foo", null, parser, "foo");
		check("foo", "", parser, "foo/");
		check("", "bar", parser, "/bar");
		check("foo", "bar", parser, "foo/bar");
		
		check(IllegalArgumentException.class, parser, "/foo/bar");
		check(IllegalArgumentException.class, parser, "/foo/bar/");
		check(IllegalArgumentException.class, parser, "/foo/bar/baz");
		check(IllegalArgumentException.class, parser, "/foo/bar/baz/");

		check("lorem ipsum", "dolor sit amet", parser, "lorem ipsum/dolor sit amet");
		check("hello world", "hell wood", parser, "hello world/hell wood");
		
		// FIXME: check("disco", "the best of 98/99", converter, "disco/the best of
		// 98/99");
	}

	/////////////////////////////////////////////////////////////////////////////////////


	private void check(String expectedFirst, String expectedSecond, CoupleOrNotParser parser, String input) {
		CoupleOrNot bpn = parser.parse(input);

		assertEquals(expectedFirst, bpn.first(""));
		
		if (expectedSecond != null) { 
			assertEquals(expectedSecond, bpn.second(""));
		} else {
			assertThrows(NullPointerException.class, () -> bpn.second("whatever"));
		}
	}
	

	private void check(Class<IllegalArgumentException> expectedException, CoupleOrNotParser parser, String input) {
		assertThrows(expectedException, () -> parser.parse(input));
	}

}
