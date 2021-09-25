package cz.martlin.jmop.common.data.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

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

	public Track getTrack(TrackIndex index) {
		int indx = index.getIndex();
		if (indx < 0 || indx >= count()) {
			Exception cause = new IndexOutOfBoundsException(indx);
			throw new JMOPRuntimeException("Playlist does not have track " + index + ", " //
					+ "it has only " + count() + " tracks.", cause);
		}

		return tracks.get(indx);
	}

	public List<Track> subList(TrackIndex start, TrackIndex end) {
		int startIndx = start.getIndex();
		int endIndx = end.getIndex();

		return tracks.subList(startIndx, endIndx);
	}

	public Map<TrackIndex, Track> asIndexedMap() {
		return IntStream.range(0, count()) //
				.mapToObj(i -> TrackIndex.ofIndex(i)) //
				.collect(Collectors.toMap(ti -> ti, //
						ti -> getTrack(ti), //
						(t1, t2) -> t1, //
						() -> new TreeMap<>()));
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
