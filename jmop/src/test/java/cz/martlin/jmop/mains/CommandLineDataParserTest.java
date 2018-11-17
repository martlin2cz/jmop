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
		run("-h", true, false, null, null, null, null); //$NON-NLS-1$
		run("--help", true, false, null, null, null, null); //$NON-NLS-1$
		run("-v", false, true, null, null, null, null); //$NON-NLS-1$
		run("--version", false, true, null, null, null, null); //$NON-NLS-1$
	}

	@Test
	public void testSimples() {
		run("-lang CZ", false, false, null, "CZ", null, null); //$NON-NLS-1$ //$NON-NLS-2$
		run("-dir foo/bar", false, false, "foo/bar", null, null, null); //$NON-NLS-1$ //$NON-NLS-2$
		run("-play foo", false, false, null, null, "foo", null); //$NON-NLS-1$ //$NON-NLS-2$
		run("-play foo bar", false, false, null, null, "foo", "bar"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	@Test
	public void testCombined() {
		run("-dir foo/bar -lang CZ", false, false, "foo/bar", "CZ", null, null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		run("-dir foo -play bar", false, false, "foo", null, "bar", null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		run("-lang CZ -play bar baz", false, false, null, "CZ", "bar", "baz"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	@Test
	public void testReorder() {
		run("-dir foo/bar -lang CZ -play Lorem Ipsum", false, false, "foo/bar", "CZ", "Lorem", "Ipsum"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		run("-lang CZ -dir foo/bar -play Lorem Ipsum", false, false, "foo/bar", "CZ", "Lorem", "Ipsum"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		run("-play Lorem Ipsum -lang CZ -dir foo/bar", false, false, "foo/bar", "CZ", "Lorem", "Ipsum"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}
	
	@Test
	public void testAmbigious() {
		run("-dir -lang", false, false, "-lang", null, null, null); //$NON-NLS-1$ //$NON-NLS-2$
		run("-play -dir -lang", false, false, null, null, "-dir", "-lang"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	@Test
	public void testCorrupted() {
		invalid("foobar"); //$NON-NLS-1$
		invalid("-dir"); //$NON-NLS-1$
		invalid("-lang"); //$NON-NLS-1$
		invalid("-play"); //$NON-NLS-1$
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private void run(String input, boolean expectedIsHelp, boolean expectedIsVersion, //
			String expectedRootPath, String expectedLang, String expectedBundleName, String expectedPlaylistName) {

		String[] args = input.split(" "); //$NON-NLS-1$
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
		String[] args = input.split(" "); //$NON-NLS-1$
		try {
			parser.extractCommandLineData(args);
			fail("Input invalid"); //$NON-NLS-1$
		} catch (IllegalArgumentException e) {
			assertNotNull(e);
		}

	}
}
