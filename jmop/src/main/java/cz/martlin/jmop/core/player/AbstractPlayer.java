package cz.martlin.jmop.core.player;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import javafx.util.Duration;

public abstract class AbstractPlayer implements BasePlayer {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseLocalSource local;
	private final TrackFileLocation tracksLocation;
	private final TrackFileFormat supportedFormat;

	private TrackPlayedHandler handler;

	private boolean playing;
	private boolean paused;

	public AbstractPlayer(BaseLocalSource local, AbstractTrackFileLocator locator, TrackFileFormat supportedFormat) {
		super();
		this.local = local;
		this.supportedFormat = supportedFormat;
		
		this.tracksLocation = locator.locationOfPlay();
		this.playing = false;
		this.paused = false;
	}

	public TrackPlayedHandler getHandler() {
		return handler;
	}

	@Override
	public TrackFileFormat getPlayableFormat() {
		return supportedFormat;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void setHandler(TrackPlayedHandler handler) {
		this.handler = handler;
	}

	@Override
	public boolean supports(TrackFileFormat format) {
		return supportedFormat.equals(format);
	}

	protected void onPlayed(Track track) {
		if (handler != null) {
			handler.trackPlayed(track);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public synchronized void startPlayling(Track track) throws JMOPSourceException {
		LOG.info("Starting playing");
		if (playing) {
			doStopPlaying();
		}

		playing = true;

		File file = local.fileOfTrack(track, tracksLocation, supportedFormat);

		LOG.debug("Will play file " + file);
		doStartPlaying(track, file);
	}

	protected abstract void doStartPlaying(Track track, File file);

	@Override
	public synchronized void stop() {
		LOG.info("Stopping playing");
		if (!playing) {
			return;
		}

		doStopPlaying();

		playing = false;
	}

	protected abstract void doStopPlaying();

	@Override
	public synchronized void pause() {
		LOG.info("Pausing playing");
		if (paused) {
			return;
		}

		paused = true;

		doPausePlaying();
	}

	protected abstract void doPausePlaying();

	@Override
	public synchronized void resume() {
		LOG.info("Resuming playing");
		if (!paused) {
			return;
		}

		doResumePlaying();

		paused = false;
	}

	protected abstract void doResumePlaying();

	@Override
	public void seek(Duration to) {
		LOG.info("Seeking to " + DurationUtilities.toHumanString(to));

		doSeek(to);
	}

	protected abstract void doSeek(Duration to);

	/////////////////////////////////////////////////////////////////////////////////////
}
