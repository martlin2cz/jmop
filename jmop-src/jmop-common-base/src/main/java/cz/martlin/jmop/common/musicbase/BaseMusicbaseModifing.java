package cz.martlin.jmop.common.musicbase;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public interface BaseMusicbaseModifing {
	
	///////////////////////////////////////////////////////////////////////////
	
	public Bundle createNewBundle(String name) throws JMOPMusicbaseException;

	public void renameBundle(Bundle bundle, String newName) throws JMOPMusicbaseException;

	public void removeBundle(Bundle bundle) throws JMOPMusicbaseException;

	public void bundleUpdated(Bundle bundle) throws JMOPMusicbaseException;

	///////////////////////////////////////////////////////////////////////////
	
	public Playlist createNewPlaylist(Bundle bundle, String name) throws JMOPMusicbaseException;

	public void renamePlaylist(Playlist playlist, String newName) throws JMOPMusicbaseException;

	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPMusicbaseException;

	public void removePlaylist(Playlist playlist) throws JMOPMusicbaseException;

	public void playlistUpdated(Playlist playlist) throws JMOPMusicbaseException;
	
	///////////////////////////////////////////////////////////////////////////
	
	public Track createNewTrack(Bundle bundle, TrackData data) throws JMOPMusicbaseException;

	public void renameTrack(Track track, String newTitle) throws JMOPMusicbaseException;

	public void moveTrack(Track track, Bundle newBundle) throws JMOPMusicbaseException;

	public void removeTrack(Track track) throws JMOPMusicbaseException;

	public void trackUpdated(Track track) throws JMOPMusicbaseException;

	///////////////////////////////////////////////////////////////////////////
}
