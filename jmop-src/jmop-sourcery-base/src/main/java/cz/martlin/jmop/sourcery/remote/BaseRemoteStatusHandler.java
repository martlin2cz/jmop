package cz.martlin.jmop.sourcery.remote;

import cz.martlin.jmop.core.misc.BaseUIInterractor;

/**
 * The status handler (manger) of the remote.
 * 
 * @author martin
 *
 */
public interface BaseRemoteStatusHandler {

	/**
	 * Check whether the querier is ready to use.
	 * 
	 * @param interactor
	 * @return
	 */
	boolean checkQuerier(BaseUIInterractor interactor);

	/**
	 * Checks whether the downloader is ready to use.
	 * 
	 * @param interactor
	 * @return
	 */
	boolean checkDownloader(BaseUIInterractor interactor);

	/**
	 * Checks whether the converter is ready to use.
	 * 
	 * @param interactor
	 * @return
	 */
	boolean checkConverter(BaseUIInterractor interactor);

}
