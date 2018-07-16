package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.Sources;

public class OnlinePlaylister implements BasePlaylister {
	private final Sources sources;
	private BetterPlaylistRuntime playlist;

	public OnlinePlaylister(Sources sources) {
		super();
		this.sources = sources;
	}
	
	@Override
	public void setPlaylist(BetterPlaylistRuntime playlist) {
		this.playlist = playlist;
	}

	@Override
	public Track previous() {
		return playlist.toPreviousOrAnother();
	}

	@Override
	public Track next() {
		Track current = playlist.getCurrentlyPlayed();
		sources.prepareNextOf(current, playlist);

		return playlist.toNextOrAnother();
	}

}
