package cz.martlin.jmop.common.musicbase;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.core.misc.JMOPSourceException;

public interface BaseMusicbaseModifing {
	///////////////////////////////////////////////////////////////////////////
	public Bundle createBundle(String name) throws JMOPSourceException;

	public void renameBundle(Bundle bundle, String newName) throws JMOPSourceException;

	public void removeBundle(Bundle bundle) throws JMOPSourceException;

	public void updateMetadata(Bundle bundle, Metadata newMetadata) throws JMOPSourceException;

	//TODO save modified bundle !?
	//TODO XXX nope, already done by all the CRUDs of the playlists and tracks
	
	///////////////////////////////////////////////////////////////////////////
	public Playlist createPlaylist(Bundle bundle, String name) throws JMOPSourceException;

	public void renamePlaylist(Playlist playlist, String newName) throws JMOPSourceException;

	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPSourceException;

	public void removePlaylist(Playlist playlist) throws JMOPSourceException;

	public void updateMetadata(Playlist playlist, Metadata newMetadata) throws JMOPSourceException;

	//FIXME design better, now just call-me-maybe design
	public void saveModifiedTracklist(Playlist playlist) throws JMOPSourceException;
	
	///////////////////////////////////////////////////////////////////////////
	public Track createTrack(Bundle bundle, TrackData data) throws JMOPSourceException;

	public void renameTrack(Track track, String newTitle) throws JMOPSourceException;

	public void moveTrack(Track track, Bundle newBundle) throws JMOPSourceException;

	public void removeTrack(Track track) throws JMOPSourceException;

	public void updateMetadata(Track track, Metadata newMetadata) throws JMOPSourceException;

	///////////////////////////////////////////////////////////////////////////
}
