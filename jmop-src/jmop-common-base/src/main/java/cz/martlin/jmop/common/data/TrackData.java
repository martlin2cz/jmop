package cz.martlin.jmop.common.data;

import javafx.util.Duration;

/**
 * Ann immutable raw data of the audiotrack. Convert to actual {@link Track}
 * ASAP.
 * 
 * @author martin
 *
 */
public class TrackData {
	private final String identifier;
	private final String title;
	private final String description;
	private final Duration duration;

	public TrackData(String identifier, String title, String description, Duration duration) {
		super();
		this.identifier = identifier;
		this.title = title;
		this.description = description;
		this.duration = duration;
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

}
