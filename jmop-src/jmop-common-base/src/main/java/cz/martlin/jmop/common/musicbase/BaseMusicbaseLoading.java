package cz.martlin.jmop.common.musicbase;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseMusicbaseLoading {
	///////////////////////////////////////////////////////////////////////////
	
	public void addBundle(Bundle bundle) throws JMOPMusicbaseException;
	
	public void addPlaylist(Playlist playlist) throws JMOPMusicbaseException;
	
	public void addTrack(Track track) throws JMOPMusicbaseException;
	
	///////////////////////////////////////////////////////////////////////////
	
	public Set<Bundle> bundles() throws JMOPMusicbaseException;

	public Set<Playlist> playlists(Bundle bundle) throws JMOPMusicbaseException;

	public Set<Track> tracks(Bundle bundle) throws JMOPMusicbaseException;

	///////////////////////////////////////////////////////////////////////////
}
