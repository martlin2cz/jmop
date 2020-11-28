package cz.martlin.jmop.core.player;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.AbstractTrackFileLocator;
import javafx.beans.value.ChangeListener;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.media.Media;
//import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * Player encapsulating JavaFX {@link MediaPlayer} instance.
 * 
 * @author martin
 *
 */
public class JavaFXMediaPlayer extends AbstractPlayer {
	public static final TrackFileFormat PLAYER_FORMAT = TrackFileFormat.WAV;

//	private MediaPlayer mediaPlayer;
	private Duration currentTime;

	private Runnable endListener;
	private ChangeListener<? super Duration> timeListener;

	static {
		initializeFX();
	}

	public JavaFXMediaPlayer(BaseLocalSource local, AbstractTrackFileLocator locator) {
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

//		Media media = new Media(path);
//		mediaPlayer = new MediaPlayer(media);

		endListener = () -> trackFinished();
//		mediaPlayer.setOnEndOfMedia(endListener);

		timeListener = (observable, oldValue, newValue) -> timeChanged(newValue);
//		mediaPlayer.currentTimeProperty().addListener(timeListener);

//		mediaPlayer.play();
		System.err.println("JavaFXMediaPlayer.doStartPlaying()");
	}

	@Override
	protected void doStopPlaying() {
//		mediaPlayer.setOnEndOfMedia(null);
//		mediaPlayer.currentTimeProperty().removeListener(timeListener);

//		mediaPlayer.stop();
//		mediaPlayer = null;
		System.err.println("JavaFXMediaPlayer.doStopPlaying()");
		currentTime = null;
	}

	@Override
	protected void doPausePlaying() {
//		mediaPlayer.pause();
		System.err.println("JavaFXMediaPlayer.doPausePlaying()");
	}

	@Override
	protected void doResumePlaying() {
//		mediaPlayer.play();
		System.out.println("JavaFXMediaPlayer.doResumePlaying()");
	}

	@Override
	protected void doSeek(Duration to) {
//		mediaPlayer.seek(to);
		System.out.println("JavaFXMediaPlayer.doSeek()");

	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Force-loads the FX framework.
	 */
	private static void initializeFX() {
		System.out.println("JavaFXMediaPlayer.initializeFX()");
		//new JFXPanel();
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
