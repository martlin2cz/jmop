package cz.martlin.jmop.common.musicbase;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicbaseLoading {
	///////////////////////////////////////////////////////////////////////////
	
	public void addBundle(Bundle bundle) throws JMOPSourceException;
	
	public void addPlaylist(Playlist playlist) throws JMOPSourceException;
	
	public void addTrack(Track track) throws JMOPSourceException;
	
	///////////////////////////////////////////////////////////////////////////
	
	public Set<Bundle> bundles() throws JMOPSourceException;

	public Set<Playlist> playlists(Bundle bundle) throws JMOPSourceException;

	public Set<Track> tracks(Bundle bundle) throws JMOPSourceException;

	///////////////////////////////////////////////////////////////////////////
}
