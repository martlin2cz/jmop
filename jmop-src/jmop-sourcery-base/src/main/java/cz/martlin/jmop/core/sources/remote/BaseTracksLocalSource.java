package cz.martlin.jmop.core.sources.remote;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;

@Deprecated
public interface BaseTracksLocalSource {

	File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat downloadFormat);

}
