package cz.martlin.jmop.core.sources.locals.funky;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.PlaylistFileData;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

/**
 * Parser and composer of playlists in the XSPF format.
 * 
 * @author martin
 *
 */
public class XSPFParserComposer {
	public static final String FILE_EXTENSION = "xspf"; //$NON-NLS-1$

	private static final String NAMESPACE = "http://xspf.org/ns/0/"; //$NON-NLS-1$
	private static final String APPLICATION = "https://github.com/martlin2cz/jmop"; //$NON-NLS-1$

	/**
	 * Parses XSPF file and loads its contents. If onlyMetadata is set to true,
	 * loads only the informations about the playlist and bundle, not the
	 * tracks.
	 * 
	 * @param bundle
	 * @param document
	 * @param playlist
	 * @param onlyMetadata
	 */
	public void parse(Bundle bundle, Document document, PlaylistFileData playlist, boolean onlyMetadata) {
		Element root = document.getDocumentElement();
		checkRoot(root);
		processHeaders(root, playlist);

		if (!onlyMetadata) {
			processTracks(bundle, root, playlist);
		}
	}

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
	 * Transfers metadata from playlist file's root element into data.
	 * 
	 * @param root
	 * @param playlist
	 */
	private void processHeaders(Element root, PlaylistFileData playlist) {
		processTitle(root, playlist);
		processExtension(root, playlist);
	}

	/**
	 * Transfers title of playlist from xml to data.
	 * 
	 * @param root
	 * @param playlist
	 */
	private void processTitle(Element root, PlaylistFileData playlist) {
		Element title = getChild(root, "title"); //$NON-NLS-1$
		playlist.setPlaylistName(title.getTextContent());
	}

