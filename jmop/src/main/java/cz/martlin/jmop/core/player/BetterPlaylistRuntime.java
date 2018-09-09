package cz.martlin.jmop.core.player;

import java.util.List;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
@Deprecated
public class BetterPlaylistRuntime extends BasicPlaylistRuntime {

	private Tracklist allTracks;

	public BetterPlaylistRuntime() {
		super();
	}

	public BetterPlaylistRuntime(Track track) {
		super(track);
	}

	public BetterPlaylistRuntime(List<Track> tracks) {
		super(tracks);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Track getNextToPlayOrNull() {
		final boolean hasNext = hasNextToPlay();

		if (hasNext) {
			return getNextToPlay();
		} else {
			return null;
		}

	}

	public Track getLastPlayedOrNull() {
		final boolean hasPrev = hasLastPlayed();

		if (hasPrev) {
			return getLastPlayed();
		} else {
			return null;
		}
	}

	public Track toNextOrAnother() {
		final boolean hasNext = hasNextToPlay();

		if (!hasNext) {
			Track some = some();
			getRemaining().add(some);
		}

		return toNext();
	}

	public Track toPreviousOrAnother() {
		final boolean hasPrev = hasLastPlayed();

		if (hasPrev) {
			Track some = some();
			getPlayed().add(some);
		}

		return toPrevious();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private Track some() {
		List<Track> all = allTracks.getTracks();

		// TODO or just simply first?
		return getRandom(all);
	}

	private Track getRandom(List<Track> list) {
		int size = list.size();
		int index = (int) (size * Math.random());
		return list.get(index);
	}

}
