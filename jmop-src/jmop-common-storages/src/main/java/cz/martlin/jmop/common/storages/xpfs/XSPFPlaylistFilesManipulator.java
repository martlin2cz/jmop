package cz.martlin.jmop.common.storages.xpfs;

import java.io.File;
import java.util.Map;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.storages.playlists.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;

public class XSPFPlaylistFilesManipulator implements BaseExtendedPlaylistManipulator {

	private static final String XSPF_FILE_EXTENSION = "xspf";

	private final BaseErrorReporter reporter;

	public XSPFPlaylistFilesManipulator(BaseErrorReporter reporter) {
		super();
		this.reporter = reporter;
	}

	@Override
	public String fileExtension() {
		return XSPF_FILE_EXTENSION;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void savePlaylistWithBundle(Playlist playlist, File file, TracksSource tracks)
			throws JMOPPersistenceException {
		JMOPExtendedXSPFPlaylist pla = JMOPExtendedXSPFPlaylist.tryLoadOrCreate(file, reporter);

		pla.setPlaylistData(playlist, tracks);

		Bundle bundle = playlist.getBundle();
		pla.setBundleData(bundle);

		pla.save(file);
	}

	@Override
	public void saveOnlyPlaylist(Playlist playlist, File file, TracksSource tracks) throws JMOPPersistenceException {
		JMOPExtendedXSPFPlaylist pla = JMOPExtendedXSPFPlaylist.tryLoadOrCreate(file, reporter);

		pla.setPlaylistData(playlist, tracks);

		pla.save(file);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public Bundle loadOnlyBundle(File file) throws JMOPPersistenceException {
		JMOPExtendedXSPFPlaylist pla = JMOPExtendedXSPFPlaylist.load(file, reporter);
		return pla.getBundleData();
	}

	@Override
	public Playlist loadOnlyPlaylist(Bundle bundle, Map<String, Track> tracks, File file)
			throws JMOPPersistenceException {
		JMOPExtendedXSPFPlaylist pla = JMOPExtendedXSPFPlaylist.load(file, reporter);
		return pla.getPlaylistData(bundle, tracks);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}
