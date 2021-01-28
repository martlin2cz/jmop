package cz.martlin.jmop.player.cli.repl;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.core.misc.DurationUtilities;

class JmopReplPlayingTest extends AbstractReplTest {


	@Test
	void testStatusAndBarCommand()  {

		exec("status");
		exec("bar");

		jmop.playing().play(tmb.tm.daftPunk);

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
		String daftPunk = tmb.tm.daftPunk.getName();
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

		String daftPunk = tmb.tm.daftPunk.getName();
		exec("play", "bundle", daftPunk);
		
		exec("status");
		exec("playlist", ".");

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
