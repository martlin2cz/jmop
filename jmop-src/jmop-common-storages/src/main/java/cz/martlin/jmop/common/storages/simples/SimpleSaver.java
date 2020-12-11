package cz.martlin.jmop.common.storages.simples;

import java.io.File;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.storages.bundlesdir.BaseMusicdataSaver;
import cz.martlin.jmop.common.storages.utils.BaseFileSystemAccessor;
import cz.martlin.jmop.core.misc.JMOPSourceException;

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
