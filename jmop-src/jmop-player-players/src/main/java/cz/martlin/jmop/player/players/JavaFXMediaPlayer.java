package cz.martlin.jmop.player.players;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
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
public class JavaFXMediaPlayer extends AbstractTrackFilePlaingPlayer {
	private MediaPlayer mediaPlayer;

	private Runnable endListener;
//	private ChangeListener<? super Duration> timeListener;

	static {
		initializeFX();
	}

	public JavaFXMediaPlayer(TracksSource local) {
		super(local);
	}

	@Override
	public Duration doCurrentTime() {
		return mediaPlayer.getCurrentTime();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected void doStartPlaingFile(Track track, File file) {
		URI uri = file.toURI();
		String path = uri.toString();

		Media media = new Media(path);
		mediaPlayer = new MediaPlayer(media);

		endListener = () -> onTrackFinished();
		mediaPlayer.setOnEndOfMedia(endListener);

//		timeListener = (observable, oldValue, newValue) -> timeChanged(newValue);
//		mediaPlayer.currentTimeProperty().addListener(timeListener);

		mediaPlayer.play();
	}


	@Override
	protected void doStopPlaying() {
		mediaPlayer.setOnEndOfMedia(null);
//		mediaPlayer.currentTimeProperty().removeListener(timeListener);

		mediaPlayer.stop();
		mediaPlayer = null;
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
	
	@Override
	protected void doTrackFinished() {
		mediaPlayer = null;
	}
	

	private void onTrackFinished() {
		trackFinished();
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Force-loads the FX framework.
	 */
	private static void initializeFX() {
		new JFXPanel();
	}
//
//	/**
//	 * Handles current time change.
//	 * 
//	 * @param to
//	 */
//	private void timeChanged(Duration to) {
//		this.currentTime = to;
//		fireValueChangedEvent();
//	}

}
