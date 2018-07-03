package cz.martlin.jmop.core.sources.download;

import java.io.File;

import cz.martlin.jmop.core.tracks.Track;

public interface BaseSourceDownloader {

	public File download(Track track) throws Exception;


}
