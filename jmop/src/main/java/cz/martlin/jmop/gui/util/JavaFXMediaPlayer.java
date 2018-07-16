package cz.martlin.jmop.gui.util;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.core.player.WavPlayer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class JavaFXMediaPlayer extends WavPlayer  {
	MediaPlayer mediaPlayer;

	public JavaFXMediaPlayer(BaseLocalSource local) {
		super(local);
	}

	@Override
	public void playWAVfile(File file) {
		URI uri = file.toURI();
		String path = uri.toString();
		
		Media media = new Media(path);
		mediaPlayer = new MediaPlayer(media);
		
		mediaPlayer.play();
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
