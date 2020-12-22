package cz.martlin.jmop.player.players;

import org.junit.jupiter.api.Nested;

import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.utils.TestingTracksSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.player.players.BasePlayer;
import cz.martlin.jmop.player.players.JavaFXMediaPlayer;

public class JavaFXPlayerTest  {

	public JavaFXPlayerTest() {
		super();
	}

	@Nested
	public class JavaFXPlayerMP3Test extends AbstractPlayerTest {

		public JavaFXPlayerMP3Test() {
			super();
		}

		@Override
		protected BasePlayer createPlayer() {
			TracksSource local = new TestingTracksSource(TrackFileFormat.MP3);
			return new JavaFXMediaPlayer(local);
		}

	}
	
	@Nested
	public class JavaFXPlayerWAVTest extends AbstractPlayerTest {

		public JavaFXPlayerWAVTest() {
			super();
		}

		@Override
		protected BasePlayer createPlayer() {
			TracksSource local = new TestingTracksSource(TrackFileFormat.WAV);
			return new JavaFXMediaPlayer(local);
		}

	}
}
