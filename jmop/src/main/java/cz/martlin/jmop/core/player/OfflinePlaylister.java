package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;

public class OfflinePlaylister implements BasePlaylister {

	private final BetterPlaylist playlist;

	public OfflinePlaylister(BetterPlaylist playlist) {
		super();
		this.playlist = playlist;
	}

	@Override
	public Track previous() {
		return playlist.toPreviousOrAnother();
	}

	@Override
	public Track next() {
		return playlist.toNextOrAnother();
	}

}
