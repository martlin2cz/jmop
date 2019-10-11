package cz.martlin.jmop.core.sources.locals.electronic.xspf;

import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.local.BaseTracksLocalSource;
import cz.martlin.jmop.core.sources.local.TrackFileFormat;
import cz.martlin.jmop.core.sources.local.TrackFileLocation;
import cz.martlin.jmop.core.sources.local.misc.flu.FormatsLocationsUtility;

public class XSPFpflsWithFiles extends XSPFPlaylistFilesLoaderStorer {
	private final BaseTracksLocalSource tracks;
	private final FormatsLocationsUtility flu;

	public XSPFpflsWithFiles(BaseConfiguration config, BaseTracksLocalSource tracks, FormatsLocationsUtility flu) {
		super(config);

		this.tracks = tracks;
		this.flu = flu;
	}

	@Override
	protected Element pushTrack(Document document, Element tracklist, Track track) {
		Element trackElem = super.pushTrack(document, tracklist, track);

		pushTrackFile(document, track, trackElem);
		// TODO thumbnail?

		return trackElem;
	}

	protected void pushTrackFile(Document document, Track track, Element trackElem) {
		String fileName = nameOfFile(track);
		if (fileName != null) {
			XSPFDocumentUtility.createElementWithText(document, trackElem, XSPFDocumentNamespaces.XSPF, "file", fileName);
		}
	}

	private String nameOfFile(Track track) {
		try {
			TrackFileLocation location = flu.saveLocation();
			TrackFileFormat format = flu.saveFormat();
			File file = tracks.fileOfTrack(track, location, format);

			return file.getName();
		} catch (JMOPSourceException e) {
			//TODO warn?
			return null;
		}
	}

}
