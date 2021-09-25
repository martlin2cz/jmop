package cz.martlin.jmop.core.sources.remote.youtubedl;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.source.extprogram.AbstractProcessEncapsulation;
import cz.martlin.jmop.core.source.extprogram.ExternalProcessLongOperation;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;

public class YoutubeDLDownloader implements BaseDownloader {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final TrackFileFormat DOWNLOAD_FILE_FORMAT = TrackFileFormat.OPUS;

	private final BaseRemoteSourceQuerier querier;
	private final BaseTracksLocalSource tracks;

	public YoutubeDLDownloader(BaseRemoteSourceQuerier querier, BaseTracksLocalSource tracks) {
		super();
		this.querier = querier;
		this.tracks = tracks;
	}

	@Override
	public TrackFileFormat downloadFormat() {
		return DOWNLOAD_FILE_FORMAT;
	}

	@Override
	public BaseLongOperation<Track, Track> download(Track track, TrackFileLocation location)
			throws JMOPMusicbaseException {
		LOG.info("Preparing download of " + track + " via YoutubeDl downloader");

		AbstractProcessEncapsulation process = prepareProcess(track, location);

		String name = "Downloading";
		Track input = track;

		Supplier<Track> resultCreator = () -> track;
		Function<Track, String> dataToString = (t) -> t.toString();
		return new ExternalProcessLongOperation<Track, Track>(name, input, process, dataToString, resultCreator);
	}

	///////////////////////////////////////////////////////////////////////////
	private AbstractProcessEncapsulation prepareProcess(Track track, TrackFileLocation location)
			throws JMOPMusicbaseException {

		File workingDirectory = AbstractProcessEncapsulation.currentDirectory();
		File targetFile = createTargetFileFile(track, location);
		List<String> arguments = createArguments(track, location);

		AbstractProcessEncapsulation process = new YoutubeDLProcessEncapsulation(arguments, workingDirectory,
				targetFile);
		return process;
	}

	private List<String> createArguments(Track track, TrackFileLocation location) throws JMOPMusicbaseException {
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

	private String createUrlOfTrack(Track track) throws JMOPMusicbaseException {
		URL url = querier.urlOfTrack(track);
		return url.toExternalForm();
	}

	private String createTargetFilePath(Track track, TrackFileLocation location) throws JMOPMusicbaseException {
		File tmpFile = createTargetFileFile(track, location);

		return tmpFile.getAbsolutePath();
	}

	private File createTargetFileFile(Track track, TrackFileLocation location) throws JMOPMusicbaseException {
		return tracks.fileOfTrack(track, location, DOWNLOAD_FILE_FORMAT);
	}

}
