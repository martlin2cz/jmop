package cz.martlin.jmop.core.player;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ObservableObject;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import javafx.util.Duration;

/**
 * The general {@link BasePlayer} with some common functions. Holds all required
 * values in fields and when the internal status changes, fires event.
 * 
 * @author martin
 *
 */
public abstract class AbstractPlayer extends ObservableObject<BasePlayer> implements BasePlayer {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseLocalSource local;
	private final TrackFileLocation tracksLocation;
	private final TrackFileFormat supportedFormat;

	private boolean stopped;
	private boolean paused;
	private boolean over;
	private Track playedTrack;

	public AbstractPlayer(BaseLocalSource local, AbstractTrackFileLocator locator, TrackFileFormat supportedFormat) {
		super();
		this.local = local;

		this.supportedFormat = supportedFormat;
		this.tracksLocation = locator.locationOfPlay(this);

		this.stopped = true;
		this.paused = false;
		this.over = false;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public TrackFileFormat getPlayableFormat() {
		return supportedFormat;
	}

	@Override
	public Track getPlayedTrack() {
		return playedTrack;
	}

	@Override
	public boolean isStopped() {
		return stopped;
	}

	@Override
	public boolean isPaused() {
		return paused;
	}

	@Override
	public boolean isPlayOver() {
		return over;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public synchronized void startPlaying(Track track) throws JMOPSourceException {
		LOG.info("Starting playing");
		if (!stopped) {
			doStopPlaying();
		}

		File file = local.fileOfTrack(track, tracksLocation, supportedFormat);
		LOG.debug("Will play file " + file);
		doStartPlaying(track, file);

		over = false;
		stopped = false;
		paused = false;
		playedTrack = track;

		fireValueChangedEvent();
	}

	/**
	 * Start playing given track with given file. No need to additional checks
	 * or stop.
	 * 
	 * @param track
	 * @param file
	 */
	protected abstract void doStartPlaying(Track track, File file);

	@Override
	public synchronized void stop() {
		LOG.info("Stopping playing");
		if (stopped) {
			return;
		}

		doStopPlaying();

		playedTrack = null;
		stopped = true;
		fireValueChangedEvent();
	}

	/**
	 * Stop playing current track. No need to aditional checks.
	 */
	protected abstract void doStopPlaying();

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public synchronized void pause() {
		LOG.info("Pausing playing");
		if (paused) {
			return;
		}

		paused = true;

		doPausePlaying();
		fireValueChangedEvent();
	}

	/**
	 * Pauses playing current track. No need to aditional checks.
	 */
	protected abstract void doPausePlaying();

	@Override
	public synchronized void resume() {
		LOG.info("Resuming playing");
		if (!paused) {
			return;
		}

		doResumePlaying();

		paused = false;
		fireValueChangedEvent();
	}

	/**
	 * Resumes playing current track. No need to aditional checks.
	 */
	protected abstract void doResumePlaying();

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void seek(Duration to) {
		LOG.info("Seeking to " + DurationUtilities.toHumanString(to));

		doSeek(to);
		fireValueChangedEvent();
	}

	/**
	 * Seeks to given time. No need to aditional checks.
	 * 
	 * @param to
	 */
	protected abstract void doSeek(Duration to);

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Marks as finished playing track. No need to aditional checks.
	 */
	protected void trackFinished() {
		LOG.info("Track play finished");

		over = true;
		fireValueChangedEvent();
	}
}
