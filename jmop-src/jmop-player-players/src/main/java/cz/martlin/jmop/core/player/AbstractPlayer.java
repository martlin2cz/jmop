package cz.martlin.jmop.core.player;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ObservableObject;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
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

	private final TracksSource local;
	/**
	 * @deprecated use the {@link TracksSource} instance.
	 */
	@Deprecated
	private final Object tracksLocation;
	/**
	 * @deprecated no more needed.
	 */
	@Deprecated
	private final TrackFileFormat supportedFormat;
	
	private boolean stopped;
	private boolean paused;
	private boolean over;
	private Track playedTrack;

	public AbstractPlayer(TracksSource local, Object locator, TrackFileFormat supportedFormat) {
		super();
		this.local = local;

		this.supportedFormat = supportedFormat;
//		this.tracksLocation = locator.locationOfPlay(this); //FIXME
		this.tracksLocation = null;
		
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
	public synchronized void startPlaying(Track track) throws JMOPMusicbaseException {
		LOG.info("Starting playing"); //$NON-NLS-1$
		if (!stopped) {
			doStopPlaying();
		}

		File file = local.trackFile(track);
		LOG.debug("Will play file " + file); //$NON-NLS-1$
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
		LOG.info("Stopping playing"); //$NON-NLS-1$
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
		LOG.info("Pausing playing"); //$NON-NLS-1$
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
		LOG.info("Resuming playing"); //$NON-NLS-1$
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
		LOG.info("Seeking to " + DurationUtilities.toHumanString(to)); //$NON-NLS-1$

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
		LOG.info("Track play finished"); //$NON-NLS-1$

		over = true;
		fireValueChangedEvent();
	}
}
