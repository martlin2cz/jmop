package cz.martlin.jmop.common.musicbase;

import java.util.Set;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicbaseLoading {
	///////////////////////////////////////////////////////////////////////////
	public Set<Bundle> bundles() throws JMOPSourceException;

	public Set<Playlist> playlists(Bundle bundle) throws JMOPSourceException;

	public Set<Track> tracks(Bundle bundle) throws JMOPSourceException;

	///////////////////////////////////////////////////////////////////////////
}
