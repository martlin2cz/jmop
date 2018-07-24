package cz.martlin.jmop.core.sources.local;

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

public class XSPFParserComposer {

	private static final String NAMESPACE = "http://xspf.org/ns/0/";
	private static final String APPLICATION = "https://github.com/martlin2cz/jmop";

	public void parse(Bundle bundle, Document document, PlaylistFileData playlist) {
		Element root = document.getDocumentElement();
		checkRoot(root);
		processHeaders(root, playlist);
		processTracks(bundle, root, playlist);
	}

	private void checkRoot(Element root) {
		if (!"playlist".equals(root.getTagName())) {
			throw new IllegalArgumentException("Not a playlist file");
		}

		if (!"1".equals(root.getAttribute("version"))) {
			throw new IllegalArgumentException("Not version 1");
		}
	}

	private void processHeaders(Element root, PlaylistFileData playlist) {
		processTitle(root, playlist);
		processExtension(root, playlist);
	}

	private void processTitle(Element root, PlaylistFileData playlist) {
		Element title = getChild(root, "title");
		playlist.setPlaylistName(title.getTextContent());
	}

	private void processExtension(Element root, PlaylistFileData playlist) {
		Element extension = getChild(root, "extension");
		Element jmop = getChild(extension, "jmop");

		String source = jmop.getAttribute("source");
		SourceKind kind = SourceKind.valueOf(source);
		playlist.setKind(kind);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	private static Element getChild(Element element, String tagName) {
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Element && tagName.equals(node.getNodeName())) {
				return (Element) node;
			}
		}
		throw new IllegalArgumentException("Element " + tagName + " not found");
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private void processTracks(Bundle bundle, Element root, PlaylistFileData playlist) {
		Element trackList = getChild(root, "trackList");

		List<Track> tracks = new ArrayList<>(trackList.getChildNodes().getLength());
		for (int i = 0; i < trackList.getChildNodes().getLength(); i++) {
			Node child = trackList.getChildNodes().item(i);

			if (!(child instanceof Element)) {
				continue;
			}
			Element trackElem = (Element) child;

			if (!("track".equals(child.getNodeName()))) {
				throw new IllegalArgumentException("Expected track element, found" + trackElem.getTagName());
			}

			Track track = processTrack(bundle, trackElem);
			tracks.add(track);
		}

		Tracklist tracklist = new Tracklist(tracks);
		playlist.setTracklist(tracklist);
	}

	private Track processTrack(Bundle bundle, Element trackElem) {
		Element titleElem = getChild(trackElem, "title");
		String title = titleElem.getTextContent();

		Element idElem = getChild(trackElem, "identifier");
		String identifier = idElem.getTextContent();

		Element annotationElem = getChild(trackElem, "annotation");
		String description = annotationElem.getTextContent();

		Element durationElem = getChild(trackElem, "duration");
		String durationStr = durationElem.getTextContent();
		Duration duration = DurationUtilities.parseMilisDuration(durationStr);

		return bundle.createTrack(identifier, title, description, duration);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void compose(PlaylistFileData playlist, Document document) {
		Element root = createRoot(playlist, document);
		document.appendChild(root);
	}

	private Element createRoot(PlaylistFileData playlist, Document document) {
		Element root = createRootElement(document);
		processHeaders(root, playlist, document);
		processTracks(root, playlist, document);

		return root;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private Element createRootElement(Document document) {
		Element root = document.createElementNS(NAMESPACE, "playlist");

		root.setAttribute("version", "1");
		// FIXME hack: attr not under NS

		return root;
	}

	private void processHeaders(Element root, PlaylistFileData playlist, Document document) {
		Element title = document.createElementNS(NAMESPACE, "title");
		title.setTextContent(playlist.getPlaylistName());
		root.appendChild(title);

		Element creator = document.createElementNS(NAMESPACE, "creator");
		creator.setTextContent("jmop application");
		root.appendChild(creator);

		Element date = document.createElementNS(NAMESPACE, "date");
		date.setTextContent(java.util.Calendar.getInstance().getTime().toString());
		root.appendChild(date);

		Element extension = document.createElementNS(NAMESPACE, "extension");
		Element jmop = document.createElement("jmop");
		jmop.setAttribute("source", playlist.getKind().name());
		jmop.setAttribute("application", APPLICATION);
		extension.appendChild(jmop);
		root.appendChild(extension);
	}

	private void processTracks(Element root, PlaylistFileData playlist, Document document) {
		Element trackList = document.createElementNS(NAMESPACE, "trackList");

		for (Track track : playlist.getTracklist().getTracks()) {
			Element trackElem = processTrack(track, document);
			trackList.appendChild(trackElem);
		}

		root.appendChild(trackList);
	}

	private Element processTrack(Track track, Document document) {
		Element trackElem = document.createElementNS(NAMESPACE, "track");

		Element identifier = document.createElementNS(NAMESPACE, "identifier");
		identifier.setTextContent(track.getIdentifier());
		trackElem.appendChild(identifier);

		Element title = document.createElementNS(NAMESPACE, "title");
		title.setTextContent(track.getTitle());
		trackElem.appendChild(title);

		Element annotation = document.createElementNS(NAMESPACE, "annotation");
		annotation.setTextContent(track.getDescription());
		trackElem.appendChild(annotation);

		Element duration = document.createElementNS(NAMESPACE, "duration");
		duration.setTextContent(DurationUtilities.toMilis(track.getDuration()));
		trackElem.appendChild(duration);

		Comment durationComment = document.createComment(DurationUtilities.toHumanString(track.getDuration()));
		trackElem.appendChild(durationComment);

		return trackElem;
	}

}
