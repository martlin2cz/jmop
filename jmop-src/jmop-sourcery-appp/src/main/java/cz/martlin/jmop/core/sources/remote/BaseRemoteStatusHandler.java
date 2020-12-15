package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.misc.BaseUIInterractor;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseRemoteStatusHandler {
	public boolean isInstalled(BaseUIInterractor interactor) throws JMOPMusicbaseException;

	public boolean install(BaseUIInterractor interactor) throws JMOPMusicbaseException;

	public boolean checkQuerier(BaseUIInterractor interactor) throws JMOPMusicbaseException;

	public boolean checkDownloader(BaseUIInterractor interactor) throws JMOPMusicbaseException;

	public boolean checkConverter(BaseUIInterractor interactor) throws JMOPMusicbaseException;

}
