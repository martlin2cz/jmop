package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;

public class StaticPlaylistPlaylister extends SimplePlaylister {

	public StaticPlaylistPlaylister() {
		super();
	}

	@Override
	public void addTrack(Track track) {
		PlaylistRuntime runtime = getRuntime();
		
		runtime.append(track);
	}

}
