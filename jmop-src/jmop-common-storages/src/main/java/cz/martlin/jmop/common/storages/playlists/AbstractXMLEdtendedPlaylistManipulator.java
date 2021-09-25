package cz.martlin.jmop.common.storages.playlists;

import java.io.File;
import java.util.Map;

import org.w3c.dom.Document;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.xspf.util.XMLFileLoaderStorer;

/**
 * @deprecated Separate the PlaylistManipulator and the XML to two separate classes
 * @author martin
 *
 */
@Deprecated
public abstract class AbstractXMLEdtendedPlaylistManipulator implements BaseExtendedPlaylistManipulator {

	private final XMLFileLoaderStorer xfls;

	public AbstractXMLEdtendedPlaylistManipulator() {
		super();
		this.xfls = new XMLFileLoaderStorer();
	}

/////////////////////////////////////////////////////////////////

	@Override
	public void savePlaylistWithBundle(Playlist playlist, File file, TracksSource tracks) throws JMOPPersistenceException {
		save(playlist, file, true, true);
	}

	@Override
	public void saveOnlyPlaylist(Playlist playlist, File file, TracksSource tracks) throws JMOPPersistenceException {
		save(playlist, file, false, false);
	}

	private void save(Playlist playlist, File file, boolean withBundleInfo, boolean withTrackInfo)
			throws JMOPPersistenceException {
		try {
			Document document = xfls.createEmptyDocument(); //FIXME try to load the existing file and just enhance/override, not recreate from scratch
			pushPlaylistIntoDocument(playlist, withTrackInfo, document);

			if (withBundleInfo) {
				Bundle bundle = playlist.getBundle();
				pushBundleDataIntoDocument(bundle, document);
			}

			xfls.saveDocument(document, file);
		} catch (Exception e) {
			throw new JMOPPersistenceException("Cannot save playlist/bundle/tracks", e);
		}
	}

	protected abstract void pushPlaylistIntoDocument(Playlist playlist, boolean withTrackInfo, Document document)
			throws JMOPPersistenceException;

	protected abstract void pushBundleDataIntoDocument(Bundle bundle, Document document)
			throws JMOPPersistenceException;

/////////////////////////////////////////////////////////////////

	@Override
	public Bundle loadOnlyBundle(File file) throws JMOPPersistenceException {
		try {
			Document document = xfls.loadDocument(file);
			return extractBundleFromDocument(document);
		} catch (Exception e) {
			throw new JMOPPersistenceException("Cannot load bundle", e);
		}
	}

	protected abstract Bundle extractBundleFromDocument(Document document) throws JMOPPersistenceException;

/////////////////////////////////////////////////////////////////

	@Override
	public Playlist loadOnlyPlaylist(Bundle bundle, Map<String, Track> tracks, File file)
			throws JMOPPersistenceException {
		try {
			Document document = xfls.loadDocument(file);
			return extractPlaylistFromDocument(bundle, tracks, document);
		} catch (Exception e) {
			throw new JMOPPersistenceException("Cannot load playlist", e);
		}
	}

	protected abstract Playlist extractPlaylistFromDocument(Bundle bundle, Map<String, Track> tracks, Document document)
			throws JMOPPersistenceException;

/////////////////////////////////////////////////////////////////

}
