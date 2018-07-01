package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public class DefaultLocalSource implements BaseLocalSource {

	private final AbstractFileSystemAccessor fileSystem;
	private final Bundle bundle;

	public DefaultLocalSource(AbstractFileSystemAccessor fileSystem, Bundle bundle) {
		super();
		this.fileSystem = fileSystem;
		this.bundle = bundle;
	}

	@Override
	public Track getTrack(TrackIdentifier id) throws JMOPSourceException {
		return bundle.getTrack(id);
	}

	@Override
	public File fileOfTrack(Track track) throws JMOPSourceException {
		return fileSystem.getFileOfTrack(bundle, track);
	}

	@Override
	public boolean contains(Track track) throws JMOPSourceException {
		return bundle.contains(track);
	}

}
