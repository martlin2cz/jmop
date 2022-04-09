package cz.martlin.jmop.player.cli.repl;

import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.model.Playlist;

public class JmopReplBruteForceTest extends AbstractReplTest {

	private String[][] commands;

	private String[][] prepareCommands() {
		return new String[][] { //
				// playing commands
				{ "play" }, //
				{ "pause" }, //
				{ "resume" }, //
				{ "stop" }, //
				{ "seek", "0:10" }, //
				{ "seek", "0:30" }, //
				// play something commands
				{ "play", "bundle", tme.tmd.daftPunk.getName() }, //
				{ "play", "playlist", couple(tme.tmd.daftPunk, tme.tmd.discovery) }, //
				{ "play", "track", couple(tme.tmd.daftPunk, tme.tmd.getLucky) }, //
				{ "play", "1." }, //
				// { "play", "+1" }, //
				// displaying commands
				{ "status" }, //
				{ "bar" }, //
				{ "bundle", "." }, //
				{ "playlist" , "."}, //
				{ "track" , "."}, //
				// listing commands
				{ "list", "bundles" }, //
				{ "list", "playlists" }, //
				{ "list", "tracks" }, //

//			{ "whatever" }, //
		};
	}

	@BeforeEach
	public void before() {
		Playlist playlist = tme.tmd.seventeen;
		jmop.playing().play(playlist);

		this.commands = prepareCommands();
	}

	@AfterEach
	public void after() {
		if (jmop.status().isPlayingSomeTrack()) {
			jmop.playing().stop();
		}
	}

	///////////////////////////////////////////////////////////////////////////

	@Test
	public void testNumberOne() throws Exception {
		run(1, 10);
	}

	@Test
	public void testNumberTwo() throws Exception {
		run(2, 100);
	}

	@Test
	public void testNumberThree() throws Exception {
		run(3, 100);
	}

	///////////////////////////////////////////////////////////////////////////

	private void run(int seed, int count) {
		Random rand = new Random(seed);

		for (int i = 0; i < count; i++) {
			int index = rand.nextInt(commands.length);
			String[] command = commands[index];
			exec(command);
		}
	}

}
