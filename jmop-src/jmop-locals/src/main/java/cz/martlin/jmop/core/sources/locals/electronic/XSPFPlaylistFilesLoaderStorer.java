package cz.martlin.jmop.core.sources.locals.electronic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Metadata;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

public class XSPFPlaylistFilesLoaderStorer extends AbstractXMLPlaylistAndBundleFilesLoaderStorer {

	private static final String XMLNS_NAMESPACE = "http://www.w3.org/2000/xmlns/";
	public static final String FILE_EXTENSION = "xspf"; //$NON-NLS-1$
	protected static final String APPLICATION_URL = "https://github.com/martlin2cz/jmop"; //$NON-NLS-1$

	protected static final String XSPF_NAMESPACE = "http://xspf.org/ns/0/"; //$NON-NLS-1$
	protected static final String JMOP_NAMESPACE = APPLICATION_URL + "/schemas/xspf-extension.xsd"; //$NON-NLS-1$

	public static final String JMOP_PREFIX = "jmop";

	public XSPFPlaylistFilesLoaderStorer() {
		super(FILE_EXTENSION);
	}

	///////////////////////////////////////////////////////////////////////////
	@Override
	protected Bundle extractBundleFromDocument(Document document) {
		Element root = document.getDocumentElement();
		checkRoot(root);

		SourceKind kind = extractBundleKind(root);
		String name = extractName(root, "bundle");
		Metadata metadata = extractMetadata(root, "bundle");

		Bundle bundle = new Bundle(kind, name, metadata);

		extractTracks(bundle, root);

		return bundle;
	}

	@Override
	protected void pushBundleIntoDocument(Bundle bundle, Document document) {
		Element root = createRootElement(document);

		SourceKind kind = bundle.getKind();
		pushBundleKind(document, root, kind);

		String name = bundle.getName();
		pushName(document, root, name);

		Metadata metadata = bundle.getMetadata();
		pushMetadata(document, root, "bundle", metadata);

		Tracklist tracks = bundle.tracks();
		pushTracks(document, root, tracks);

	}

	@Override
	protected Playlist extractPlaylistFromDocument(Bundle bundle, Document document) {
		Element root = document.getDocumentElement();
		checkRoot(root);

		String name = extractName(root, "Bundle");
		int currentTrack = extractPlaylistCurrentTrack(root);
		boolean locked = extractPlaylistLocked(root);
		Metadata metadata = extractMetadata(root, "Bundle");
		Tracklist tracks = extractTracks(bundle, root);

		return bundle.createPlaylist(name, currentTrack, locked, metadata, tracks);
	}

	@Override
	protected void pushPlaylistIntoDocument(Bundle bundle, Playlist playlist, Document document) {
		Element root = createRootElement(document);

		String name = playlist.getName();
		pushName(document, root, name);

		int currentTrack = playlist.getCurrentTrackIndex();
		pushPlaylistCurrentTrack(document, root, currentTrack);

		boolean locked = playlist.isLocked();
		pushPlaylistLocked(document, root, locked);

		Metadata metadata = playlist.getMetadata();
		pushMetadata(document, root, "bundle", metadata);

		Tracklist tracks = playlist.getTracks();
		pushTracks(document, root, tracks);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Checks root element and playlist file format version.
	 * 
	 * @param root
	 */
	private void checkRoot(Element root) {
		if (!"playlist".equals(root.getTagName())) { //$NON-NLS-1$
			throw new IllegalArgumentException("Not a playlist file"); //$NON-NLS-1$
		}

		if (!"1".equals(root.getAttribute("version"))) { //$NON-NLS-1$ //$NON-NLS-2$
			throw new IllegalArgumentException("Not version 1"); //$NON-NLS-1$
		}
	}

	/**
	 * Creates empty playlist root element.
	 * 
	 * @param document
	 * @return
	 */
	private Element createRootElement(Document document) {
		Element root = document.createElementNS(XSPF_NAMESPACE, "playlist"); //$NON-NLS-1$

		root.setAttributeNS(XMLNS_NAMESPACE, "xmlns:" + JMOP_PREFIX, JMOP_NAMESPACE);
		root.setAttributeNS(XMLNS_NAMESPACE, "xmlns", XSPF_NAMESPACE);

		root.setAttribute("version", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		// FIXME hack: attr not under NS

		document.appendChild(root);
		return root;
	}

	///////////////////////////////////////////////////////////////////////////
	private void pushName(Document document, Element root, String name) {
		XSPFDocumentUtility.createElementWithText(document, root, XSPF_NAMESPACE, "title", name);
	}

	private void pushBundleKind(Document document, Element root, SourceKind kind) {
		String sourceStr = kind.getName();
		XSPFDocumentUtility.setExtensionValue(document, root, "bundle", "source", sourceStr);
	}

	private void pushMetadata(Document document, Element element, String extensionElement, Metadata metadata) {
		String createdStr = XSPFDocumentUtility.stringifyDatetime(metadata.getCreated());
		XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "created", createdStr);

		String numberOfPlaysStr = Integer.toString(metadata.getNumberOfPlays());
		XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "numberOfPlays", numberOfPlaysStr);

		String lastPlayedStr = XSPFDocumentUtility.stringifyDatetime(metadata.getLastPlayed());
		XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "lastPlayed", lastPlayedStr);
	}

	private void pushPlaylistLocked(Document document, Element root, boolean locked) {
		String lockedStr = Boolean.toString(locked);
		XSPFDocumentUtility.setExtensionValue(document, root, "playlist", "locked", lockedStr);
	}

	private void pushPlaylistCurrentTrack(Document document, Element root, int currentTrack) {
		String currTrackStr = Integer.toString(currentTrack);
		XSPFDocumentUtility.setExtensionValue(document, root, "playlist", "currentTrack", currTrackStr);
	}

	private void pushTracks(Document document, Element root, Tracklist tracks) {
		Element tracklist = XSPFDocumentUtility.getChildOrCreate(document, root, XSPF_NAMESPACE, "tracklist");

		tracks.getTracks().forEach( //
				(t) -> pushTrack(document, tracklist, t));
	}

	private void pushTrack(Document document, Element tracklist, Track track) {
		Element trackElem = XSPFDocumentUtility.getChildOrCreate(document, tracklist, XSPF_NAMESPACE, "track");

		String name = track.getTitle();
		XSPFDocumentUtility.createElementWithText(document, trackElem, XSPF_NAMESPACE, "title", name);

		String id = track.getIdentifier();
		XSPFDocumentUtility.createElementWithText(document, trackElem, XSPF_NAMESPACE, "identifier", id);

		String annot = track.getDescription();
		XSPFDocumentUtility.createElementWithText(document, trackElem, XSPF_NAMESPACE, "annotation", annot);

		String duration = DurationUtilities.toHumanString(track.getDuration());
		XSPFDocumentUtility.createElementWithText(document, trackElem, XSPF_NAMESPACE, "duration", duration);

		Metadata metadata = track.getMetadata();
		pushMetadata(document, trackElem, "track", metadata);
	}
