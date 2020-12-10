package cz.martlin.jmop.core.sources.local.util.xml;

import java.io.File;

import org.w3c.dom.Document;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Tracklist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.base.BaseBundleFilesLoaderStorer;
import cz.martlin.jmop.core.sources.locals.base.BasePlaylistFilesLoaderStorer;

public abstract class AbstractXMLPlaylistAndBundleFilesLoaderStorer {

	private final XMLFileLoaderStorer xfls;

	public AbstractXMLPlaylistAndBundleFilesLoaderStorer() {
		super();
		this.xfls = new XMLFileLoaderStorer();
	}

	/////////////////////////////////////////////////////////////////

	public Bundle loadBundle(File file) throws JMOPSourceException {
		try {
			Document document = xfls.loadDocument(file);
			return extractBundleFromDocument(document);
		} catch (Exception e) {
			throw new JMOPSourceException("Cannot load bundle", e);
		}
	}

	protected abstract Bundle extractBundleFromDocument(Document document);

	/////////////////////////////////////////////////////////////////

	public Playlist loadPlaylist(Bundle bundle, File file) throws JMOPSourceException {
		try {
			Document document = xfls.loadDocument(file);
			return extractPlaylistFromDocument(bundle, document);
		} catch (Exception e) {
			throw new JMOPSourceException("Cannot load playlist", e);
		}
	}

	protected abstract Playlist extractPlaylistFromDocument(Bundle bundle, Document document);

	/////////////////////////////////////////////////////////////////

	public void savePlaylist(Playlist playlist, File file, boolean withBundleInfo, boolean withTrackInfo) throws JMOPSourceException {
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

}
