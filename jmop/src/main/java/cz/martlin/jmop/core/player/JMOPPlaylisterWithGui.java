package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.WorksWithPlaylist;
import cz.martlin.jmop.core.sources.AutomaticSavesPerformer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class JMOPPlaylisterWithGui extends JMOPPlaylister implements WorksWithPlaylist {
	final ObjectProperty<Track> currentTrackProperty;
	final ObjectProperty<Track> previousTrackProperty;
	final ObjectProperty<Track> nextTrackProperty;
	final BooleanProperty stoppedProperty;
	final BooleanProperty pausedProperty;

	public JMOPPlaylisterWithGui(BasePlayer player, TrackPreparer preparer, InternetConnectionStatus connection,
			AutomaticSavesPerformer saver) {
		super(player, preparer, connection, saver);

		this.currentTrackProperty = new SimpleObjectProperty<>();
		this.previousTrackProperty = new SimpleObjectProperty<>();
		this.nextTrackProperty = new SimpleObjectProperty<>();
		this.stoppedProperty = new SimpleBooleanProperty(true);
		this.pausedProperty = new SimpleBooleanProperty(true);
	}

	public ObjectProperty<Track> currentTrackProperty() {
		return currentTrackProperty;
	}

	public ObjectProperty<Track> previousTrackProperty() {
		return previousTrackProperty;
	}

	public ObjectProperty<Track> nextTrackProperty() {
		return nextTrackProperty;
	}

	public BooleanProperty stoppedProperty() {
		return stoppedProperty;
	}

	public BooleanProperty pausedProperty() {
		return pausedProperty;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void play() {
		super.play();
		updateProperties(false, false);
	}

	public void stop() {
		super.stop();
		updateProperties(true, false);
	}

	public void toNext() {
		super.toNext();
		updateProperties(false, false);
	}

	public void toPrevious() {
		super.toPrevious();
		updateProperties(false, false);
	}

	public void pause() {
		super.pause();
		updateProperties(false, true);
	}

	public void resume() {
		super.resume();
		updateProperties(false, false);
	}
	

	public void seek(Duration to) {
		super.seek(to);
	}

	public void appendTrack(Track track) {
		super.appendTrack(track);
		updateProperties(stoppedProperty.get(), pausedProperty.get());

	}

	/////////////////////////////////////////////////////////////////////////////////////

	void updateProperties(boolean stopped, boolean paused) {
		BetterPlaylistRuntime playlist = getPlaylist();

		Track current = playlist.getCurrentlyPlayed();
		currentTrackProperty.set(current);

		Track next = playlist.getNextToPlayOrNull();
		nextTrackProperty.set(next);

		Track previous = playlist.getLastPlayedOrNull();
		previousTrackProperty.set(previous);

		stoppedProperty.set(stopped);
		pausedProperty.set(paused);
	}


}