///////////////////////////////////////////////////////////////////////////

	private String extractName(Element root, String extensionElementName) {
		return XSPFDocumentUtility.getElementText(root, XSPF_NAMESPACE, "title");
	}

	private SourceKind extractBundleKind(Element root) {
		String sourceStr = XSPFDocumentUtility.getExtensionValue(root, "bundle", "source");
		return SourceKind.ofName(sourceStr);
	}

	private boolean extractPlaylistLocked(Element root) {
		String lockedStr = XSPFDocumentUtility.getExtensionValue(root, "playlist", "locked");
		return Boolean.parseBoolean(lockedStr);
	}

	private int extractPlaylistCurrentTrack(Element root) {
		String currTrackStr = XSPFDocumentUtility.getExtensionValue(root, "playlist", "currentTrack");
		if (currTrackStr != null && !currTrackStr.isEmpty()) {
			return Integer.parseInt(currTrackStr);
		} else {
			return 0;
		}
	}

	private Metadata extractMetadata(Element root, String extensionElementName) {
		String lastPlayedStr = XSPFDocumentUtility.getExtensionValue(root, extensionElementName, "lastPlayed");
		Calendar lastPlayed = XSPFDocumentUtility.parseDatetime(lastPlayedStr);

		String numberOfPlaysStr = XSPFDocumentUtility.getExtensionValue(root, extensionElementName, "numberOfPlays");
		int numberOfPlays = Integer.parseInt(numberOfPlaysStr);

		String createdStr = XSPFDocumentUtility.getExtensionValue(root, extensionElementName, "created");
		Calendar created = XSPFDocumentUtility.parseDatetime(createdStr);

		return Metadata.createExisting(created, lastPlayed, numberOfPlays);
	}

	private Tracklist extractTracks(Bundle bundle, Element root) {
		Element trackList = XSPFDocumentUtility.getChildOrFail(root, JMOP_NAMESPACE, "trackList"); //$NON-NLS-1$

		List<Track> tracks = new ArrayList<>(trackList.getChildNodes().getLength());

		XSPFDocumentUtility.iterateOverChildrenOrFail(trackList, XMLNS_NAMESPACE, "track", (te) -> {
			Track track = extractTrack(bundle, te);
			tracks.add(track);
		});

		return new Tracklist(tracks);
	}

	private Track extractTrack(Bundle bundle, Element trackElem) {
		String title = XSPFDocumentUtility.getElementText(trackElem, XSPF_NAMESPACE, "title");
		String identifier = XSPFDocumentUtility.getElementText(trackElem, XSPF_NAMESPACE, "identifier");
		String description = XSPFDocumentUtility.getElementText(trackElem, XSPF_NAMESPACE, "annotation");

		String durationStr = XSPFDocumentUtility.getElementText(trackElem, XSPF_NAMESPACE, "duration");
		Duration duration = DurationUtilities.parseMilisDuration(durationStr);

		Metadata metadata = extractMetadata(trackElem, "track"); //$NON-NLS-1$

		return bundle.createTrack(identifier, title, description, duration, metadata);
	}

}
