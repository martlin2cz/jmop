package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.download.PreparerTask;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.util.Duration;
@Deprecated
public class CoreGuiDescriptor {
	private final JMOPPlayer jmop;

	public CoreGuiDescriptor(JMOPPlayer jmop) {
		super();
		this.jmop = jmop;
	}

	public ReadOnlyObjectProperty<Track> currentTrackProperty() {
		return jmop.getPlaying().getPlaylister().currentTrackProperty();
	}

	public ReadOnlyObjectProperty<Track> previousTrackProperty() {
		return jmop.getPlaying().getPlaylister().previousTrackProperty();
	}

	public ReadOnlyObjectProperty<Track> nextTrackProperty() {
		return jmop.getPlaying().getPlaylister().nextTrackProperty();
	}

	public ReadOnlyBooleanProperty stoppedProperty() {
		return jmop.getPlaying().getPlaylister().stoppedProperty();
	}

	public ReadOnlyBooleanProperty pausedProperty() {
		return jmop.getPlaying().getPlaylister().pausedProperty();
	}

	public ReadOnlyBooleanProperty hasPreviousProperty() {
		return jmop.getPlaying().getPlaylister().hasPreviousProperty();
	}
	
	public ReadOnlyBooleanProperty hasNextProperty() {
		return jmop.getPlaying().getPlaylister().hasNextProperty();
	}
	
	
	public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
		return null;
//		return jmop.getPlaying().getPlaylister().getPlayer().currentTimeProperty();
	}

	@Deprecated
	public ReadOnlyObjectProperty<PreparerTask> currentDownloadTaskProperty() {
		return jmop.getSources().getPreparer().currentTaskProperty();
	}
	
	public ObservableList<PreparerTask> currentDownloadTasksProperty() {
		return jmop.getSources().getPreparer().currentTasks();
	}

	public ReadOnlyObjectProperty<Playlist> currentPlaylistProperty() {
		return jmop.currentPlaylistProperty();
	}

	public BooleanBinding hasActiveBundleAndPlaylistProperty() {
		return currentPlaylistProperty().isNotNull();
	}

	public BooleanBinding isPreparpingProperty() {
		return Bindings.isNotEmpty(currentDownloadTasksProperty());
	}

}
