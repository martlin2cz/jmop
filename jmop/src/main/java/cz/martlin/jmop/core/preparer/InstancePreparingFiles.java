package cz.martlin.jmop.core.preparer;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.BaseRemoteSource;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.download.TrackFileFormatLocationPreparer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.location.TrackFileLocation;

@Deprecated
public abstract class InstancePreparingFiles<T> extends BaseTrackOperation<T> {

	protected final BaseRemoteSource remote;
	private final BaseLocalSource local;
	private final BaseSourceDownloader downloader;
	private final TrackFileFormatLocationPreparer preparer;
	private final TrackFileLocation downloadLocation;
	private final TrackFileLocation saveLocation;
	private final TrackFileLocation playerLocation;
	private final TrackFileFormat downloadFormat;
	private final TrackFileFormat saveFormat;
	private final TrackFileFormat playerFormat;

	public InstancePreparingFiles(BaseRemoteSource remote, BaseLocalSource local, BaseSourceDownloader downloader,
			TrackFileFormatLocationPreparer preparer, TrackFileLocation downloadLocation,
			TrackFileLocation saveLocation, TrackFileLocation playerLocation, TrackFileFormat downloadFormat,
			TrackFileFormat saveFormat, TrackFileFormat playerFormat) {

		super();
		this.remote = remote;
		this.local = local;
		this.downloader = downloader;
		this.preparer = preparer;
		this.downloadLocation = downloadLocation;
		this.saveLocation = saveLocation;
		this.playerLocation = playerLocation;
		this.downloadFormat = downloadFormat;
		this.saveFormat = saveFormat;
		this.playerFormat = playerFormat;
	}

	protected boolean prepareFiles(Track track) throws Exception {
		DownloadAndConvertOperation filesPreparer = new DownloadAndConvertOperation(local, downloader, preparer,
				downloadLocation, saveLocation, playerLocation, downloadFormat, saveFormat, playerFormat, track);
		
		return filesPreparer.runInternal();
	}

}