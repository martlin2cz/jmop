package cz.martlin.jmop.core.player;

import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.utils.TestingTracksSource;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.player.base.player.BasePlayer;

public class AplayPlayerTest extends AbstractPlayerTest {

	public AplayPlayerTest() {
		super();
	}
	
	@Override
	protected BasePlayer createPlayer() {
		TracksSource local = new TestingTracksSource();
		BaseErrorReporter reporter = new SimpleErrorReporter();
		return new AplayPlayer(reporter, local);
	}

}
