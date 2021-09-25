package cz.martlin.jmop.player.engine.engines;

import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.utils.TestingTracksSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.testing.AbstractPlayerEngineTest;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.JavaFXMediaPlayer;

class SimplePlayerEngineTest extends AbstractPlayerEngineTest {

	@Override
	protected BasePlayerEngine createEngine() {
		TracksSource tracks = new TestingTracksSource(TrackFileFormat.WAV);
		BasePlayer player = new JavaFXMediaPlayer(tracks);
		return new SimplePlayerEngine(player);
	}


}
