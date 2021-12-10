package cz.martlin.jmop.core.sources.remote;

import java.io.File;

/**
 * The downloader. Performs of the download of the track file from provided url
 * down to given file.
 * 
 * @author martin
 *
 */
public interface BaseDownloader {

	public void download(String url, File target) throws JMOPSourceryException;

}
