package cz.martlin.jmop.player.cli.main;

import static org.junit.jupiter.api.Assumptions.assumeFalse;

import java.util.Arrays;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.DefaultPlayerFascadeBuilder;
import picocli.CommandLine;

class JMOPCLITest {

	private JMOPPlayerFascade fascade;

	@Test
	void testListCommands() throws JMOPMusicbaseException {
		CommandLine cl = prepareCL();

		exec(cl, "bundles");

		exec(cl, "playlists");
		exec(cl, "playlists", "BarBundle");

		exec(cl, "tracks");
		exec(cl, "tracks", "BarBundle");
	}

	@Test
	void testStatusCommand() throws JMOPMusicbaseException {
		CommandLine cl = prepareCL();

		exec(cl, "status");
		
		Bundle bundle = fascade.adapter().bundleOfName("BarBundle");
		Playlist playlist = fascade.adapter().playlistOfName(bundle, "all tracks");
		fascade.startPlaying(playlist);

		exec(cl, "status");
		
		fascade.pause();
		exec(cl, "status");
		
		fascade.stopPlaying();
		exec(cl, "status");
	}
	
	@Test
	void testPlayingCommands() throws JMOPMusicbaseException {
		CommandLine cl = prepareCL();

		exec(cl, "play", "BarBundle");
		exec(cl, "status");
		
		exec(cl, "pause");
		exec(cl, "status");
		
		exec(cl, "resume");
		exec(cl, "status");
		
		exec(cl, "pause");
		exec(cl, "status");
		
		exec(cl, "play");
		exec(cl, "status");
		
		exec(cl, "stop");
		exec(cl, "status");
		
		exec(cl, "play");
		exec(cl, "status");
		
		exec(cl, "seek", "0:10");
		exec(cl, "status");
		
		exec(cl, "stop");
		exec(cl, "status");
	}

	
	@Test
	void testPlayingNextAndPrevious() throws JMOPMusicbaseException {
		CommandLine cl = prepareCL();

		exec(cl, "play", "BarBundle");
		exec(cl, "status");
		
		exec(cl, "next");
		exec(cl, "status");

		exec(cl, "previous");
		exec(cl, "status");

		exec(cl, "stop");
		exec(cl, "status");
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	private CommandLine prepareCL() {
		fascade = DefaultPlayerFascadeBuilder.createTesting();
		try {
			fascade.load();
		} catch (JMOPMusicbaseException e) {
			assumeFalse(e != null);
		}
		
		AbstractRepl repl = new JmopRepl(fascade);
		return repl.createStandaloneCommandline();
	}
	
	private void exec(CommandLine cl, String... command) {
		System.err.println("=== EXECUTING " + Arrays.toString(command) + " ===");

		cl.execute(command);

	}

}
