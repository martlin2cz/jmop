package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.sources.SourceKind;

public class DefaultPlaylistLoader implements PlaylistLoader {

	@Override
	public PlaylistFileData load(File file) throws IOException {
		// TODO Auto-generated method stub
		return new PlaylistFileData(file.getName(), SourceKind.YOUTUBE, new Tracklist());
	}

	@Override
	public void save(PlaylistFileData playlist, File file) throws IOException {
		// TODO Auto-generated method stub

	}

}
