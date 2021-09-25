package cz.martlin.jmop.player.engine.engines;

import cz.martlin.jmop.common.musicbase.TracksLocator;
import cz.martlin.jmop.common.musicbase.dflt.DefaultInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.testing.resources.TestingTracksSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.engine.BasePlayerEngine;
import cz.martlin.jmop.player.engine.testing.AbstractPlayerEngineTest;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.JavaFXMediaPlayer;

class SimplePlayerEngineTest extends AbstractPlayerEngineTest {

	@Override
	protected BasePlayerEngine createEngine() {
		TracksLocator tracks = new TestingTracksSource(TrackFileFormat.WAV);
		BasePlayer player = new JavaFXMediaPlayer(tracks);
		return new SimplePlayerEngine(player);
	}


}
