package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;

public class JMOPChecker {
	private final BaseSourceDownloader downloader;
	private final BaseSourceConverter converter;

	public JMOPChecker(BaseSourceDownloader downloader, BaseSourceConverter converter) {
		super();
		this.downloader = downloader;
		this.converter = converter;
	}

	//TODO check current bundle/playlist/track/...
	
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
