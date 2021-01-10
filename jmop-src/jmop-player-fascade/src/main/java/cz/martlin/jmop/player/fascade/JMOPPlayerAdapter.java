package cz.martlin.jmop.player.fascade;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

/**
 * @deprecated replaced by {@link JMOPPlayer}.
 * @author martin
 *
 */
@Deprecated
public class JMOPPlayerAdapter {
	private final MusicbaseListingEncapsulator musicbaseListing;
	private final MusicbaseModyfiingEncapsulator musicbaseModyfiing;

	public JMOPPlayerAdapter(BaseMusicbase musicbase) {
		super();
		this.musicbaseListing = new MusicbaseListingEncapsulator(musicbase);
		this.musicbaseModyfiing = new MusicbaseModyfiingEncapsulator(musicbase);
	}
	
	/////////////////////////////////////////////////////////////////////////////////////
	
	public Bundle bundleOfName(String bundleNameOrNot) throws JMOPMusicbaseException {
		return musicbaseListing.getBundle(bundleNameOrNot);
	}
	
	public Playlist playlistOfName(Bundle bundleOrNull, String playlistNameOrNot) throws JMOPMusicbaseException {
		return musicbaseListing.getPlaylist(bundleOrNull, playlistNameOrNot);
	}
	
	public Track trackOfTitle(Bundle bundleOrNull, String trackTitleOrNot) throws JMOPMusicbaseException {
		return musicbaseListing.getTrack(bundleOrNull, trackTitleOrNot);
	}	
}
