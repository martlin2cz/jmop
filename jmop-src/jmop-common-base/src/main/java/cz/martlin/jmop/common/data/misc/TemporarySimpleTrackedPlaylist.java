package cz.martlin.jmop.common.data.misc;

import cz.martlin.jmop.common.data.model.Metadata;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

public class TemporarySimpleTrackedPlaylist extends Playlist {

	public TemporarySimpleTrackedPlaylist(Track track) {
		super(track.getBundle(), track.getTitle(), Metadata.createNew());
		
		this.getTracks().getTracks().add(track);
	}

	

}
