package cz.martlin.jmop.core.sources.remote.youtubedl;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.common.io.Files;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;
import cz.martlin.jmop.core.source.extprogram.ExternalProcessLongOperation;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;

public class YoutubeDLDownloader implements BaseDownloader {

	private static final TrackFileFormat DOWNLOAD_FILE_FORMAT = TrackFileFormat.OPUS;

	private final BaseRemoteSourceQuerier querier;
	private final BaseLocalSource local;

	public YoutubeDLDownloader(BaseRemoteSourceQuerier querier, BaseLocalSource local) {
		super();
		this.querier = querier;
		this.local = local;
	}

	@Override
	public BaseLongOperation<Track, Track> download(Track track, TrackFileLocation location)
			throws JMOPSourceException {
		AbstractProcessEncapsulation process = prepareProcess(track, location);

		String name = "Downloading";
		Track input = track;

		Supplier<Track> resultCreator = () -> track;
		Function<Track, String> dataToString = (t) -> t.toString();
		return new ExternalProcessLongOperation<Track, Track>(name, input, process, dataToString, resultCreator);
	}

	///////////////////////////////////////////////////////////////////////////
	private AbstractProcessEncapsulation prepareProcess(Track track, TrackFileLocation location)
			throws JMOPSourceException {

		File workingDirectory = Files.createTempDir(); // FIXME jmop temp dir?
		List<String> arguments = createArguments(track, location);
		AbstractProcessEncapsulation process = new YoutubeDLProcessEncapsulation(arguments, workingDirectory);
		return process;
	}

	private List<String> createArguments(Track track, TrackFileLocation location) throws JMOPSourceException {
		String path = createTargetFilePath(track, location);
		String url = createUrlOfTrack(track);
		return createCommandLine(url, path);
	}

	private List<String> createCommandLine(String url, String path) {
		return Arrays.asList( //
				"--newline", //
				"--extract-audio", "--audio-format", DOWNLOAD_FILE_FORMAT.getExtension(), //
				"--output", path, url);
	}

	private String createUrlOfTrack(Track track) throws JMOPSourceException {
		URL url = querier.urlOfTrack(track);
		return url.toExternalForm();
	}

	private String createTargetFilePath(Track track, TrackFileLocation location) throws JMOPSourceException {
		File tmpFile = createTargetFileFile(track, location);

		return tmpFile.getAbsolutePath();
	}

	private File createTargetFileFile(Track track, TrackFileLocation location) throws JMOPSourceException {
		return local.fileOfTrack(track, location, DOWNLOAD_FILE_FORMAT);
	}

}
