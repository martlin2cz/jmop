package cz.martlin.jmop.common.storages.xpfs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.storages.playlists.AbstractXMLEdtendedPlaylistManipulator;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import javafx.util.Duration;

public class XSPFFilesManipulator extends AbstractXMLEdtendedPlaylistManipulator {
	private static final String XSPF_VERSION = "1"; //$NON-NLS-1$
	public static final String FILE_EXTENSION = "xspf"; //$NON-NLS-1$
	protected static final String APPLICATION_URL = "https://github.com/martlin2cz/jmop"; //$NON-NLS-1$

	public XSPFFilesManipulator() {
		super();
	}
	
	@Override
	public String fileExtension() {
		return FILE_EXTENSION;
	}


	///////////////////////////////////////////////////////////////////////////
	@Override
	protected Bundle extractBundleFromDocument(Document document) {
		Element root = document.getDocumentElement();
		checkRoot(root);

		Bundle bundle = extractBundleDataFromDocument(root);

		extractPlaylistDataFromDocument(bundle, null, root);

		return bundle;
	}

	private Bundle extractBundleDataFromDocument(Element root) {
		String name = extractBundleName(root);
		Metadata metadata = extractMetadata(root, "bundle");

		Bundle bundle = new Bundle(name, metadata);
		return bundle;
	}

	@Override
	protected void pushBundleDataIntoDocument(Bundle bundle, Document document) {
		//Note: call pushPlaylistData(...) first
		Element root = document.getDocumentElement();

		pushBundleDataIntoDocument(document, root, bundle);
	}

	private void pushBundleDataIntoDocument(Document document, Element root, Bundle bundle) {
		String name = bundle.getName();
		pushBundleName(document, root, name);

		Metadata metadata = bundle.getMetadata();
		pushMetadata(document, root, "bundle", metadata);
	}

	@Override
	protected Playlist extractPlaylistFromDocument(Bundle bundle, Map<String, Track> tracks, Document document) {
		Element root = document.getDocumentElement();
		checkRoot(root);

		return extractPlaylistDataFromDocument(bundle, tracks, root);
	}

	private Playlist extractPlaylistDataFromDocument(Bundle bundle, Map<String, Track> tracks, Element root) {
		String name = extractName(root);
		int currentTrack = extractPlaylistCurrentTrack(root);
		Metadata metadata = extractMetadata(root, "playlist");
		Tracklist tracklist = extractTracks(bundle, tracks, root);

		return new Playlist(bundle, name, tracklist, currentTrack, metadata);
	}

	@Override
	protected void pushPlaylistIntoDocument(Playlist playlist, boolean withTrackInfo, Document document) {
		Element root = createRootElement(document);

		pushPlaylistDataIntoDocument(document, root, playlist, withTrackInfo);
	}

	private void pushPlaylistDataIntoDocument(Document document, Element root, Playlist playlist, boolean withTrackInfo) {
		String name = playlist.getName();
		pushName(document, root, name);

		int currentTrack = playlist.getCurrentTrackIndex();
		pushPlaylistCurrentTrack(document, root, currentTrack);

		Metadata metadata = playlist.getMetadata();
		pushMetadata(document, root, "playlist", metadata);

		Tracklist tracks = playlist.getTracks();
		pushTracks(document, root, tracks, withTrackInfo);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Checks root element and playlist file format version.
	 * 
	 * @param root
	 */
	private void checkRoot(Element root) {
		String nsName = XSPFDocumentNamespaces.XSPF.namify("playlist"); //$NON-NLS-1$
		if (!nsName.equals(root.getTagName())) { // $NON-NLS-1$
			throw new IllegalArgumentException("Not a playlist file"); //$NON-NLS-1$
		}

		String nsVersion = XSPFDocumentNamespaces.XSPF.namify("version"); //$NON-NLS-1$
		if (!XSPF_VERSION.equals(root.getAttribute(nsVersion))) { // $NON-NLS-1$ //$NON-NLS-2$
			throw new IllegalArgumentException("Not version " + XSPF_VERSION); //$NON-NLS-1$
		}
	}

	/**
	 * Creates empty playlist root element.
	 * 
	 * @param document
	 * @return
	 */
	private Element createRootElement(Document document) {
		String nsName = XSPFDocumentNamespaces.XSPF.namify("playlist"); //$NON-NLS-1$
		Element root = document.createElement(nsName);

		for (XSPFDocumentNamespaces namespace : XSPFDocumentNamespaces.values()) {
			addNSattributes(root, namespace);
		}

		String nsVersion = XSPFDocumentNamespaces.XSPF.namify("version"); //$NON-NLS-1$
		root.setAttribute(nsVersion, XSPF_VERSION);

		document.appendChild(root);
		return root;
	}

	private void addNSattributes(Element root, XSPFDocumentNamespaces namespace) {
		String attrName = namespace.namifyXMLNS();
		String atrrValue = namespace.getUrl();

		root.setAttribute(attrName, atrrValue);
	}

	///////////////////////////////////////////////////////////////////////////
	private void pushName(Document document, Element root, String name) {
		XSPFDocumentUtility.createElementWithText(document, root, XSPFDocumentNamespaces.XSPF, "title", name);
	}

	private void pushBundleName(Document document, Element root, String name) {
		XSPFDocumentUtility.setExtensionValue(document, root, "bundle", "name", name);
	}

	private void pushMetadata(Document document, Element element, String extensionElement, Metadata metadata) {
		String createdStr = XSPFDocumentUtility.stringifyDatetime(metadata.getCreated());
		XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "created", createdStr);

		String numberOfPlaysStr = Integer.toString(metadata.getNumberOfPlays());
		XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "numberOfPlays", numberOfPlaysStr);

