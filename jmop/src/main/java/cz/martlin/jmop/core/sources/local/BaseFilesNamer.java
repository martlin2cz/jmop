package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public interface BaseFilesNamer {
	public File fileOfTrack(Bundle bundle, Track track);
	
	//TODO 
}
