package cz.martlin.jmop.sourcery.remote;

import cz.martlin.jmop.core.misc.BaseUIInterractor;

public interface BaseRemoteStatusHandler {

	boolean checkQuerier(BaseUIInterractor interactor);

	boolean checkDownloader(BaseUIInterractor interactor);

	boolean checkConverter(BaseUIInterractor interactor);

}
