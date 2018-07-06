package cz.martlin.jmop.core.player;

import java.util.List;

import cz.martlin.jmop.core.data.Track;

public class BetterPlaylist extends BasicPlaylist {

	public BetterPlaylist() {
	}

	public BetterPlaylist(Track track) {
		super(track);
	}

	public BetterPlaylist(List<Track> tracks) {
		super(tracks);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Track toNextOrAnother() {
		final boolean hasNext = hasNextToPlay();

		if (hasNext) {
			return toNext();
		} else {
			return toSome();
		}
	}

	public Track toPreviousOrAnother() {
		final boolean hasPrev = hasLastPlayed();

		if (hasPrev) {
			return toPrevious();
		} else {
			return toSome();
		}
	}

	private Track toSome() {
		List<Track> all = super.list();

		// TODO choose some strategy? first? random?

		return all.get(0);
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
