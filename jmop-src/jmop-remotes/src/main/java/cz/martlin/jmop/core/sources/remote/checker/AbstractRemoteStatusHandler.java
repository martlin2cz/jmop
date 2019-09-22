package cz.martlin.jmop.core.sources.remote.checker;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.common.io.Files;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.BaseUIInterractor;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.misc.ops.BaseShortOperation;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.remote.BaseConverter;
import cz.martlin.jmop.core.sources.remote.BaseDownloader;
import cz.martlin.jmop.core.sources.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.core.sources.remote.BaseRemoteStatusHandler;
import cz.martlin.jmop.core.sources.remote.ConversionReason;
import javafx.util.Duration;

public abstract class AbstractRemoteStatusHandler implements BaseRemoteStatusHandler {

	private final SourceKind kind;
	private final BaseRemoteSourceQuerier querier;
	private final BaseDownloader downloader;
	private final BaseConverter converter;
	private final BaseLocalSource local;

	public AbstractRemoteStatusHandler(SourceKind kind, BaseRemoteSourceQuerier querier, BaseDownloader downloader,
			BaseConverter converter, BaseLocalSource local) {

		super();
		this.kind = kind;
		this.querier = querier;
		this.downloader = downloader;
		this.converter = converter;
		this.local = local;
	}

	@Override
	public boolean checkQuerier(BaseUIInterractor interactor) throws JMOPSourceException {
		Bundle bundle = prepareTestingBundle();
		String query = prepareTestingQuery();

		BaseShortOperation<String, List<Track>> operation = querier.search(bundle, query);

		return runOperation(interactor, operation);
	}

	@Override
	public boolean checkDownloader(BaseUIInterractor interactor) throws JMOPSourceException {
		TrackFileLocation location = TrackFileLocation.TEMP;
		Track track = prepareTestingTrack();

		BaseLongOperation<Track, Track> operation = downloader.download(track, location);

		return runOperation(interactor, operation);
	}

	@Override
	public boolean checkConverter(BaseUIInterractor interactor) throws JMOPSourceException {
		Track track = prepareTestingTrack();

		TrackFileFormat fromFormat = TrackFileFormat.MP3;
		TrackFileLocation fromLocation = TrackFileLocation.TEMP;

		prepareTestingFile(track, fromFormat, fromLocation, interactor);

		TrackFileLocation toLocation = TrackFileLocation.TEMP;
		TrackFileFormat toFormat = TrackFileFormat.WAV;
		ConversionReason reason = ConversionReason.PREPARE_TO_PLAY;

		BaseLongOperation<Track, Track> operation = converter.convert(track, fromLocation, fromFormat, toLocation,
				toFormat, reason);
		return runOperation(interactor, operation);
	}

	///////////////////////////////////////////////////////////////////////////

	protected abstract String prepareTestingQuery();

	private Bundle prepareTestingBundle() {
		String name = "test";
		return new Bundle(kind, name);
	}

	private Track prepareTestingTrack() {
		Bundle bundle = prepareTestingBundle();

		String identifier = prepareTestingTrackID();
		String title = "testing track";
		Duration duration = DurationUtilities.createDuration(0, 3, 15);
		String description = "This is just an testing track";

		return bundle.createTrack(identifier, title, description, duration);
	}

	protected abstract String prepareTestingTrackID();

	private void prepareTestingFile(Track track, TrackFileFormat fromFormat, TrackFileLocation fromLocation,
			BaseUIInterractor interactor) throws JMOPSourceException {

		String extension = fromFormat.getExtension();
		File sourceFile = interactor.promptFile("", extension);

		File targetFile = local.fileOfTrack(track, fromLocation, fromFormat);
		try {
			Files.copy(sourceFile, targetFile);
		} catch (IOException e) {
			throw new JMOPSourceException("Cannot prepare sample file", e);
		}
	}
	///////////////////////////////////////////////////////////////////////////

	private static <InT, OutT> boolean runOperation(BaseUIInterractor interactor, BaseOperation<InT, OutT> operation) {

		if (operation instanceof BaseLongOperation) {
			boolean agreed = interactor.confirm("This operation may take a while, continue?");
			if (!agreed) {
				return false;
			}
		}

		BaseProgressListener listener = new NoopProgressListener();
		try {
			OutT result = operation.run(listener);
			return result != null;
		} catch (Exception e) {
			interactor.displayError(e.getMessage());
			return false;
		}
	}

	public static class NoopProgressListener implements BaseProgressListener {

		@Override
		public void reportProgressChanged(double progress) {
			// ignore
		}
	}

}
