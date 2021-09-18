package cz.martlin.jmop.core.sources.local.util.xml;

import java.io.File;

import org.w3c.dom.Document;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.locals.base.BaseBundleFilesLoaderStorer;
import cz.martlin.jmop.core.sources.locals.base.BasePlaylistFilesLoaderStorer;

public abstract class AbstractXMLPlaylistAndBundleFilesLoaderStorer
		implements BasePlaylistFilesLoaderStorer, BaseBundleFilesLoaderStorer {

	private final XMLFileLoaderStorer xfls;
	private final String extension;

	public AbstractXMLPlaylistAndBundleFilesLoaderStorer(String extension) {
		super();
		this.xfls = new XMLFileLoaderStorer();
		this.extension = extension;
	}

	@Override
	public String extensionOfFile() {
		return extension;
	}

	/////////////////////////////////////////////////////////////////

	@Override
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

	@Override
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
	@Override
	public void saveBundle(Bundle bundle, File file) throws JMOPSourceException {
		try {
			Document document = xfls.createEmptyDocument();
			pushBundleIntoDocument(bundle, document);
			xfls.saveDocument(document, file);
		} catch (Exception e) {
			throw new JMOPSourceException("Cannot save bundle", e);
		}
	}

	protected abstract void pushBundleIntoDocument(Bundle bundle, Document document);

	/////////////////////////////////////////////////////////////////

	@Override
	public void savePlaylist(Bundle bundle, Playlist playlist, File file) throws JMOPSourceException {
		try {
			Document document = xfls.createEmptyDocument();
			pushPlaylistIntoDocument(bundle, playlist, document);
			xfls.saveDocument(document, file);
		} catch (Exception e) {
			throw new JMOPSourceException("Cannot save playlist", e);
		}
	}

	protected abstract void pushPlaylistIntoDocument(Bundle bundle, Playlist playlist, Document document);

	/////////////////////////////////////////////////////////////////

}
