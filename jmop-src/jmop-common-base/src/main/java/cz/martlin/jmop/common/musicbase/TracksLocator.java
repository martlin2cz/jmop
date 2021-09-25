package cz.martlin.jmop.common.musicbase;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;

public interface TracksLocator {
	
	public File trackFile(Track track);
}
