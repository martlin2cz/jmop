package cz.martlin.jmop.common.data.misc;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.common.data.model.Track;
import javafx.util.Duration;

/**
 * Ann immutable raw data of the audiotrack. Convert to actual {@link Track}
 * ASAP.
 * 
 * @author martin
 *
 */
public class TrackData {
	private final String title;
	private final String description;
	private final Duration duration;
	private final URI uri;
	private final File file;

	public TrackData(String title, String description, Duration duration, URI uri) {
		super();
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.uri = uri;
		this.file = null;
	}

	public TrackData(String title, String description, Duration duration, URI uri, File file) {
		super();
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.uri = uri;
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Duration getDuration() {
		return duration;
	}

	public URI getURI() {
		return uri;
	}
	
	public File getTrackFile() {
		return file;
	}
	
	@Override
	public String toString() {
		return "TrackData [uri=" + uri + ", title=" + title + ", description=" + "..."
				+ ", duration=" + duration + "]";
	}
	

}
