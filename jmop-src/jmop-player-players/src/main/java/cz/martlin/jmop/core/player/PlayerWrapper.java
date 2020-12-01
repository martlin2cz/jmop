package cz.martlin.jmop.core.player;

import java.util.function.Consumer;

import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.BaseWrapper;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

/**
 * Wrapper for {@link BasePlayer}. Specifies properties for the status (in play
 * mode, stopped, paused), current track and duration.
 * 
 * To specify what to do, when the track is over, use
 * {@link #setOnTrackPlayed(Consumer)}.
 * 
 * @author martin
 *
 */
public class PlayerWrapper implements BaseWrapper<BasePlayer> {

	private final BasePlayer player;

	private final BooleanProperty stoppedProperty;
	private final BooleanProperty pausedProperty;
	private final ObjectProperty<Track> trackProperty;
	private final ObjectProperty<Duration> timeProperty;
	private final BooleanProperty inPlayModeProperty;

	private Consumer<Track> onTrackPlayed;

	public PlayerWrapper(BasePlayer player) {
		this.player = player;

		this.stoppedProperty = new SimpleBooleanProperty();
		this.pausedProperty = new SimpleBooleanProperty();
		this.trackProperty = new SimpleObjectProperty<>();
		this.timeProperty = new SimpleObjectProperty<>();
		this.inPlayModeProperty = new SimpleBooleanProperty();

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

	public BooleanProperty inPlayModeProperty() {
		return inPlayModeProperty;
	}

	///////////////////////////////////////////////////////////////////////////////
	

	///////////////////////////////////////////////////////////////////////////////
	/**
	 * Handles changed player. If track is over fires {@link #onTrackPlayed}, if
	 * not updates all the properties.
	 */
	private void playerChanged() {
		boolean over = player.isPlayOver();

		if (over) {
			fireTrackPlayed();
		} else {
			updateProperties();
		}
	}

	/**
	 * Updates values of properties to match the player.
	 */
	private void updateProperties() {
		boolean stopped = player.isStopped();
		stoppedProperty.set(stopped);

		boolean paused = player.isPaused();
		pausedProperty.set(paused);

		Track track = player.getPlayedTrack();
		trackProperty.set(track);

		Duration time = player.currentTime();
		timeProperty.set(time);

		boolean inPlayMode = inPlayModeProperty.get() || !stopped;
		inPlayModeProperty.set(inPlayMode);
	}

	/**
	 * Fires the {@link #onTrackPlayed}.
	 */
	private void fireTrackPlayed() {
		Track track = player.getPlayedTrack();

		onTrackPlayed.accept(track);
	}

}
