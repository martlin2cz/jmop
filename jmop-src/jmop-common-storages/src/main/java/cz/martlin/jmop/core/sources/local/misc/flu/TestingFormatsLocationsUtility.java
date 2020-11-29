package cz.martlin.jmop.core.sources.local.misc.flu;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;

public class TestingFormatsLocationsUtility extends FormatsLocationsUtility {
	private final TrackFileFormat format;
	private final TrackFileLocation location;

	public TestingFormatsLocationsUtility(TrackFileFormat format, TrackFileLocation location) {
		super(null, null, null, null);

		this.format = format;
		this.location = location;
	}

	@Override
	public TrackFileFormat downloadFormat() {
		return format;
	}

	@Override
	public TrackFileLocation downloadLocation() {
		return location;
	}

	@Override
	public TrackFileFormat saveFormat() {
		return format;
	}

	@Override
	public TrackFileLocation saveLocation() {
		return location;
	}

	@Override
	public TrackFileFormat playFormat() {
		return format;
	}

	@Override
	public TrackFileLocation playLocation() {
		return location;
	}

}
