package cz.martlin.jmop.common.musicbase;

import java.io.File;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

/**
 * The modifiing operations of the musicbase.
 * 
 * @author martin
 *
 */
public interface BaseMusicbaseModifing {
	
	///////////////////////////////////////////////////////////////////////////
	
	public Bundle createNewBundle(String name) ;
	
	public void renameBundle(Bundle bundle, String newName) ;

	public void removeBundle(Bundle bundle) ;

	public void bundleUpdated(Bundle bundle) ;

	///////////////////////////////////////////////////////////////////////////
	
	public Playlist createNewPlaylist(Bundle bundle, String name) ;

	public void renamePlaylist(Playlist playlist, String newName) ;

	public void movePlaylist(Playlist playlist, Bundle newBundle) ;

	public void removePlaylist(Playlist playlist) ;

	public void playlistUpdated(Playlist playlist) ;
	
	///////////////////////////////////////////////////////////////////////////
	
	public Track createNewTrack(Bundle bundle, TrackData data, TrackFileCreationWay trackFileHow, File trackSourceFile) ;
	
	public void specifyTrackFile(Track track, TrackFileCreationWay trackFileHow, File trackSourceFile) ;

	public void renameTrack(Track track, String newTitle) ;

	public void moveTrack(Track track, Bundle newBundle) ;

	public void removeTrack(Track track) ;

	public void trackUpdated(Track track) ;

	///////////////////////////////////////////////////////////////////////////
}
