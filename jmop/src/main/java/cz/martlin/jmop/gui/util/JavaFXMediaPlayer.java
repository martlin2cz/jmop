package cz.martlin.jmop.gui.util;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.player.AbstractPlayer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JavaFXMediaPlayer extends AbstractPlayer {
	public static final TrackFileFormat PLAYER_FORMAT = TrackFileFormat.WAV;

	private MediaPlayer mediaPlayer;

	public JavaFXMediaPlayer(BaseLocalSource local) {
		super(local, PLAYER_FORMAT);
	}

	@Override
	protected void doStartPlaying(Track track, File file) {
		URI uri = file.toURI();
		String path = uri.toString();

		Media media = new Media(path);
		mediaPlayer = new MediaPlayer(media);
		mediaPlayer.setOnEndOfMedia(() -> onPlayed(track));

		mediaPlayer.play();
	}

	@Override
	protected void doStopPlaying() {
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

}
