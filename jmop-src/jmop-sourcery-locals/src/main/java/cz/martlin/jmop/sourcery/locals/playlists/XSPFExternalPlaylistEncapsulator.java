package cz.martlin.jmop.sourcery.locals.playlists;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.sourcery.locals.abstracts.BaseExternalPlaylistEncapsulator;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;
import cz.martlin.xspf.util.XSPFException;
import javafx.util.Duration;

/**
 * The encapsulator of the external XSPF playlist file.
 * 
 * @author martin
 *
 */
public class XSPFExternalPlaylistEncapsulator extends BaseExternalPlaylistEncapsulator<XSPFPlaylist, XSPFTrack> {

	private static final Comparator<XSPFTrack> TRACK_NUM_COMPARATOR = new TrackNumComparator();

	@Override
	public XSPFPlaylist loadPlaylist(File file) throws IOException {
		try {
			return XSPFFile.load(file).playlist();
		} catch (XSPFException e) {
			throw new IOException("Cannot load playlist file", e);
		}
	}

	@Override
	public String obtainName(XSPFPlaylist xplaylist) throws IOException {
		try {
			return xplaylist.getTitle();
		} catch (XSPFException e) {
			throw new IOException("Cannot load playlist tracks", e);
		}
	}

	@Override
	protected List<XSPFTrack> obtainRawTracks(XSPFPlaylist xplaylist) throws IOException {
		try {
			return xplaylist.tracks().list() //
					.sorted(TRACK_NUM_COMPARATOR) //
					.collect(Collectors.toList()); //
		} catch (XSPFException e) {
			throw new IOException("Cannot load playlist tracks", e);
		}
	}

	@Override
	protected TrackData convertTrack(XSPFTrack xtrack) throws IOException {
		try {
			String title = xtrack.getTitle();
			String description = xtrack.getAnnotation();
			Duration duration = DurationUtilities.durationToDuration(xtrack.getDuration());
			URI uri = xtrack.getInfo();
			File file = (xtrack.getLocation() != null ? new File(xtrack.getLocation()) : null);
			return new TrackData(title, description, duration, uri, file);
		} catch (Exception e) {
			throw new IOException("Cannot convert track", e);
		}
	}


	public static class TrackNumComparator implements Comparator<XSPFTrack> {

		@Override
		public int compare(XSPFTrack t1, XSPFTrack t2) {
			try {
				return Integer.compare(t1.getTrackNum(), t2.getTrackNum());
			} catch (Exception e) {
				LOGGER.error("Cannot obtain tracks number: " + t1 + " and/or " + t2, e);
				return 0;
			}
		}

	}

	
}
