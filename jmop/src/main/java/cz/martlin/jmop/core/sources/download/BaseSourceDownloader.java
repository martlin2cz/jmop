package cz.martlin.jmop.core.sources.download;

import cz.martlin.jmop.core.data.Track;

public interface BaseSourceDownloader {

	public boolean download(Track track) throws Exception;


}
