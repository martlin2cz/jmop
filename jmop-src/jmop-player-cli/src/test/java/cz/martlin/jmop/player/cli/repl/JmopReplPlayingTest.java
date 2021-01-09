package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import picocli.CommandLine;

class JmopReplPlayingTest extends AbstractReplTest {

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
		fascade.play(playlist);

		exec(cl, "status");

		fascade.pause();
		exec(cl, "status");

		fascade.stop();
		exec(cl, "status");
		
		fascade.terminate();
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
		exec(cl, "playlist");

		exec(cl, "next");
		exec(cl, "status");
		exec(cl, "playlist");

		exec(cl, "previous");
		exec(cl, "status");
		exec(cl, "playlist");

		exec(cl, "stop");
		exec(cl, "status");
		exec(cl, "playlist");
	}
	
}
