package cz.martlin.jmop.core.player;

import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.utils.TestingTracksSource;
import cz.martlin.jmop.core.player.base.player.BasePlayer;

public class JavaFXPlayerTest extends AbstractPlayerTest {

	public JavaFXPlayerTest() {
		super();
	}

	@Override
	protected BasePlayer createPlayer() {
		TracksSource local = new TestingTracksSource();
		return new JavaFXMediaPlayer(local);
	}

}
