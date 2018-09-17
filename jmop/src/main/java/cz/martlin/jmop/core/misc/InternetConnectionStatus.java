package cz.martlin.jmop.core.misc;

import java.time.Instant;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.config.BaseConfiguration;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class InternetConnectionStatus {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final int timeout;
	private final BooleanProperty offline;
	private Calendar offlineSince;

	public InternetConnectionStatus(BaseConfiguration config) {
		this.timeout = config.getOfflineRetryTimeout();
		this.offline = new SimpleBooleanProperty();
	}

	public ReadOnlyBooleanProperty getOfflineProperty() {
		return offline;
	}

	public boolean isOffline() {
		checkOfflineTimeout();
		return offline.get();
	}

	public void markOffline() {
		LOG.info("The internet connection is offline");

		offline.set(true);
		offlineSince = Calendar.getInstance();
	}

	private void checkOfflineTimeout() {
		boolean is = isOfflineTimeOut();
		if (is) {
			boolean was = offline.get();
			if (!was) {
				LOG.info("The internet connection could be back online");
			}
			
			offline.set(false);
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
