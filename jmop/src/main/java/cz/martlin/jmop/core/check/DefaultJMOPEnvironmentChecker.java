package cz.martlin.jmop.core.check;

import cz.martlin.jmop.core.sources.remote.BaseSourceConverter;
import cz.martlin.jmop.core.sources.remote.BaseSourceDownloader;

public class DefaultJMOPEnvironmentChecker implements BaseJMOPEnvironmentChecker {
	private final BaseSourceDownloader downloader;
	private final BaseSourceConverter converter;

	public DefaultJMOPEnvironmentChecker(BaseSourceDownloader downloader, BaseSourceConverter converter) {
		super();
		this.downloader = downloader;
		this.converter = converter;
	}

	//TODO check current bundle/playlist/track/...
	

	@Override
	public String doCheck() {
		String error = null;

		error = checkDownloader();
		if (error != null) {
			return error;
		}

		error = checkConverter();
		if (error != null) {
			return error;
		}

		return error;
	}

	private String checkDownloader() {
		boolean ok = downloader.check();
		if (!ok) {
			return "The downloader (" + downloader.getClass().getSimpleName() + ") seems to be not able to work.";
		} else {
			return null;
		}
	}

	private String checkConverter() {
		boolean ok = converter.check();
		if (!ok) {
			return "The converter (" + converter.getClass().getSimpleName() + ") seems to be not able to work.";
		} else {
			return null;
		}
	}

}
