package cz.martlin.jmop.sourcery.locals.playlists;

import cz.martlin.jmop.sourcery.locals.abstracts.FromPlaylistFileTracksImporter;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;

/**
 * The importer of te tracks from the external XSPF playlist file.
 * 
 * @author martin
 *
 */
public class FromXSPFPlaylistTracksImpoter extends FromPlaylistFileTracksImporter<XSPFPlaylist, XSPFTrack> {

	public FromXSPFPlaylistTracksImpoter() {
		super(new XSPFExternalPlaylistEncapsulator());
	}
}
