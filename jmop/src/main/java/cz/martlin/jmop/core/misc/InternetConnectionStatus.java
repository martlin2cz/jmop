package cz.martlin.jmop.core.misc;

import java.time.Instant;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.config.BaseConfiguration;

/**
 * The class responsible for very simply network status monitoring. When some
 * trouble with the internet connection happens, call {@link #markOffline()}. To
 * avoid unnecessary network operations, before each of them check the
 * connection by calling {@link #isOffline()}. After specified timeout call to
 * {@link #isOffline()} marks connection back offline.
 * 
 * @author martin
 *
 */
public class InternetConnectionStatus extends ObservableObject<InternetConnectionStatus> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final int timeout;
	private boolean offline;
	private Calendar offlineSince;

	public InternetConnectionStatus(BaseConfiguration config) {
		this.timeout = config.getOfflineRetryTimeout();
		this.offline = false;
	}

	/**
	 * Is connection (probably) offline?
	 * 
	 * @return
	 */
	public boolean isOffline() {
		checkOfflineTimeout();
		return offline;
	}

	/**
	 * Marks the connection as offline.
	 */
	public void markOffline() {
		LOG.info("The internet connection is offline"); //$NON-NLS-1$

		offlineSince = Calendar.getInstance();

		boolean was = offline;
		if (!was) {
			offline = true;
			fireValueChangedEvent();
		}
	}

	/**
	 * Checks whether the connection is (probably) offline.
	 */
	private void checkOfflineTimeout() {
		boolean is = isOfflineTimeOut();
		if (is) {
			boolean was = offline;
			if (was) {
				LOG.info("The internet connection could be back online"); //$NON-NLS-1$

				offline = false;
				fireValueChangedEvent();
			}

		}
	}

	/**
	 * Timed out the offline status?
	 * 
	 * @return
	 */
	private boolean isOfflineTimeOut() {
		if (offlineSince != null) {
			return isOfflineMoreThanTimeout();
		} else {
			return true;
		}
	}

	/**
	 * Is the time since marked as offline more than the timeout given by
	 * configuration?
	 * 
	 * @return
	 */
	private boolean isOfflineMoreThanTimeout() {
		Instant now = Instant.now();
		Instant offlineAfterTimeout = offlineSince.toInstant().plusSeconds(timeout);

		return offlineAfterTimeout.isBefore(now);
	}

}
