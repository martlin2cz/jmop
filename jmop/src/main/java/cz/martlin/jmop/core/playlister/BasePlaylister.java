package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;

public interface BasePlaylister {

	public PlaylistRuntime getRuntime();

	/////////////////////////////////////////////////////////////////////////////////////////


	/////////////////////////////////////////////////////////////////////////////////////////

	public boolean hasPrevious();

	public boolean hasNext();

	public Track obtainPrevious();

	public Track obtainNext();

	public Track nextToPlay();

	public Track playChoosen(int index);

	/////////////////////////////////////////////////////////////////////////////////////////

	public void addTrack(Track track);

	void startPlayingPlaylist(Playlist playlist, PlaylistRuntime runtime);

	void stopPlayingPlaylist();

}
