package cz.martlin.jmop.core.sources.local;

import java.io.File;

import cz.martlin.jmop.core.tracks.Bundle;
import cz.martlin.jmop.core.tracks.Track;

public class DefaultFileSystemAccessor implements AbstractFileSystemAccessor {

	private final BaseFilesNamer namer;
	
	public DefaultFileSystemAccessor(BaseFilesNamer namer) {
		super();
		this.namer = namer;
	}

	@Override
	public File getFileOfTrack(Bundle bundle, Track track, TrackFileFormat format) {
		return namer.fileOfTrack(bundle, track, format);
		//TODO test existence
	}
	
	
	
	

}
