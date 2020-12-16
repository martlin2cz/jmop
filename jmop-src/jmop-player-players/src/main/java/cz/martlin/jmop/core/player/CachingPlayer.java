package cz.martlin.jmop.core.player;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.util.Duration;

public class CachingPlayer extends AbstractPlayer {

	public CachingPlayer(TracksSource local, Object locator, TrackFileFormat supportedFormat) {
		super(local, locator, supportedFormat);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Duration currentTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void doStartPlaying(Track track, File file) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doStopPlaying() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doPausePlaying() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doResumePlaying() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doSeek(Duration to) {
		// TODO Auto-generated method stub

	}

}
