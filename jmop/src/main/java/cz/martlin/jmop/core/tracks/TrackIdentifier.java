package cz.martlin.jmop.core.tracks;

import cz.martlin.jmop.core.sources.SourceKind;

public class TrackIdentifier {
	private final SourceKind source;
	private final String identifier;

	public TrackIdentifier(SourceKind source, String identifier) {
		super();
		this.source = source;
		this.identifier = identifier;
	}

	public SourceKind getSource() {
		return source;
	}

	public String getIdentifier() {
		return identifier;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
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
		TrackIdentifier other = (TrackIdentifier) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		if (source != other.source)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TrackIdentifier [source=" + source + ", identifier=" + identifier + "]";
	}

}
