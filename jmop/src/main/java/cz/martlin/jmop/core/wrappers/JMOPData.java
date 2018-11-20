package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.operation.base.OperationWrapper;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Duration;

/**
 * The JMOP data wrapper, provides references to properties holding up-to-date
 * values of the internal state of the JMOP. Keep in mind that theese proeprties
 * are all read only. To modify them, use particullar actions of JMOPPlayer.
 * 
 * @author martin
 *
 */
public class JMOPData {

	private final JMOPPlayer jmop;

	public JMOPData(JMOPPlayer jmop) {
		this.jmop = jmop;
	}

	/**
	 * Current playlist.
	 * 
	 * @return
	 */
	public ReadOnlyObjectProperty<Playlist> playlistProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().playlistProperty();
	}

	/**
	 * Is JMOP in play mode (not in wellcome mode)?
	 * 
	 * @return
	 */
	public ReadOnlyBooleanProperty inPlayModeProperty() {
		return jmop.getPlaying().getEngine().getPlayer().inPlayModeProperty();
	}

	/**
	 * Has (current playlist) at least one track?
	 * 
	 * @return
	 */
	public ReadOnlyBooleanProperty hasSomeTrackProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().hasSomeTrackProperty();
	}

	/**
	 * Current track.
	 * 
	 * @return
	 */
	public ReadOnlyObjectProperty<Track> currentTrackProperty() {
		return jmop.getPlaying().getEngine().getPlayer().trackProperty();
	}

	/**
	 * Is playing stopped?
	 * 
	 * @return
	 */
	public ReadOnlyBooleanProperty stoppedProperty() {
		return jmop.getPlaying().getEngine().getPlayer().stoppedProperty();
	}

	/**
	 * Is playing paused?
	 * 
	 * @return
	 */
	public ReadOnlyBooleanProperty pausedProperty() {
		return jmop.getPlaying().getEngine().getPlayer().pausedProperty();
	}

	/**
	 * Has previous track?
	 * 
	 * @return
	 */
	public ReadOnlyBooleanProperty hasPreviousProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().hasPreviousProperty();
	}

	/**
	 * Has next track?
	 * 
	 * @return
	 */
	public ReadOnlyBooleanProperty hasNextProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().hasNextProperty();
	}

	/**
	 * Previous track.
	 * 
	 * @return
	 */
	public ReadOnlyObjectProperty<Track> previousTrackProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().previousTrackProperty();
	}

	/**
	 * Next track.
	 * 
	 * @return
	 */
	public ReadOnlyObjectProperty<Track> nextTrackProperty() {
		return jmop.getPlaying().getEngine().getPlaylister().nextTrackProperty();
	}

	/**
	 * Current time (duration).
	 * 
	 * @return
	 */
	public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
		return jmop.getPlaying().getEngine().getPlayer().timeProperty();
	}

	/**
	 * List of all, currently running operations.
	 * 
	 * @return
	 */
	public ObservableList<OperationWrapper<?, ?>> currentOperationsProperty() {
		return jmop.getSources().getPreparer().currentOperations();
	}

}
