package cz.martlin.jmop.common.data.misc;

import java.net.URL;

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
	/** @deprecated replaced by #url */
	@Deprecated
	private final String identifier;
	private final String title;
	private final String description;
	private final Duration duration;
	private final URL url;

	@Deprecated
	public TrackData(String identifier, String title, String description, Duration duration) {
		super();
		this.identifier = identifier;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.url = null;
	}
	

	public TrackData(String title, String description, Duration duration, URL url) {
		super();
		this.identifier = null;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.url = url;
	}

	public TrackData(String identifier, String title, String description, Duration duration, URL url) {
		super();
		this.identifier = identifier;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.url = url;
	}


	public String getIdentifier() {
		return identifier;
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

	public URL getURL() {
		return url;
	}
	
	@Override
	public String toString() {
		return "TrackData [identifier=" + identifier + ", title=" + title + ", description=" + "..."
				+ ", duration=" + duration + "]";
	}
	
	

}
