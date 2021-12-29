package cz.martlin.jmop.player.players;

import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.testing.resources.TestingTracksSource;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.SimpleErrorReporter;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.players.AplayPlayer;
import cz.martlin.jmop.player.players.BasePlayer;

@DisabledOnOs(value = OS.WINDOWS)
public class AplayPlayerTest extends AbstractPlayerTest {

	public AplayPlayerTest() {
		super();
	}
	
	@Override
	protected TrackFileFormat getFormat() {
		return TrackFileFormat.WAV;
	}
	
	@Override
	protected BasePlayer createPlayer() {
		BaseErrorReporter reporter = new SimpleErrorReporter();
		return new AplayPlayer(reporter);
	}

}
