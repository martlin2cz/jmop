package cz.martlin.jmop.core.player;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;
import cz.martlin.jmop.core.sources.local.misc.locator.BaseTrackFileLocator;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.beans.value.ChangeListener;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Player encapsulating JavaFX {@link MediaPlayer} instance.
 * 
 * @author martin
 *
 */
public class JavaFXMediaPlayer extends AbstractPlayer {
	public static final TrackFileFormat PLAYER_FORMAT = TrackFileFormat.WAV;

	private MediaPlayer mediaPlayer;
	private Duration currentTime;

	private Runnable endListener;
	private ChangeListener<? super Duration> timeListener;

	static {
		initializeFX();
	}

	public JavaFXMediaPlayer(XXX_BaseLocalSource local, BaseTrackFileLocator locator) {
		super(local, locator, PLAYER_FORMAT);
	}

	@Override
	public Duration currentTime() {
		return currentTime;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected void doStartPlaying(Track track, File file) {
		URI uri = file.toURI();
		String path = uri.toString();

		Media media = new Media(path);
		mediaPlayer = new MediaPlayer(media);

		endListener = () -> trackFinished();
		mediaPlayer.setOnEndOfMedia(endListener);

		timeListener = (observable, oldValue, newValue) -> timeChanged(newValue);
		mediaPlayer.currentTimeProperty().addListener(timeListener);

		mediaPlayer.play();
	}

	@Override
	protected void doStopPlaying() {
		mediaPlayer.setOnEndOfMedia(null);
		mediaPlayer.currentTimeProperty().removeListener(timeListener);

		mediaPlayer.stop();
		mediaPlayer = null;
		currentTime = null;
	}

	@Override
	protected void doPausePlaying() {
		mediaPlayer.pause();
	}

	@Override
	protected void doResumePlaying() {
		mediaPlayer.play();
	}

	@Override
	protected void doSeek(Duration to) {
		mediaPlayer.seek(to);

	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Force-loads the FX framework.
	 */
	private static void initializeFX() {
		new JFXPanel();
	}

	/**
	 * Handles current time change.
	 * 
	 * @param to
	 */
	private void timeChanged(Duration to) {
		this.currentTime = to;
		fireValueChangedEvent();
	}

}
