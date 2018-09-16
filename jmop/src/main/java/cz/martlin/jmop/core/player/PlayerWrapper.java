package cz.martlin.jmop.core.player;

import java.util.function.Consumer;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class PlayerWrapper implements BaseWrapper<BasePlayer> {

	private final BasePlayer player;

	private final BooleanProperty stoppedProperty;
	private final BooleanProperty pausedProperty;
	private final ObjectProperty<Track> trackProperty;
	private final ObjectProperty<Duration> timeProperty;

	private Consumer<Track> onTrackPlayed;

	public PlayerWrapper(BasePlayer player) {
		this.player = player;

		this.stoppedProperty = new SimpleBooleanProperty();
		this.pausedProperty = new SimpleBooleanProperty();
		this.trackProperty = new SimpleObjectProperty<>();
		this.timeProperty = new SimpleObjectProperty<>();
		
		initBindings();
	}

	@Override
	public void initBindings() {
		player.addListener((value) -> playerChanged());
	}

	public void setOnTrackPlayed(Consumer<Track> onTrackPlayed) {
		this.onTrackPlayed = onTrackPlayed;
	}

	///////////////////////////////////////////////////////////////////////////////

	public ReadOnlyBooleanProperty stoppedProperty() {
		return stoppedProperty;
	}

	public ReadOnlyBooleanProperty pausedProperty() {
		return pausedProperty;
	}

	public ReadOnlyObjectProperty<Track> trackProperty() {
		return trackProperty;
	}

	public ReadOnlyObjectProperty<Duration> timeProperty() {
		return timeProperty;
	}
	///////////////////////////////////////////////////////////////////////////////

	public synchronized void startPlaying(Track track) throws JMOPSourceException {
		player.startPlaying(track);
	}

	public synchronized void stop() {
		player.stop();
	}

	public synchronized void pause() {
		player.pause();
	}

	public synchronized void resume() {
		player.resume();
	}

	public void seek(Duration to) {
		player.seek(to);
	}

	///////////////////////////////////////////////////////////////////////////////
	private void playerChanged() {
		boolean over = player.isPlayOver();

		if (over) {
			fireTrackPlayed();
		} else {
			updateVariables();
		}
	}

	private void updateVariables() {
		boolean stopped = player.isStopped();
		stoppedProperty.set(stopped);

		boolean paused = player.isPaused();
		pausedProperty.set(paused);

		Track track = player.getPlayedTrack();
		trackProperty.set(track);

		Duration time = player.currentTime();
		timeProperty.set(time);
	}

	private void fireTrackPlayed() {
		Track track = player.getPlayedTrack();

		onTrackPlayed.accept(track);
	}

}
