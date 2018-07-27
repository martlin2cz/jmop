package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.download.DownloaderTask;
import javafx.beans.property.ReadOnlyObjectProperty;

public class CoreGuiDescriptor {
	private final JMOPPlayer jmop;

	public CoreGuiDescriptor(JMOPPlayer jmop) {
		super();
		this.jmop = jmop;
	}

	public ReadOnlyObjectProperty<Track> currentTrackProperty() {
		return jmop.getPlaying().getPlaylister().currentTrackProperty();
	}

	public ReadOnlyObjectProperty<DownloaderTask> currentDownloadTaskProperty() {
		return jmop.getSources().getPreparer().currentTaskProperty();
	}

}
