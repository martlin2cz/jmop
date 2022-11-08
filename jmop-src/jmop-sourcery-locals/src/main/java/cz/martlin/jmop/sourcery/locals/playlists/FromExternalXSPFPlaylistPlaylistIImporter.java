package cz.martlin.jmop.sourcery.locals.playlists;

import cz.martlin.jmop.sourcery.locals.abstracts.FromExternalPlaylistPlaylistImpoter;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.playlist.elements.XSPFTrack;

/**
 * The playlist importer importing from external XSPF playlist file.
 * 
 * @author martin
 *
 */
public class FromExternalXSPFPlaylistPlaylistIImporter
		extends FromExternalPlaylistPlaylistImpoter<XSPFPlaylist, XSPFTrack> {

	public FromExternalXSPFPlaylistPlaylistIImporter() {
		super(new XSPFExternalPlaylistEncapsulator());
	}
}
