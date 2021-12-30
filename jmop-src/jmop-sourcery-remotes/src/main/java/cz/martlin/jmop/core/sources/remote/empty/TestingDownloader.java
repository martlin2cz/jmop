package cz.martlin.jmop.core.sources.remote.empty;

import java.io.File;
import java.io.IOException;

import com.google.common.io.Files;

import cz.martlin.jmop.common.testing.resources.TestingResources;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;

public class TestingDownloader implements BaseDownloader {

	private final TrackFileFormat downloadFileFormat;

	public TestingDownloader(TrackFileFormat downloadFileFormat) {
		super();
		this.downloadFileFormat = downloadFileFormat;
	}

	@Override
	public TrackFileFormat downloadFormat() {
		return downloadFileFormat;
	}
	
	@Override
	public void download(String url, File target) throws JMOPSourceryException {
		try {
			copyTestingFileTo(target);
		} catch (IOException e) {
			throw new JMOPSourceryException("Could not prepare the file", e);
		}
	}

	/**
	 * Copies testing file to given file.
	 * 
	 * @param targetFile
	 * @
	 * @throws IOException
	 */
	public void copyTestingFileTo(File targetFile) throws IOException {
		File file = TestingResources.prepareSampleTrack(this, downloadFileFormat);
				
		Files.copy(file, targetFile);	
	}

	/////////////////////////////////////////////////////////////////////////////////////

}
