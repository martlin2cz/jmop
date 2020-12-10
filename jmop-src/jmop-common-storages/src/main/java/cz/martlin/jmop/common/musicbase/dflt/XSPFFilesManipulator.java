package cz.martlin.jmop.common.musicbase.dflt;

import java.io.File;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.musicbase.commons.BaseExtendedPlaylistManipulator;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.electronic.xspf.XSPFPlaylistFilesLoaderStorer;

public class XSPFFilesManipulator implements BaseExtendedPlaylistManipulator {

	private static final String XSPF_FILE_EXTENSION = "xspf";
	
	private final XSPFPlaylistFilesLoaderStorer xspf;
	
	
	public XSPFFilesManipulator() {
		super();
		this.xspf = new XSPFPlaylistFilesLoaderStorer();
	}
	
	@Override
	public String fileExtension() {
		return XSPF_FILE_EXTENSION;
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
	public Bundle loadOnlyBundle(File playlistFile) throws JMOPSourceException {
		return xspf.loadBundle(playlistFile);
	}

	@Override
	public Playlist loadOnlyPlaylist(Bundle bundle, File playlistFile) throws JMOPSourceException {
		return xspf.loadPlaylist(bundle, playlistFile);
	}

}
