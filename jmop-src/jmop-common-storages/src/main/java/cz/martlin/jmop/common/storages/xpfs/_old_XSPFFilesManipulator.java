package cz.martlin.jmop.common.storages.xpfs;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.common.musicbase.TracksSource;
import cz.martlin.jmop.common.storages.playlists.AbstractXMLEdtendedPlaylistManipulator;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * Replaced by {@link XSPFPlaylistFilesManipulator}
 * @author martin
 *
 */
@Deprecated
public class _old_XSPFFilesManipulator extends AbstractXMLEdtendedPlaylistManipulator {
	private static final String XSPF_VERSION = "1"; //$NON-NLS-1$
	public static final String FILE_EXTENSION = "xspf"; //$NON-NLS-1$
	protected static final String APPLICATION_URL = "https://github.com/martlin2cz/jmop"; //$NON-NLS-1$
	private final BaseErrorReporter reporter;

	public _old_XSPFFilesManipulator(BaseErrorReporter reporter) {
		super();

		this.reporter = reporter;
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
		// Note: call pushPlaylistData(...) first
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
		TrackIndex currentTrackIndex = extractPlaylistCurrentTrack(root);
		Metadata metadata = extractMetadata(root, "playlist");
		Tracklist tracklist = extractTracks(bundle, tracks, root);

		return new Playlist(bundle, name, tracklist, currentTrackIndex, metadata);
	}

	@Override
	protected void pushPlaylistIntoDocument(Playlist playlist, boolean withTrackInfo, Document document) {
		Element root = createRootElement(document);

		pushPlaylistDataIntoDocument(document, root, playlist, withTrackInfo);
	}

	private void pushPlaylistDataIntoDocument(Document document, Element root, Playlist playlist,
			boolean withTrackInfo) {
		String name = playlist.getName();
		pushName(document, root, name);

		TrackIndex currentTrack = playlist.getCurrentTrackIndex();
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
		_old_XSPFDocumentUtility.createElementWithText(document, root, XSPFDocumentNamespaces.XSPF, "title", name);
	}

	private void pushBundleName(Document document, Element root, String name) {
		_old_XSPFDocumentUtility.setExtensionValue(document, root, "bundle", "name", name);
	}

	private void pushMetadata(Document document, Element element, String extensionElement, Metadata metadata) {
		String createdStr = _old_XSPFDocumentUtility.stringifyDatetime(metadata.getCreatedCal());
		_old_XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "created", createdStr);

		String numberOfPlaysStr = Integer.toString(metadata.getNumberOfPlays());
		_old_XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "numberOfPlays", numberOfPlaysStr);

		String lastPlayedStr = _old_XSPFDocumentUtility.stringifyDatetime(metadata.getLastPlayedCal());
		_old_XSPFDocumentUtility.setExtensionValue(document, element, extensionElement, "lastPlayed", lastPlayedStr);
	}

	private void pushPlaylistCurrentTrack(Document document, Element root, TrackIndex currentTrack) {
		String currTrackStr = Integer.toString(currentTrack.getHuman());
		_old_XSPFDocumentUtility.setExtensionValue(document, root, "playlist", "currentTrack", currTrackStr);
	}

	private void pushTracks(Document document, Element root, Tracklist tracks, boolean withTrackInfo) {
		Element tracklist = _old_XSPFDocumentUtility.getChildOrCreate(document, root, XSPFDocumentNamespaces.XSPF,
				"trackList");

		tracks.getTracks().forEach( //
				(t) -> pushTrack(document, tracklist, t, withTrackInfo));
	}

	protected Element pushTrack(Document document, Element tracklist, Track track, boolean withTrackInfo) {
		Element trackElem = _old_XSPFDocumentUtility.createElement(document, tracklist, XSPFDocumentNamespaces.XSPF,
				"track");

		String name = track.getTitle();
		_old_XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "title", name);

		String id = track.getIdentifier();
		_old_XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "identifier", id);

		// TODO identifier into extension?
		// TODO full url -> identifier (use remote querier)?

		String annot = track.getDescription();
		_old_XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "annotation",
				annot);

		String durationStr = DurationUtilities.toMilis(track.getDuration());
		String durationHumanStr = DurationUtilities.toHumanString(track.getDuration());
		_old_XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "duration",
				durationStr);
		_old_XSPFDocumentUtility.createCommentWithText(document, trackElem, durationHumanStr);

		if (withTrackInfo) {
			Metadata metadata = track.getMetadata();
			pushMetadata(document, trackElem, "track", metadata);
		}

		return trackElem;
	}

