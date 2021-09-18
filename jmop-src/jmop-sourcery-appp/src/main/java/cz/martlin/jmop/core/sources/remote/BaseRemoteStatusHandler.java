package cz.martlin.jmop.core.sources.remote;

import cz.martlin.jmop.core.misc.BaseUIInterractor;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseRemoteStatusHandler {
	public boolean isInstalled(BaseUIInterractor interactor) throws JMOPSourceException;

	public boolean install(BaseUIInterractor interactor) throws JMOPSourceException;

	public boolean checkQuerier(BaseUIInterractor interactor) throws JMOPSourceException;

	public boolean checkDownloader(BaseUIInterractor interactor) throws JMOPSourceException;

	public boolean checkConverter(BaseUIInterractor interactor) throws JMOPSourceException;

}
