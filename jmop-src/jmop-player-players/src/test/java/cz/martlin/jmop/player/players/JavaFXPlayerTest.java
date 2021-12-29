package cz.martlin.jmop.player.players;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

@Tag(value = "IDE_ONLY")
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
		protected TrackFileFormat getFormat() {
			return TrackFileFormat.MP3;
		}
		
		@Override
		protected BasePlayer createPlayer() {
			return new JavaFXMediaPlayer();
		}

	}
	
	@Nested
	public class JavaFXPlayerWAVTest extends AbstractPlayerTest {

		public JavaFXPlayerWAVTest() {
			super();
		}
		@Override
		protected TrackFileFormat getFormat() {
			return TrackFileFormat.WAV;
		}

		@Override
		protected BasePlayer createPlayer() {
			return new JavaFXMediaPlayer();
		}

	}
}
