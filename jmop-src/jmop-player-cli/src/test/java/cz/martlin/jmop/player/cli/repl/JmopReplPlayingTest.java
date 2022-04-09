package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.core.misc.DurationUtilities;

class JmopReplPlayingTest extends AbstractReplTest {


	@Test
	void testStatusAndBarCommand()  {

		exec("status");
		exec("bar");

		jmop.playing().play(tme.tmd.daftPunk);

		exec("status");
		exec("bar");

		jmop.playing().pause();
		exec("status");
		exec("bar");

		jmop.playing().seek(DurationUtilities.createDuration(0, 0, 30));
		exec("status");
		exec("bar");
		
		jmop.playing().stop();
		exec("status");
		exec("bar");
		
		jmop.config().terminate();
		exec("status");
		exec("bar");
	}

	@Test
	void testPlayingCommands()  {
		String daftPunk = tme.tmd.daftPunk.getName();
		exec("play", "bundle", daftPunk);
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
	void testPlayingNextAndPrevious()  {

		String daftPunk = tme.tmd.daftPunk.getName();
		exec("play", "bundle", daftPunk);
		
		exec("status");
		exec("playlist", ".");

		exec("next");
		exec("status");
		exec("playlist", ".");

		exec("previous");
		exec("status");
		exec("playlist", ".");

		exec("stop");
		exec("status");
		exec("playlist", ".");
	}
	
	@Test
	void testTheCommandP()  {
		String daftPunk = tme.tmd.daftPunk.getName();
		String bestTracks = tme.tmd.bestTracks.getName();
		String meteorities = tme.tmd.meteorities.getTitle();
		
		exec("p", daftPunk);
		exec("status");
	
		exec("p", bestTracks);
		exec("status");
		
		exec("p", meteorities);
		exec("status");
		
		exec("p", "-1");
		exec("status");
		
		exec("stop");
		exec("p");
		exec("status");
		
		exec("p");
		exec("status");
		
		exec("p");
		exec("status");
	}
}
