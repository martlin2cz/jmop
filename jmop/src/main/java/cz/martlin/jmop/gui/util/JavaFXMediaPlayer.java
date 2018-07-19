package cz.martlin.jmop.gui.util;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.TrackPlayedHandler;
import cz.martlin.jmop.core.player.WavPlayer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JavaFXMediaPlayer extends WavPlayer {
	private final MediaPlayerGuiReporter reporter;
	private TrackPlayedHandler handler;
	private MediaPlayer mediaPlayer;

	public JavaFXMediaPlayer(BaseLocalSource local,  MediaPlayerGuiReporter reporter) {
		super(local);

		this.reporter = reporter;
	}

	public void setHandler(TrackPlayedHandler handler) {
		this.handler = handler;
	}
	
	@Override
	public void playWAVfile(File file, Track track) {
		if (mediaPlayer != null) {
			stop(); //TODO it may be more of situations like this
		}
		
		URI uri = file.toURI();
		String path = uri.toString();

		Media media = new Media(path);
		mediaPlayer = new MediaPlayer(media);
		setupBindings(track);

		mediaPlayer.play();
	}

	private void setupBindings(Track track) {
		mediaPlayer.setOnEndOfMedia(() -> onTrackPlayed(track));
		reporter.durationProperty().bind(mediaPlayer.currentTimeProperty());
		reporter.statusProperty().bind(mediaPlayer.statusProperty());
	}

	private void onTrackPlayed(Track track) {
		if (handler != null) {
			handler.trackPlayed(track);
		}
	}

	@Override
	public void stopPlaying() {
		mediaPlayer.stop();
		mediaPlayer = null;
	}

	@Override
	public void pause() {
		mediaPlayer.pause();
	}

	@Override
	public void resume() {
		mediaPlayer.play();
	}
}
