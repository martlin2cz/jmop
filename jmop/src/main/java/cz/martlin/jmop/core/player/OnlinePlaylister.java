package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.sources.Sources;
import cz.martlin.jmop.core.tracks.Track;

public class OnlinePlaylister implements BasePlaylister {
	private final Sources sources;
	private final BetterPlaylist playlist;

	public OnlinePlaylister(Sources sources, BetterPlaylist playlist) {
		super();
		this.sources = sources;
		this.playlist = playlist;
	}

	@Override
	public Track previous() {
		return playlist.toPreviousOrAnother();
	}

	@Override
	public Track next() {
		Track current = playlist.getCurrentlyPlayed();
		sources.prepareNextOf(current);

		return playlist.toNextOrAnother();
	}

}