	/**
	 * Transfers JMOP extension data (source kind, bundle name, current track
	 * index) from xml to data.
	 * 
	 * @param root
	 * @param playlist
	 */
	private void processExtension(Element root, PlaylistFileData playlist) {
		Element extension = getChild(root, "extension"); //$NON-NLS-1$
		Element jmop = getChild(extension, "jmop"); //$NON-NLS-1$

		String source = jmop.getAttribute("source"); //$NON-NLS-1$
		SourceKind kind = SourceKind.valueOf(source);
		playlist.setKind(kind);

		String bundleName = jmop.getAttribute("bundleName"); //$NON-NLS-1$
		playlist.setBundleName(bundleName);

		String currentTrackOrNot = jmop.getAttribute("currentTrack"); //$NON-NLS-1$
		if (currentTrackOrNot != null && !currentTrackOrNot.isEmpty()) {
			int currentTrack = Integer.parseInt(currentTrackOrNot);
			playlist.setCurrentTrackIndex(currentTrack);
		}

		String lockedOrNot = jmop.getAttribute("locked"); //$NON-NLS-1$
		if (lockedOrNot != null && !lockedOrNot.isEmpty()) {
			boolean locked = Boolean.parseBoolean(lockedOrNot);
			playlist.setLocked(locked);
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns (first) child of given element with given tak name. If no such
	 * found, throws exception.
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	private static Element getChild(Element element, String tagName) {
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Element && tagName.equals(node.getNodeName())) {
				return (Element) node;
			}
		}
		throw new IllegalArgumentException("Element " + tagName + " not found"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Transfers tracks from xml to data.
	 * 
	 * @param bundle
	 * @param root
	 * @param playlist
	 */
	private void processTracks(Bundle bundle, Element root, PlaylistFileData playlist) {
		Element trackList = getChild(root, "trackList"); //$NON-NLS-1$

		List<Track> tracks = new ArrayList<>(trackList.getChildNodes().getLength());
		for (int i = 0; i < trackList.getChildNodes().getLength(); i++) {
			Node child = trackList.getChildNodes().item(i);

			if (!(child instanceof Element)) {
				continue;
			}
			Element trackElem = (Element) child;

			if (!("track".equals(child.getNodeName()))) { //$NON-NLS-1$
				throw new IllegalArgumentException("Expected track element, found" + trackElem.getTagName()); //$NON-NLS-1$
			}

			Track track = processTrack(bundle, trackElem);
			tracks.add(track);
		}

		Tracklist tracklist = new Tracklist(tracks);
		playlist.setTracklist(tracklist);
	}

	/**
	 * Transfers track element from xml to data.
	 * 
	 * @param bundle
	 * @param trackElem
	 * @return
	 */
	private Track processTrack(Bundle bundle, Element trackElem) {
		Element titleElem = getChild(trackElem, "title"); //$NON-NLS-1$
		String title = titleElem.getTextContent();

		Element idElem = getChild(trackElem, "identifier"); //$NON-NLS-1$
		String identifier = idElem.getTextContent();

		Element annotationElem = getChild(trackElem, "annotation"); //$NON-NLS-1$
		String description = annotationElem.getTextContent();

		Element durationElem = getChild(trackElem, "duration"); //$NON-NLS-1$
		String durationStr = durationElem.getTextContent();
		Duration duration = DurationUtilities.parseMilisDuration(durationStr);

		return bundle.createTrack(identifier, title, description, duration);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Saves given data into given (empty) xml document.
	 * 
	 * @param playlist
	 * @param document
	 */
	public void compose(PlaylistFileData playlist, Document document) {
		Element root = createRoot(playlist, document);
		document.appendChild(root);
	}

	/**
	 * Creates root element with both metadata and tracks.
	 * 
	 * @param playlist
	 * @param document
	 * @return
	 */
	private Element createRoot(PlaylistFileData playlist, Document document) {
		Element root = createRootElement(document);
		processHeaders(root, playlist, document);
		processTracks(root, playlist, document);

		return root;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates empty playlist root element.
	 * 
	 * @param document
	 * @return
	 */
	private Element createRootElement(Document document) {
		Element root = document.createElementNS(NAMESPACE, "playlist"); //$NON-NLS-1$

		root.setAttribute("version", "1"); //$NON-NLS-1$ //$NON-NLS-2$
		// FIXME hack: attr not under NS

		return root;
	}

	/**
	 * Transfers metadata from data into xml element and appends it to the root.
	 * 
	 * @param root
	 * @param playlist
	 * @param document
	 */
	private void processHeaders(Element root, PlaylistFileData playlist, Document document) {
		Element title = document.createElementNS(NAMESPACE, "title"); //$NON-NLS-1$
		title.setTextContent(playlist.getPlaylistName());
		root.appendChild(title);

		Element creator = document.createElementNS(NAMESPACE, "creator"); //$NON-NLS-1$
		creator.setTextContent("jmop application"); //$NON-NLS-1$
		root.appendChild(creator);

		Element date = document.createElementNS(NAMESPACE, "date"); //$NON-NLS-1$
		date.setTextContent(java.util.Calendar.getInstance().getTime().toString());
		root.appendChild(date);

		Element extension = document.createElementNS(NAMESPACE, "extension"); //$NON-NLS-1$
		Element jmop = document.createElement("jmop"); //$NON-NLS-1$

		jmop.setAttribute("source", playlist.getKind().name()); //$NON-NLS-1$
		jmop.setAttribute("bundleName", playlist.getBundleName()); //$NON-NLS-1$
		jmop.setAttribute("application", APPLICATION); //$NON-NLS-1$
		jmop.setAttribute("currentTrack", Integer.toString(playlist.getCurrentTrackIndex())); //$NON-NLS-1$
		jmop.setAttribute("locked", Boolean.toString(playlist.isLocked())); //$NON-NLS-1$

		extension.appendChild(jmop);
		root.appendChild(extension);
	}

	/**
	 * Transfers tracks from from data into xml element and appends it to root.
	 * 
	 * @param root
	 * @param playlist
	 * @param document
	 */
	private void processTracks(Element root, PlaylistFileData playlist, Document document) {
		Element trackList = document.createElementNS(NAMESPACE, "trackList"); //$NON-NLS-1$

		for (Track track : playlist.getTracklist().getTracks()) {
			Element trackElem = processTrack(track, document);
			trackList.appendChild(trackElem);
		}

		root.appendChild(trackList);
	}

	/**
	 * Transfers given track from data into xml element and returns the newly
	 * created element.
	 * 
	 * @param track
	 * @param document
	 * @return
	 */
	private Element processTrack(Track track, Document document) {
		Element trackElem = document.createElementNS(NAMESPACE, "track"); //$NON-NLS-1$

		Element identifier = document.createElementNS(NAMESPACE, "identifier"); //$NON-NLS-1$
		identifier.setTextContent(track.getIdentifier());
		trackElem.appendChild(identifier);

		Element title = document.createElementNS(NAMESPACE, "title"); //$NON-NLS-1$
		title.setTextContent(track.getTitle());
		trackElem.appendChild(title);

		Element annotation = document.createElementNS(NAMESPACE, "annotation"); //$NON-NLS-1$
		annotation.setTextContent(track.getDescription());
		trackElem.appendChild(annotation);

		Element duration = document.createElementNS(NAMESPACE, "duration"); //$NON-NLS-1$
		duration.setTextContent(DurationUtilities.toMilis(track.getDuration()));
		trackElem.appendChild(duration);

		Comment durationComment = document.createComment(DurationUtilities.toHumanString(track.getDuration()));
		trackElem.appendChild(durationComment);

		return trackElem;
	}

}
