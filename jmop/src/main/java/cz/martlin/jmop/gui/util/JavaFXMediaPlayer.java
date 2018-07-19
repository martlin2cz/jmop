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
	private final TrackPlayedHandler handler;
	private final MediaPlayerGuiReporter reporter;
	private MediaPlayer mediaPlayer;

	public JavaFXMediaPlayer(BaseLocalSource local, TrackPlayedHandler handler, MediaPlayerGuiReporter reporter) {
		super(local);

		this.handler = handler;
		this.reporter = reporter;
	}

	@Override
	public void playWAVfile(File file, Track track) {
		URI uri = file.toURI();
		String path = uri.toString();

		Media media = new Media(path);
		mediaPlayer = new MediaPlayer(media);
		setupBindings(track);

		mediaPlayer.play();
	}

	private void setupBindings(Track track) {
		mediaPlayer.setOnEndOfMedia(() -> handler.trackPlayed(track));
		reporter.durationProperty().bind(mediaPlayer.currentTimeProperty());
		reporter.statusProperty().bind(mediaPlayer.statusProperty());
	}

	@Override
	public void stopPlaying() {
		mediaPlayer.stop();
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
