package cz.martlin.jmop.core.sources.remote.checker;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.testing.resources.TestingTrackFilesCreator;
import cz.martlin.jmop.core.misc.BaseUIInterractor;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.ops.BaseLongOperation;
import cz.martlin.jmop.core.misc.ops.BaseOperation;
import cz.martlin.jmop.core.misc.ops.BaseProgressListener;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.sourcery.remote.BaseConverter;
import cz.martlin.jmop.sourcery.remote.BaseDownloader;
import cz.martlin.jmop.sourcery.remote.BaseRemoteSourceQuerier;
import cz.martlin.jmop.sourcery.remote.BaseRemoteStatusHandler;
import cz.martlin.jmop.sourcery.remote.JMOPSourceryException;
import javafx.util.Duration;

public abstract class AbstractRemoteStatusHandler implements BaseRemoteStatusHandler {

	private static final String QUERIER_SAMPLE_QUERY = "sample";
	private static final String DOWNLOADER_SAMPLE_IDENTIFIER = null;
	private final BaseRemoteSourceQuerier querier;
	private final BaseDownloader downloader;
	private final BaseConverter converter;

	public AbstractRemoteStatusHandler(BaseRemoteSourceQuerier querier, BaseDownloader downloader,
			BaseConverter converter) {

		super();
		this.querier = querier;
		this.downloader = downloader;
		this.converter = converter;
	}

	@Override
	public boolean checkQuerier(BaseUIInterractor interactor)  {
		try {
			List<TrackData> result = querier.search(prepareTestingQuery());
			if (result.isEmpty()) {
				throw new JMOPSourceryException("The query executed, but returned empty results");
			}
			return true;
		} catch (JMOPSourceryException e) {
			interactor.displayError(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean checkDownloader(BaseUIInterractor interactor)  {
		try {
			File target = File.createTempFile("downloader-testing", ".track");
			Track track = prepareTestingTrack();
			
			URL urlURL = querier.urlOfTrack(track);
			String urlString = urlURL.toExternalForm(); 
			downloader.download(urlString, target);
			
			if (target.exists()) {
				throw new JMOPSourceryException("The download succeded, but the file was not created");
			}
			return true;
		} catch (JMOPSourceryException e) {
			interactor.displayError(e.getMessage());
			return false;
		} catch (IOException e) {
			interactor.displayError(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean checkConverter(BaseUIInterractor interactor)  {
		try {
			Track track = prepareTestingTrack();
			
			TrackFileFormat fromFormat = TrackFileFormat.MP3;
			File fromFile = File.createTempFile("jmop-converter-test-source-", "." + fromFormat.fileExtension());
			
			TestingTrackFilesCreator creator = new TestingTrackFilesCreator();
			creator.prepare(fromFormat, fromFile);
	
	//		prepareTestingFile(track, fromFormat, fromLocation, interactor);
	
			TrackFileFormat toFormat = TrackFileFormat.WAV;
			File toFile = File.createTempFile("jmop-converter-test-target-", "." + toFormat.fileExtension());
		
		
			converter.convert(track, fromFile, toFile);
		} catch (Exception e) {
			interactor.displayError(e.getMessage());
			return false;
		}
		
		return true;
	}

	///////////////////////////////////////////////////////////////////////////

	protected abstract String prepareTestingQuery();

	protected Track prepareTestingTrack() {
		Bundle bundle = null;

		String title = "testing track";
		Duration duration = DurationUtilities.createDuration(0, 3, 15);
		String description = "This is just an testing track";
		Metadata metadata = Metadata.createNew();
		URI source = prepareTestingTrackURI();
		File file = null;
		
		return new Track(bundle, title, description, duration, source, file, metadata);
	}

	protected abstract URI prepareTestingTrackURI();
	
	///////////////////////////////////////////////////////////////////////////

	@Deprecated
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
