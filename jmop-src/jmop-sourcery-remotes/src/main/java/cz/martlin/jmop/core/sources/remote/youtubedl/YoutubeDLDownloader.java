package cz.martlin.jmop.core.sources.remote.youtubedl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.misc.ExternalProgramException;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;

public class YoutubeDLDownloader implements BaseDownloader {
	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	public static final TrackFileFormat DOWNLOAD_FILE_FORMAT = TrackFileFormat.MP3;

	private final BaseProgressListener listener;

	public YoutubeDLDownloader(BaseProgressListener listener) {
		super();
		this.listener = listener;
	}
	
	@Override
	public TrackFileFormat downloadFormat() {
		return DOWNLOAD_FILE_FORMAT;
	}

	@Override
	public void download(String url, File target) throws JMOPSourceryException {
		LOG.info("Downloading from {} to {} by the youtube-dl", url, target);
		
		List<String> arguments = createCommandLine(url, target);
		File workingDirectory = new File(".");

		AbstractProcessEncapsulation process = new YoutubeDLProcessEncapsulation(arguments, workingDirectory, target);

		try {
			process.run(listener);
		} catch (ExternalProgramException e) {
			throw new JMOPSourceryException("Download process failed", e);
		}
	}

	private List<String> createCommandLine(String url, File target) {
		String path = target.getAbsolutePath();
		return Arrays.asList( //
				"--newline", //
				"--extract-audio", "--audio-format", DOWNLOAD_FILE_FORMAT.fileExtension(), //
				"--output", path, url);
	}

}
