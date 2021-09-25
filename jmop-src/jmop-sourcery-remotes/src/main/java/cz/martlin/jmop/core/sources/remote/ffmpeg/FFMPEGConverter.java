package cz.martlin.jmop.core.sources.remote.ffmpeg;

import java.io.File;
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
import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.ConversionReason;
import javafx.util.Duration;

public class FFMPEGConverter implements BaseConverter {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final String FFMPEG_COMMAND_NAME = "ffmpeg";

	private final BaseTracksLocalSource tracks;

	public FFMPEGConverter(BaseTracksLocalSource tracks) {
		super();
		this.tracks = tracks;
	}

	@Override
	public BaseLongOperation<Track, Track> convert(Track track, TrackFileLocation fromLocation,
			TrackFileFormat fromFormat, TrackFileLocation toLocation, TrackFileFormat toFormat, ConversionReason reason)
			 {

		LOG.info("Preparing conversion of " + track + " via ffmpeg converter");

		AbstractProcessEncapsulation process = prepareProcess(track, fromLocation, fromFormat, toLocation, toFormat);

		String name = reason.getHumanName();
		Track input = track;

		Supplier<Track> resultCreator = () -> track;
		Function<Track, String> dataToString = (t) -> t.toString();
		return new ExternalProcessLongOperation<Track, Track>(name, input, process, dataToString, resultCreator);
	}

	///////////////////////////////////////////////////////////////////////////
	private AbstractProcessEncapsulation prepareProcess(Track track, TrackFileLocation fromLocation,
			TrackFileFormat fromFormat, TrackFileLocation toLocation, TrackFileFormat toFormat)
			 {

		File workingDirectory = AbstractProcessEncapsulation.currentDirectory();
		File targetFile = createTargetFileFile(track, toLocation, toFormat);
		List<String> arguments = createArguments(track, fromLocation, fromFormat, toLocation, toFormat);
		Duration duration = track.getDuration();

		String command = FFMPEG_COMMAND_NAME;

		AbstractProcessEncapsulation process = new FFMPEGProcessEncapsulation(command, arguments, workingDirectory,
				targetFile, duration);
		return process;
	}

	private List<String> createArguments(Track track, TrackFileLocation fromLocation, TrackFileFormat fromFormat,
			TrackFileLocation toLocation, TrackFileFormat toFormat)  {
		String fromPath = createFilePath(track, fromLocation, fromFormat);
		String toPath = createFilePath(track, toLocation, toFormat);
		return createCommandLine(fromPath, toPath);
	}

	private List<String> createCommandLine(String fromPath, String toPath) {
		return Arrays.asList( //
				"-stats", "-y", "-i", fromPath, toPath);
	}

	private String createFilePath(Track track, TrackFileLocation location, TrackFileFormat format)
			 {
		File tmpFile = createTargetFileFile(track, location, format);

		return tmpFile.getAbsolutePath();
	}

	private File createTargetFileFile(Track track, TrackFileLocation location, TrackFileFormat format)
			 {
		return tracks.fileOfTrack(track, location, format);
	}

}
