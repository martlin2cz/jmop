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
	public File getFileOfTrack(Bundle bundle, Track track) {
		return namer.fileOfTrack(bundle, track);
		//TODO test existence
	}
	
	
	
	

}
