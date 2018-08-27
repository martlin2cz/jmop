package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.WorksWithPlaylist;

public interface BasePlaylister extends WorksWithPlaylist {
	public Track previous();
	public Track next();
	
	public void setPlaylist(BetterPlaylistRuntime runtime);
	
}
