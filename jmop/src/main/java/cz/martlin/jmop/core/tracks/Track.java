package cz.martlin.jmop.core.tracks;

public class Track {
	private final TrackIdentifier identifier;
	private final String title;
	private final String description;

	public Track(TrackIdentifier identifier, String title, String description) {
		super();
		this.identifier = identifier;
		this.title = title;
		this.description = description;
	}

	public TrackIdentifier getIdentifier() {
		return identifier;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		return "Track [identifier=" + identifier + ", title=" + title + ", description=" + description + "]";
	}
}
