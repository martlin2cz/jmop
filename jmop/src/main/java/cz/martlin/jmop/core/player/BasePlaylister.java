package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.tracks.Track;

public interface BasePlaylister {
	public Track previous();
	public Track next();
	
}