///////////////////////////////////////////////////////////////////////////

	private String extractName(Element root) {
		return get(root, XSPFDocumentNamespaces.XSPF, "title");
	}

	private String extractBundleName(Element root) {
		return get(root, "bundle", "name");
	}

	private TrackIndex extractPlaylistCurrentTrack(Element root) {
		return get(root, "playlist", "currentTrack", //
				(v) -> TrackIndex.ofHuman(Integer.parseInt(v)), //
				TrackIndex.ofIndex(0));
	}

	private Metadata extractMetadata(Element root, String extensionElementName) {
		Calendar lastPlayed = get(root, extensionElementName, "lastPlayed",
				(v) -> _old_XSPFDocumentUtility.parseDatetime(v), null);

		int numberOfPlays = get(root, extensionElementName, "numberOfPlays",
				(v) -> Integer.parseInt(v), 0);

		Calendar created = get(root, extensionElementName, "created",
				(v) -> _old_XSPFDocumentUtility.parseDatetime(v), Calendar.getInstance());

		return Metadata.createExisting(created, lastPlayed, numberOfPlays);
	}

	private Tracklist extractTracks(Bundle bundle, Map<String, Track> tracks, Element root) {
		Element trackList = _old_XSPFDocumentUtility.getChildOrFail(root, XSPFDocumentNamespaces.XSPF, "trackList"); //$NON-NLS-1$

		List<Track> result = new ArrayList<>(trackList.getChildNodes().getLength());

		_old_XSPFDocumentUtility.iterateOverChildrenOrFail(trackList, XSPFDocumentNamespaces.XSPF, "track", (te) -> {
			Track track = pickOrExtractTrack(bundle, tracks, te);
			if (track != null) {
				result.add(track);
			}
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
		String title = get(trackElem, XSPFDocumentNamespaces.XSPF, "title");
		if (title == null) {
			return null;
		}
		
		if (!tracks.containsKey(title)) {
			Exception e = new IllegalStateException("The track " + title + " not found in the playlist");
			reporter.report("Track '" + title + "' does not exist", e);
			return null;
		}

		Track track = tracks.get(title);
		return track;
	}

	protected Track extractTrack(Bundle bundle, Element trackElem) {
		String title = get(trackElem, XSPFDocumentNamespaces.XSPF, "title");
		String identifier = get(trackElem, XSPFDocumentNamespaces.XSPF, "identifier");
		String description = get(trackElem, XSPFDocumentNamespaces.XSPF, "annotation");

		Duration duration = get(trackElem, XSPFDocumentNamespaces.XSPF, "duration",
				(v) -> DurationUtilities.parseMilisDuration(v), null);

		Metadata metadata = extractMetadata(trackElem, "track"); //$NON-NLS-1$

		return new Track(bundle, identifier, title, description, duration, metadata);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	private String get(Element owner, XSPFDocumentNamespaces elemNS, String elemName) {
		return get(owner, elemNS, elemName, AttrValToValueMapper.IDENTITY, null);
	}
	
	private <E> E get(Element owner, XSPFDocumentNamespaces elemNS, String elemName,
			AttrValToValueMapper<E> valueParser, E fallBackValue) {

		String value;
		try {
			value = _old_XSPFDocumentUtility.getElementText(owner, elemNS, elemName);
		} catch (Exception e) {
			reporter.report("The file is missing the " + elemName + " element", e);
			return fallBackValue;
		}

		try {
			return valueParser.obtain(value);
		} catch (Exception e) {
			reporter.report("The '" + value + " is invalid for the " + elemName + " element", e);
			return fallBackValue;
		}
	}

	private String get(Element element, String jmopExtensionElementName, String attrName) {
		return get(element, jmopExtensionElementName, attrName, AttrValToValueMapper.IDENTITY, null);
	}
	
	private <E> E get(Element element, String jmopExtensionElementName, String attrName,
			AttrValToValueMapper<E> valueParser, E fallBackValue) {

		String value;
		try {
			value = _old_XSPFDocumentUtility.getExtensionValue(element, jmopExtensionElementName, attrName);
		} catch (Exception e) {
			reporter.report("The file is missing the " + attrName + " attribute " //
					+ "of the " + jmopExtensionElementName + " extension element", e);
			return fallBackValue;
		}

		try {
			return valueParser.obtain(value);
		} catch (Exception e) {
			reporter.report("The '" + value + " is invalid for the " + attrName + " attribute", e);
			return fallBackValue;
		}
	}

	@FunctionalInterface
	public static interface AttrValToValueMapper<T> {
		T obtain(String value) throws Exception;
		
		public static AttrValToValueMapper<String> IDENTITY = (s) -> s;
	}

	@Override
	public void savePlaylistWithBundle(Playlist playlist, File file, TracksSource tracks)
			throws JMOPPersistenceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveOnlyPlaylist(Playlist playlist, File file, TracksSource tracks) throws JMOPPersistenceException {
		// TODO Auto-generated method stub
		
	}

}
