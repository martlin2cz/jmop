package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

class JmopReplPlayingTest extends AbstractReplTest {

	@Test
	void testListCommands() throws JMOPMusicbaseException {

		exec("bundles");

		exec("playlists");
		exec("playlists", "BarBundle");

		exec("tracks");
		exec("tracks", "BarBundle");
	}

	@Test
	void testStatusCommand() throws JMOPMusicbaseException {

		exec("status");

		Bundle bundle = jmop.musicbase().bundleOfName("BarBundle");
		Playlist playlist = jmop.musicbase().playlistOfName(bundle, "all tracks");
		jmop.playing().play(playlist);

		exec("status");

		jmop.playing().pause();
		exec("status");

		jmop.playing().stop();
		exec("status");
		
		jmop.config().terminate();
		exec("status");
	}

	@Test
	void testPlayingCommands() throws JMOPMusicbaseException {

		exec("play", "BarBundle");
		exec("status");

		exec("pause");
		exec("status");

		exec("resume");
		exec("status");

		exec("pause");
		exec("status");

		exec("play");
		exec("status");

		exec("stop");
		exec("status");

		exec("play");
		exec("status");

		exec("seek", "0:10");
		exec("status");

		exec("stop");
		exec("status");
	}

	@Test
	void testPlayingNextAndPrevious() throws JMOPMusicbaseException {

		exec("play", "BarBundle");
		exec("status");
		exec("playlist");

		exec("next");
		exec("status");
		exec("playlist");

		exec("previous");
		exec("status");
		exec("playlist");

		exec("stop");
		exec("status");
		exec("playlist");
	}
	
}
