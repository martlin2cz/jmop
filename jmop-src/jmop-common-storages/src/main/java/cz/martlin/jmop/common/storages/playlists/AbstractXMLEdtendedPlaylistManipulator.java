package cz.martlin.jmop.common.storages.playlists;

import java.io.File;

import org.w3c.dom.Document;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public abstract class AbstractXMLEdtendedPlaylistManipulator implements BaseExtendedPlaylistManipulator {

	private final XMLFileLoaderStorer xfls;

	public AbstractXMLEdtendedPlaylistManipulator() {
		super();
		this.xfls = new XMLFileLoaderStorer();
	}

/////////////////////////////////////////////////////////////////

	@Override
	public void savePlaylistWithBundle(Playlist playlist, File file) throws JMOPSourceException {
		save(playlist, file, true, true);
	}

	@Override
	public void saveOnlyPlaylist(Playlist playlist, File file) throws JMOPSourceException {
		save(playlist, file, false, false);
	}

	private void save(Playlist playlist, File file, boolean withBundleInfo, boolean withTrackInfo)
			throws JMOPSourceException {
		try {
			Document document = xfls.createEmptyDocument();
			pushPlaylistIntoDocument(playlist, withTrackInfo, document);

			if (withBundleInfo) {
				Bundle bundle = playlist.getBundle();
				pushBundleDataIntoDocument(bundle, document);
			}

			xfls.saveDocument(document, file);
		} catch (Exception e) {
			throw new JMOPSourceException("Cannot save playlist", e);
		}
	}

	protected abstract void pushPlaylistIntoDocument(Playlist playlist, boolean withTrackInfo, Document document);

	protected abstract void pushBundleDataIntoDocument(Bundle bundle, Document document);
	
/////////////////////////////////////////////////////////////////

	@Override
	public Bundle loadOnlyBundle(File file) throws JMOPSourceException {
		try {
			Document document = xfls.loadDocument(file);
			return extractBundleFromDocument(document);
		} catch (Exception e) {
			throw new JMOPSourceException("Cannot load bundle", e);
		}
	}

	protected abstract Bundle extractBundleFromDocument(Document document);
	
/////////////////////////////////////////////////////////////////

	@Override
	public Playlist loadOnlyPlaylist(Bundle bundle, File file) throws JMOPSourceException {
		try {
			Document document = xfls.loadDocument(file);
			return extractPlaylistFromDocument(bundle, document);
		} catch (Exception e) {
			throw new JMOPSourceException("Cannot load playlist", e);
		}
	}

	protected abstract Playlist extractPlaylistFromDocument(Bundle bundle, Document document);
	
/////////////////////////////////////////////////////////////////

}
