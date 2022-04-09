package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.misc.BaseUIInterractor;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseRemoteStatusHandler {
	public boolean isInstalled(BaseUIInterractor interactor) ;

	public boolean install(BaseUIInterractor interactor) ;

	public boolean checkQuerier(BaseUIInterractor interactor) ;

	public boolean checkDownloader(BaseUIInterractor interactor) ;

	public boolean checkConverter(BaseUIInterractor interactor) ;

}
