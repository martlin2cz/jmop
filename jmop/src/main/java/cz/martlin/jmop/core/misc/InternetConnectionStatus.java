package cz.martlin.jmop.core.misc;

import java.util.Calendar;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class InternetConnectionStatus {

	private final BooleanProperty offline;
	private Calendar offlineSince;

	public InternetConnectionStatus() {
		this.offline = new SimpleBooleanProperty();
	}

	public BooleanProperty getOfflineProperty() {
		return offline;
	}

	public boolean isOffline() {
		checkOfflineTimeout();
		return offline.get();
	}

	public void markOffline() {
		offline.set(true);
		offlineSince = Calendar.getInstance();
	}

	private void checkOfflineTimeout() {
		boolean is = isTimeOut();
		if (is) {
			offline.set(false);
		}
	}

	private boolean isTimeOut() {
		// TODO is (current time - OFFLINE_TIMOUT) > offlineSince?
		return false;
	}

}
