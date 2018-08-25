package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Duration;

public class CoreGuiDescriptor {
	private final JMOPPlayer jmop;
	private final ObjectProperty<Bundle> currentBundleProperty;

	public CoreGuiDescriptor(JMOPPlayer jmop) {
		super();
		this.jmop = jmop;
		
		this.currentBundleProperty = new SimpleObjectProperty<>();
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

	public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
		return jmop.getPlaying().getPlaylister().getPlayer().currentTimeProperty();
	}

	public ReadOnlyObjectProperty<DownloaderTask> currentDownloadTaskProperty() {
		return jmop.getSources().getPreparer().currentTaskProperty();
	}

	public ReadOnlyObjectProperty<Bundle> currentBundleProperty() {
		ObjectProperty<Playlist> playlistProperty = jmop.currentPlaylistProperty();
		playlistProperty.addListener((observable, oldValue, newValue) -> //
		currentBundleProperty.set(newValue.getBundle()));

		return currentBundleProperty();
	}

	public ReadOnlyObjectProperty<Playlist> currentPlaylistProperty() {
		return jmop.currentPlaylistProperty();
	}

	public BooleanBinding hasActiveBundleAndPlaylistProperty() {
		return currentPlaylistProperty().isNotNull();
	}

}
