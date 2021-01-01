package cz.martlin.jmop.player.cli.main;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import picocli.CommandLine;

class JMOPCLITest {

	@Test
	void test() throws JMOPMusicbaseException {
		CommandLine cl = JMOPCLI.prepareCL();

		exec(cl, "bundles");

		exec(cl, "playlists");
		exec(cl, "playlists", "BarBundle");

		exec(cl, "tracks");
		exec(cl, "tracks", "BarBundle");

	}

	private void exec(CommandLine cl, String... command) {
		System.err.println("=== EXECUTING " + Arrays.toString(command) + " ===");

		cl.execute(command);

	}

}
