package cz.martlin.jmop.common.storages.simplefs;

import java.io.File;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.Tracklist;
import cz.martlin.jmop.common.musicbase.commons.BaseMusicdataSaver;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.base.BaseFileSystemAccessor;

public class SimpleSaver implements BaseMusicdataSaver {

	private final BaseFileSystemAccessor fs;

	public SimpleSaver(BaseFileSystemAccessor fs) {
		super();
		this.fs = fs;
	}

	@Override
	public void saveBundleData(File bundleDir, Bundle bundle, SaveReason reason) {
		// ignore, since we don't store metadata
	}

	@Override
	public void savePlaylistData(File playlistFile, Playlist playlist, SaveReason reason) throws JMOPSourceException {
		Tracklist tracklist = playlist.getTracks();
		saveTracklist(tracklist, playlistFile);
	}

	@Override
	public void saveTrackData(File trackFile, Track track, SaveReason reason) {
		// ignore, since we don't store metadata
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void saveTracklist(Tracklist tracklist, File playlistFile) throws JMOPSourceException {
		fs.saveLines(playlistFile, //
				tracklist.getTracks().stream() //
						.map(t -> (t.getIdentifier() + "mp3")) //
						.collect(Collectors.toList()) //
		); //
	}
}
