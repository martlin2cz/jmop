package cz.martlin.jmop.player.players;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.ObservableObject;
import javafx.util.Duration;

/**
 * The general {@link BasePlayer} with some common functions. Holds all required
 * values in fields and when the internal status changes, fires event.
 * 
 * @author martin
 *
 */
public abstract class AbstractPlayer extends ObservableObject<BasePlayer> implements BasePlayer {

	private final Logger LOG = LoggerFactory.getLogger(getClass()); //TODO log all the other methods
	
	private TrackFinishedListener listener;
	private PlayerStatus status;
	private Track track;
	
	public AbstractPlayer() {
		super();

		this.listener = null;
		this.status = PlayerStatus.NO_TRACK;
		this.track = null;
		
	}
	
	@Override
	public void specifyListener(TrackFinishedListener listener) {
		this.listener = listener;
	}
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public PlayerStatus currentStatus() {
		return status;
	}

	@Override
	public Track actualTrack() {
		return track;
	}
	
	@Override
	public Duration currentTime() {
		if (status.isNotPlayingTrack()) {
			throw new IllegalStateException("Not playing track");
		}
			
		Duration currentTime = doCurrentTime();
		if (currentTime.greaterThan(track.getDuration())) {
			LOG.warn("Current time is greater than track lenght");
		}
		return currentTime;
	}

	/**
	 * Returns the current time of the currently played track.
	 * Never gets called if no track beeing played.
	 * 
	 * @return
	 */
	protected abstract Duration doCurrentTime();
	
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public synchronized void startPlaying(Track track)  {
		if (status.isPlayingTrack()) {
			throw new IllegalStateException("Already playing track");
		}

		doStartPlaying(track);

		this.status = PlayerStatus.PLAYING;
		this.track = track;

		fireValueChangedEvent();
	}

	/**
	 * Start playing given track with given file. No need to additional checks or
	 * stop.
	 * 
	 * @param track
	 * @param file
	 * @
	 */
	protected abstract void doStartPlaying(Track track) ;
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public synchronized void stop() {
		if (status.isNotPlayingTrack()) {
			throw new IllegalStateException("Not playing track");
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
		if (status.isNotPlaying()) {
			throw new IllegalStateException("Not playing");
		}

		status = PlayerStatus.PAUSED;

		doPausePlaying();
		fireValueChangedEvent();
	}

	/**
	 * Pauses playing current track. No need to aditional checks.
	 */
	protected abstract void doPausePlaying();
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public synchronized void resume() {
		if (status.isNotPaused()) {
			throw new IllegalStateException("Not paused");
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
		if (status.isNotPlayingTrack()) {
			throw new IllegalStateException("Not playing track");
		}

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

	protected void trackFinished()  {
		if (status.isNotPlayingTrack()) {
			throw new IllegalStateException("Not playing track");
		}

		doTrackFinished();
		status = PlayerStatus.NO_TRACK;
		
		if (listener != null) {
			listener.trackOver(track);
		}
		
		fireValueChangedEvent();
	}

	/**
	 * Marks as finished playing track. No need to aditional checks.
	 */
	protected abstract void doTrackFinished();

}