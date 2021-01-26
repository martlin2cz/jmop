package cz.martlin.jmop.common.musicbase;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseMusicbaseLoading {
	///////////////////////////////////////////////////////////////////////////
	
	public void addBundle(Bundle bundle) ;
	
	public void addPlaylist(Playlist playlist) ;
	
	public void addTrack(Track track) ;
	
	///////////////////////////////////////////////////////////////////////////
	
	public Set<Bundle> bundles() ;

	public Set<Playlist> playlists(Bundle bundle) ;

	public Set<Track> tracks(Bundle bundle) ;

	///////////////////////////////////////////////////////////////////////////
}
