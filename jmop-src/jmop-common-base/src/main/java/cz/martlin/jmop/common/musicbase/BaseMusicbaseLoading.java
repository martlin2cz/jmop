package cz.martlin.jmop.common.musicbase;

import java.util.List;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicbaseLoading {
	///////////////////////////////////////////////////////////////////////////
	public List<String> bundlesNames() throws JMOPSourceException;

	public Bundle getBundle(String name) throws JMOPSourceException;

	///////////////////////////////////////////////////////////////////////////
	public List<String> playlistsNames(Bundle bundle) throws JMOPSourceException;

	public Playlist getPlaylist(Bundle bundle, String name) throws JMOPSourceException;

	///////////////////////////////////////////////////////////////////////////
	public List<String> listTracksIDs(Bundle bundle) throws JMOPSourceException;

	public Track getTrack(Bundle bundle, String id) throws JMOPSourceException;
	
	///////////////////////////////////////////////////////////////////////////
}
