package cz.martlin.jmop.sourcery.remote;

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

	/**
	 * Donwloads the track from the given url into given file.
	 * @param url
	 * @param target
	 * @throws JMOPSourceryException
	 */
	public void download(String url, File target) throws JMOPSourceryException;

	/**
	 * Returns the download format.
	 * 
	 * @return
	 */
	public TrackFileFormat downloadFormat();

}
