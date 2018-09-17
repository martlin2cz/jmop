package cz.martlin.jmop.core.data;

import java.util.ArrayList;
import java.util.List;

public class Tracklist {
	private final List<Track> tracks;

	public Tracklist() {
		super();
		this.tracks = new ArrayList<>();
	}

	public Tracklist(List<Track> tracks) {
		super();
		this.tracks = tracks;
	} //////////////////////////////////////////////////////////////////////////////////////

	public List<Track> getTracks() {
		return tracks;
	}

	public int count() {
		return tracks.size();
	}

	//////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tracks == null) ? 0 : tracks.hashCode());
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
		Tracklist other = (Tracklist) obj;
		if (tracks == null) {
			if (other.tracks != null)
				return false;
		} else if (!tracks.equals(other.tracks))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Tracklist" + tracks + "";
	}

	//////////////////////////////////////////////////////////////////////////////////////
	public String toHumanString() {
		StringBuilder stb = new StringBuilder();

		tracks.forEach((t) -> stb.append("  " + t.getTitle() + "\n"));

		return stb.toString();
	}

}
