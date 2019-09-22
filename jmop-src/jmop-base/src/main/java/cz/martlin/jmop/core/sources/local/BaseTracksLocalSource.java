package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.data.Track;

public interface BaseTracksLocalSource {
	public File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format);

	public boolean exists(Track track, TrackFileLocation location, TrackFileFormat format);

	public void deleteIfExists(Track track, TrackFileLocation location, TrackFileFormat format);

	public void move(Track oldTrack, Track newTrack, TrackFileLocation location, TrackFileFormat format);

}
