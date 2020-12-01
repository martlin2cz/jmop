package cz.martlin.jmop.common.musicbase.dflt;

import java.util.List;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;

public class DefaultMusicbase implements BaseMusicbase {
//TODO implement it all
	
	@Override
	public List<String> bundlesNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Bundle getBundle(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> playlistsNames(Bundle bundle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> listTracksIDs(Bundle bundle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
		public Track getTrack(Bundle bundle, String id) {
			// TODO Auto-generated method stub
			return null;
		}

	@Override
	public Bundle createBundle(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renameBundle(Bundle bundle, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeBundle(Bundle bundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMetadata(Bundle bundle, Metadata newMetadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Playlist createPlaylist(Bundle bundle, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renamePlaylist(Playlist playlist, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle newBundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePlaylist(Playlist playlist) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMetadata(Playlist playlist, Metadata newMetadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Track createTrack(Bundle bundle, TrackData data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renameTrack(Track track, String newName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveTrack(Track track, Bundle newBundle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTrack(Track track) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMetadata(Track track, Metadata newMetadata) {
		// TODO Auto-generated method stub
		
	}


}
