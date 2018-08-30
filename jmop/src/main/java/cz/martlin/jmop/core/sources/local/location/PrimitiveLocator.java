package cz.martlin.jmop.core.sources.local.location;

public class PrimitiveLocator implements AbstractTrackFileLocator {

	@Override
	public TrackFileLocation locationOfDownload() {
		return TrackFileLocation.TEMP;
	}

	@Override
	public TrackFileLocation locationOfSave() {
		return TrackFileLocation.SAVE;
	}

	@Override
	public TrackFileLocation locationOfPlay() {
		return TrackFileLocation.CACHE;
	}

}
