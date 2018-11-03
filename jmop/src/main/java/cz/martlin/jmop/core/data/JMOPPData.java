package cz.martlin.jmop.core.data;

import cz.martlin.jmop.core.playlist.PlaylistProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class JMOPPData {
	private final PlaylistProperty playlist;
	private final ObjectProperty<Track> track;
	
	public JMOPPData() {
		this.playlist = new PlaylistProperty();
		this.track = new SimpleObjectProperty<>();
	}

	public PlaylistProperty playlistProperty() {
		return playlist;
	}

	public ObjectProperty<Track> trackProperty() {
		return track;
	}

}
