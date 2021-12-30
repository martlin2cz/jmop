package cz.martlin.jmop.core.sources.remote;

import java.io.File;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * The downloader. Performs of the download of the track file from provided url
 * down to given file.
 * 
 * @author martin
 *
 */
public interface BaseDownloader {

	public void download(String url, File target) throws JMOPSourceryException;

	public TrackFileFormat downloadFormat();

}
