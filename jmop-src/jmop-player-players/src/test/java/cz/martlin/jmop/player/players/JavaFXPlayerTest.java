package cz.martlin.jmop.player.players;

import org.junit.jupiter.api.Nested;

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
			return new JavaFXMediaPlayer();
		}

	}
	
	@Nested
	public class JavaFXPlayerWAVTest extends AbstractPlayerTest {

		public JavaFXPlayerWAVTest() {
			super();
		}

		@Override
		protected BasePlayer createPlayer() {
			return new JavaFXMediaPlayer();
		}

	}
}
