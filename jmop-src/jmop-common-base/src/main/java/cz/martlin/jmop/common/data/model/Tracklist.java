package cz.martlin.jmop.common.data.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracklist is simply immutable list of tracks.
 * 
 * @author martin
 *
 */
public class Tracklist {
	private final List<Track> tracks;

	public Tracklist() {
		super();
		this.tracks = new ArrayList<>();
	}

	public Tracklist(List<Track> tracks) {
		super();
		this.tracks = new ArrayList<>(tracks);
	}

	//////////////////////////////////////////////////////////////////////////////////////

	public List<Track> getTracks() {
		return tracks;
	}

	public int count() {
		return tracks.size();
	}

	public Track getTrack(int index) {
		return tracks.get(index);
	}
	
	public List<Track> subList(int start, int end) {
		return tracks.subList(start, end);
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
		return "Tracklist" + tracks + ""; //$NON-NLS-1$ //$NON-NLS-2$
	}

	//////////////////////////////////////////////////////////////////////////////////////
	public String toHumanString() {
		StringBuilder stb = new StringBuilder();

		tracks.forEach((t) -> stb.append("  " + t.getTitle() + "\n")); //$NON-NLS-1$ //$NON-NLS-2$

		return stb.toString();
	}

}
