package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.BaseSourceImpl;
import cz.martlin.jmop.core.stream.StreamSeed;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public interface BaseLocalSource extends BaseSourceImpl {

	@Override
	public abstract Track getTrack(TrackIdentifier id) throws JMOPSourceException;

	public abstract File fileOfTrack(Track track) throws JMOPSourceException;

	public abstract boolean contains(Track next) throws JMOPSourceException;

}
