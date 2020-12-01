package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseTracksLocalSource {
	public File fileOfTrack(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException;

	public boolean exists(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException;

	public void deleteIfExists(Track track, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException;

	public void move(Track oldTrack, Track newTrack, TrackFileLocation location, TrackFileFormat format) throws JMOPSourceException;

}
