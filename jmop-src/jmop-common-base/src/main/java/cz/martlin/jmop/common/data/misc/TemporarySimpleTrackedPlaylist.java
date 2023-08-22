package cz.martlin.jmop.common.data.misc;

import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

/**
 * A little tricky playlist containing just one track.
 * 
 * If user wants to play one single track, it actually has to be encapsulated
 * into playlist to be playable. This is this class. It doesn't have name (it
 * gets set to track title), it's not persistent, it's not supposed to be
 * stored, persisted. It's just one-time-use object.
 * 
 * 
 * @author martin
 *
 */
public class TemporarySimpleTrackedPlaylist extends Playlist {

	/**
	 * Create the playlist of the given track.
	 * 
	 * @param track
	 */
	public TemporarySimpleTrackedPlaylist(Track track) {
		super(track.getBundle(), track.getTitle(), Metadata.createNew());

		this.getTracks().getTracks().add(track);
	}

}
