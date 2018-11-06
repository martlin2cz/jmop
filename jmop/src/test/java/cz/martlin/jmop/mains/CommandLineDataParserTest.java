package cz.martlin.jmop.mains;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import cz.martlin.jmop.core.data.CommandlineData;

public class CommandLineDataParserTest {

	private final CommandLineDataParser parser;

	public CommandLineDataParserTest() {
		this.parser = new CommandLineDataParser();
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void testHelpAndVersion() {
		run("-h", true, false, null, null, null, null);
		run("--help", true, false, null, null, null, null);
		run("-v", false, true, null, null, null, null);
		run("--version", false, true, null, null, null, null);
	}

	@Test
	public void testSimples() {
		run("-lang CZ", false, false, null, "CZ", null, null);
		run("-dir foo/bar", false, false, "foo/bar", null, null, null);
		run("-play foo", false, false, null, null, "foo", null);
		run("-play foo bar", false, false, null, null, "foo", "bar");
	}
	
	@Test
	public void testCombined() {
		run("-dir foo/bar -lang CZ", false, false, "foo/bar", "CZ", null, null);
		run("-dir foo -play bar", false, false, "foo", null, "bar", null);
		run("-lang CZ -play bar baz", false, false, null, "CZ", "bar", "baz");
	}

	@Test
	public void testReorder() {
		run("-dir foo/bar -lang CZ -play Lorem Ipsum", false, false, "foo/bar", "CZ", "Lorem", "Ipsum");
		run("-lang CZ -dir foo/bar -play Lorem Ipsum", false, false, "foo/bar", "CZ", "Lorem", "Ipsum");
		run("-play Lorem Ipsum -lang CZ -dir foo/bar", false, false, "foo/bar", "CZ", "Lorem", "Ipsum");
	}
	
	@Test
	public void testAmbigious() {
		run("-dir -lang", false, false, "-lang", null, null, null);
		run("-play -dir -lang", false, false, null, null, "-dir", "-lang");
	}
	
	@Test
	public void testCorrupted() {
		invalid("foobar");
		invalid("-dir");
		invalid("-lang");
		invalid("-play");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void run(String input, boolean expectedIsHelp, boolean expectedIsVersion, //
			String expectedRootPath, String expectedLang, String expectedBundleName, String expectedPlaylistName) {

		String[] args = input.split(" ");
		CommandlineData data = parser.extractCommandLineData(args);

		assertEquals(expectedIsHelp, data.isHelp());
		assertEquals(expectedIsVersion, data.isVersion());
		if (expectedRootPath != null) {
			assertEquals(expectedRootPath, data.getRoot().toString());
		}
		assertEquals(expectedLang, data.getLanguage());
		assertEquals(expectedBundleName, data.getBundleToPlayName());
		assertEquals(expectedPlaylistName, data.getPlaylistToPlayName());

	}

	private void invalid(String input) {
		String[] args = input.split(" ");
		try {
			parser.extractCommandLineData(args);
			fail("Input invalid");
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}

	}
}
