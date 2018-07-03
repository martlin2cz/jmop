package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.tracks.Track;

public interface BaseSourceConverter {
	
	public boolean convert(Track track) throws Exception;
	
}
