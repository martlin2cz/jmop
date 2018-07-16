package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;

public class OfflinePlaylister implements BasePlaylister {

	private BetterPlaylistRuntime playlist;

	public OfflinePlaylister() {
		super();
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
		return playlist.toNextOrAnother();
	}

}
