package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.operation.base.OperationWrapper;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Duration;

public class JMOPData {

	private final JMOPPlayer jmop;

	public JMOPData(JMOPPlayer jmop) {
		this.jmop = jmop;
	}

	public ReadOnlyObjectProperty<Playlist> playlistProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().playlistProperty();
	}

	public ReadOnlyBooleanProperty inPlayModeProperty() {
		return jmop.getPlaying().getEngine().getPlayer().inPlayModeProperty();
	}

	public ReadOnlyBooleanProperty hasSomeTrackProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().hasSomeTrackProperty();
	}

	public ReadOnlyObjectProperty<Track> currentTrackProperty() {
		return jmop.getPlaying().getEngine().getPlayer().trackProperty();
	}

	public ReadOnlyBooleanProperty stoppedProperty() {
		return jmop.getPlaying().getEngine().getPlayer().stoppedProperty();
	}

	public ReadOnlyBooleanProperty pausedProperty() {
		return jmop.getPlaying().getEngine().getPlayer().pausedProperty();
	}

	public ReadOnlyBooleanProperty hasPreviousProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().hasPreviousProperty();
	}

	public ReadOnlyBooleanProperty hasNextProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().hasNextProperty();
	}

	public ReadOnlyObjectProperty<Track> previousTrackProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().previousTrackProperty();
	}

	public ReadOnlyObjectProperty<Track> nextTrackProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().nextTrackProperty();
	}

	public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
		return jmop.getPlaying().getEngine().getPlayer().timeProperty();
	}


	public ObservableList<OperationWrapper<?, ?>> currentOperationsProperty() {
		return jmop.getSources().getPreparer().currentOperations();
	}

}
