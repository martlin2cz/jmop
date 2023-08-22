package cz.martlin.jmop.sourcery.remote;

/**
 * The remote source. Contains particular sub-components.
 * 
 * @author martin
 *
 */
public interface BaseRemoteSource {

	/**
	 * Returns the statuser (checks for the status).
	 * 
	 * @return
	 */
	BaseRemoteStatusHandler statuser();

	/**
	 * Returns the querier, search performer.
	 * 
	 * @return
	 */
	BaseRemoteSourceQuerier querier();

	/**
	 * Returns the downloader.
	 * 
	 * @return
	 */
	BaseDownloader downloader();

	/**
	 * Returns the converter.
	 * 
	 * @return
	 */
	BaseConverter converter();

}