		String lastPlayedStr = XSPFDocumentUtility.stringifyDatetime(metadata.getLastPlayed());
		XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "lastPlayed", lastPlayedStr);
	}

	private void pushPlaylistCurrentTrack(Document document, Element root, int currentTrack) {
		String currTrackStr = Integer.toString(currentTrack);
		XSPFDocumentUtility.setExtensionValue(document, root, "playlist", "currentTrack", currTrackStr);
	}

	private void pushTracks(Document document, Element root, Tracklist tracks, boolean withTrackInfo) {
		Element tracklist = XSPFDocumentUtility.getChildOrCreate(document, root, XSPFDocumentNamespaces.XSPF,
				"tracklist");

		tracks.getTracks().forEach( //
				(t) -> pushTrack(document, tracklist, t, withTrackInfo));
	}

	protected Element pushTrack(Document document, Element tracklist, Track track, boolean withTrackInfo) {
		Element trackElem = XSPFDocumentUtility.createElement(document, tracklist, XSPFDocumentNamespaces.XSPF,
				"track");

		String name = track.getTitle();
		XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "title", name);

		String id = track.getIdentifier();
		XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "identifier", id);

		// TODO identifier into extension?
		// TODO full url -> identifier (use remote querier)?

		String annot = track.getDescription();
		XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "annotation",
				annot);

		String durationStr = DurationUtilities.toMilis(track.getDuration());
		String durationHumanStr = DurationUtilities.toHumanString(track.getDuration());
		XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "duration",
				durationStr);
		XSPFDocumentUtility.createCommentWithText(document, trackElem, durationHumanStr);

		if (withTrackInfo) {
			Metadata metadata = track.getMetadata();
			pushMetadata(document, trackElem, "track", metadata);
		}
		
		return trackElem;
	}

///////////////////////////////////////////////////////////////////////////

	private String extractName(Element root) {
		return XSPFDocumentUtility.getElementText(root, XSPFDocumentNamespaces.XSPF, "title");
	}

	private String extractBundleName(Element root) {
		return XSPFDocumentUtility.getExtensionValue(root, "bundle", "name");
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

	private Tracklist extractTracks(Bundle bundle, Map<String, Track> tracks, Element root) {
		Element trackList = XSPFDocumentUtility.getChildOrFail(root, XSPFDocumentNamespaces.XSPF, "tracklist"); //$NON-NLS-1$

		List<Track> result = new ArrayList<>(trackList.getChildNodes().getLength());

		XSPFDocumentUtility.iterateOverChildrenOrFail(trackList, XSPFDocumentNamespaces.XSPF, "track", (te) -> {
			Track track = pickOrExtractTrack(bundle, tracks, te);
			result.add(track);
		});

		return new Tracklist(result);
	}

	private Track pickOrExtractTrack(Bundle bundle, Map<String, Track> tracks, Element trackElem) {
		if (tracks == null) {
			return extractTrack(bundle, trackElem);
		} else {
			return pickTrack(bundle, tracks, trackElem);
		}
	}

	private Track pickTrack(Bundle bundle, Map<String, Track> tracks, Element trackElem) {
		String title = XSPFDocumentUtility.getElementText(trackElem, XSPFDocumentNamespaces.XSPF, "title");
		
		if (!tracks.containsKey(title)) {
			throw new IllegalStateException("The track " + title + " not found in the playlist"); 
		}
		
		Track track = tracks.get(title);
		return track;
	}

	protected Track extractTrack(Bundle bundle, Element trackElem) {
		String title = XSPFDocumentUtility.getElementText(trackElem, XSPFDocumentNamespaces.XSPF, "title");
		String identifier = XSPFDocumentUtility.getElementText(trackElem, XSPFDocumentNamespaces.XSPF, "identifier");
		String description = XSPFDocumentUtility.getElementText(trackElem, XSPFDocumentNamespaces.XSPF, "annotation");

		String durationStr = XSPFDocumentUtility.getElementText(trackElem, XSPFDocumentNamespaces.XSPF, "duration");
		Duration duration = DurationUtilities.parseMilisDuration(durationStr);

		Metadata metadata = extractMetadata(trackElem, "track"); //$NON-NLS-1$

		return new Track(bundle, identifier, title, description, duration, metadata);
	}

	
}
