package cz.martlin.jmop.core.player;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ObservableObject;
import cz.martlin.jmop.core.player.base.player.BasePlayer;
import cz.martlin.jmop.core.player.base.player.PlayerStatus;
import javafx.util.Duration;

/**
 * The general {@link BasePlayer} with some common functions. Holds all required
 * values in fields and when the internal status changes, fires event.
 * 
 * @author martin
 *
 */
public abstract class AbstractPlayer extends ObservableObject<BasePlayer> implements BasePlayer {
//	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final TracksSource local;
	
	private PlayerStatus status;
	private Track track;

	public AbstractPlayer(TracksSource local) {
		super();
		this.local = local;

		this.status = PlayerStatus.NO_TRACK;
		this.track = null;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public PlayerStatus currentStatus() throws JMOPMusicbaseException {
		return status;
	}
	
	@Override
	public Track actualTrack() {
		return track;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public synchronized void startPlaying(Track track) throws JMOPMusicbaseException {
//		LOG.info("Starting playing"); //$NON-NLS-1$
		if (status.isNotPlayingTrack()) {
			doStopPlaying();
		}

		File file = local.trackFile(track);
//		LOG.debug("Will play file " + file); //$NON-NLS-1$
		doStartPlaying(track, file);

		status = PlayerStatus.PLAYING;
		track = track;

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
//		LOG.info("Stopping playing"); //$NON-NLS-1$
		if (status.isNotPlayingTrack()) {
			return;
		}

		doStopPlaying();

		track = null;
		status = PlayerStatus.STOPPED;
		
		fireValueChangedEvent();
	}

	/**
	 * Stop playing current track. No need to aditional checks.
	 */
	protected abstract void doStopPlaying();

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public synchronized void pause() {
//		LOG.info("Pausing playing"); //$NON-NLS-1$
		if (status.isPlaying()) {
			return;
		}

		status = PlayerStatus.PAUSED;

		doPausePlaying();
		fireValueChangedEvent();
	}

	/**
	 * Pauses playing current track. No need to aditional checks.
	 */
	protected abstract void doPausePlaying();

	@Override
	public synchronized void resume() {
//		LOG.info("Resuming playing"); //$NON-NLS-1$
		if (status.isPaused()) {
			return;
		}

		doResumePlaying();

		status = PlayerStatus.PLAYING;
		
		fireValueChangedEvent();
	}

	/**
	 * Resumes playing current track. No need to aditional checks.
	 */
	protected abstract void doResumePlaying();

	/////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void seek(Duration to) {
//		LOG.info("Seeking to " + DurationUtilities.toHumanString(to)); //$NON-NLS-1$

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
//		LOG.info("Track play finished"); //$NON-NLS-1$

		status = PlayerStatus.NO_TRACK;
		fireValueChangedEvent();
	}
}
