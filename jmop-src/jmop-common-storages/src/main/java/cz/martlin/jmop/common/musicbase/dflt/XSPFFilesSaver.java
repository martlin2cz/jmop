package cz.martlin.jmop.common.musicbase.dflt;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.musicbase.commons.BaseExtendedPlaylistSaver;
import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.xspf.XSPFPlaylistFilesLoaderStorer;

public class XSPFFilesSaver implements BaseExtendedPlaylistSaver {

	private final XSPFPlaylistFilesLoaderStorer xspf;
	
	
	public XSPFFilesSaver() {
		super();
		this.xspf = new XSPFPlaylistFilesLoaderStorer();
	}

	@Override
	public void savePlaylistWithBundle(Playlist playlist, File playlistFile) throws JMOPSourceException {
		xspf.savePlaylist(playlist, playlistFile, true, true);
	}

	@Override
	public void saveOnlyPlaylist(Playlist playlist, File playlistFile) throws JMOPSourceException {
		xspf.savePlaylist(playlist, playlistFile, false, false);
	}

	@Override
	public Bundle loadBundleDataFromAllTracksPlaylist(File playlistFile) throws JMOPSourceException {
		return xspf.loadBundle(playlistFile);
	}

	@Override
	public Playlist loadPlaylistDataFromPlaylistFile(Bundle bundle, File playlistFile) throws JMOPSourceException {
		return xspf.loadPlaylist(bundle, playlistFile);
	}

}
