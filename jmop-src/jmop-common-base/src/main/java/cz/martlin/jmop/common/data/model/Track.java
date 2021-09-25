package cz.martlin.jmop.common.data.model;

import cz.martlin.jmop.common.data.misc.WithPlayedMarker;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * The data structure for track. Track contains reference to bundle,
 * idenfitifer, title, description length (duration). Instance of this class is
 * mutable.
 * 
 * @author martin
 *
 */
public class Track implements Comparable<Track>, WithPlayedMarker {
	private Bundle bundle;
	private String identifier; //TODO replace by uri
	private String title;
	private String description;
	private Duration duration;
	private Metadata metadata;

	public Track(Bundle bundle, String identifier, String title, String description, Duration duration,
			Metadata metadata) {
		super();
		this.bundle = bundle;
		this.identifier = identifier;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.metadata = metadata;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int compareTo(Track another) {
		return this.title.compareToIgnoreCase(another.title);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Track other = (Track) obj;
		if (bundle == null) {
			if (other.bundle != null)
				return false;
		} else if (!bundle.getName().equals(other.bundle.getName()))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Track [bundle=" + bundle.getName() + ", identifier=" + identifier + ", title=" + title //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ ", description=" + "..." + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public String toHumanString() {
		return title + " (" + DurationUtilities.toHumanString(duration) + ")";
	}
	
	@Override
	public void played(Duration time) {
		metadata = metadata.played(time);
	}
	

}
