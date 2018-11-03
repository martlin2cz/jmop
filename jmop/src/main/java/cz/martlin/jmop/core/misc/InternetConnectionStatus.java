package cz.martlin.jmop.core.misc;

import java.time.Instant;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.config.BaseConfiguration;

public class InternetConnectionStatus extends ObservableObject<InternetConnectionStatus> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final int timeout;
	private boolean offline;
	private Calendar offlineSince;

	public InternetConnectionStatus(BaseConfiguration config) {
		this.timeout = config.getOfflineRetryTimeout();
		this.offline = false;
	}

	public boolean isOffline() {
		checkOfflineTimeout();
		return offline;
	}

	public void markOffline() {
		LOG.info("The internet connection is offline");

		offlineSince = Calendar.getInstance();

		boolean was = offline;
		if (!was) {
			offline = true;
			fireValueChangedEvent();
		}
	}

	private void checkOfflineTimeout() {
		boolean is = isOfflineTimeOut();
		if (is) {
			boolean was = offline;
			if (was) {
				LOG.info("The internet connection could be back online");

				offline = false;
				fireValueChangedEvent();
			}

		}
	}

	private boolean isOfflineTimeOut() {
		if (offlineSince != null) {
			return isOfflineMoreThanTimeout();
		} else {
			return true;
		}
	}

	private boolean isOfflineMoreThanTimeout() {
		Instant now = Instant.now();
		Instant offlineAfterTimeout = offlineSince.toInstant().plusSeconds(timeout);

		return offlineAfterTimeout.isBefore(now);
	}

}
