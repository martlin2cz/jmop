package cz.martlin.jmop.core.playlister.playlisters;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.playlister.base.SimplePlaylister;

/**
 * Playlister completelly follows the playlist (i.e. runtime). 
 * @author martin
 *
 */
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
