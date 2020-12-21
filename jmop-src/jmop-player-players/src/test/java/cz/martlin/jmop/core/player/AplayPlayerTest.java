package cz.martlin.jmop.core.player;

import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.utils.TestingTracksSource;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.player.base.player.BasePlayer;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

@DisabledOnOs(value = OS.WINDOWS)
public class AplayPlayerTest extends AbstractPlayerTest {

	public AplayPlayerTest() {
		super();
	}
	
	@Override
	protected BasePlayer createPlayer() {
		TracksSource local = new TestingTracksSource(TrackFileFormat.WAV);
		BaseErrorReporter reporter = new SimpleErrorReporter();
		return new AplayPlayer(reporter, local);
	}

}
