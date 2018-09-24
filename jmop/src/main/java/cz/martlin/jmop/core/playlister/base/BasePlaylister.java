package cz.martlin.jmop.core.playlister.base;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.playlister.PlayerEngine;

/**
 * Base playlister, class performing going thought playlist (playlist runtime).
 * Subclasses could do more than just simply delegating to the runtime, for
 * instance do some checks, loading, preparing, reporting, or simply ignoring
 * the runtime and playing on its own (for instance randomly).
 * 
 * @author martin
 *
 */
public interface BasePlaylister {

	/**
	 * Starts playing given playlist (prepares to play, not the start playing
	 * itself) with given playlister (the subclasses could ignore it, but it is
	 * good practice to keep reference to it since the same runtime is shared
	 * between the others playlisters and change in one may reflect into
	 * others).
	 * @param engine TODO
	 * @param playlist
	 * @param runtime
	 */
	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist, PlaylistRuntime runtime);

	/**
	 * Stops playing playlist (may not be working after this method).
	 */
	public void stopPlayingPlaylist();
	/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns the runtime played within this playlister. Runtime may be set by
	 * {@link #startPlayingPlaylist(PlayerEngine, Playlist, PlaylistRuntime)}.
	 * 
	 * @return
	 */
	@Deprecated
	public PlaylistRuntime getRuntime();

	/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns true if has at least one track to be played.
	 * @return
	 */
	public boolean hasAtLeastOneTrack();
	
	/**
	 * Returns true, if previous track can be played.
	 * 
	 * @return
	 */
	public boolean hasPrevious();

	/**
	 * Returns true if next track can be played.
	 * 
	 * @return
	 */
	public boolean hasNext();

	/**
	 * Returns previous track (last played).
	 * 
	 * @return
	 */
	public Track getPrevious();

	/**
	 * Returns current track (track currently beeing played or to be played).
	 * 
	 * @return
	 */
	public Track getCurrent();

	/**
	 * Returns next track (track to be played after the current one).
	 * 
	 * @return
	 */
	public Track getNext();

	/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Goes to the next track. Returns new current track.
	 * 
	 * @return
	 */
	public Track toNext();

	/**
	 * Goes to the previous track. Returns new current track.
	 * 
	 * @return
	 */
	public Track toPrevious();

	/**
	 * Plays track at given index. Returns new current track.
	 * 
	 * @param index
	 * @return
	 */
	public Track playChoosen(int index);

	/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * (Somehow) adds the given track.
	 * 
	 * @param track
	 */
	public void addTrack(Track track);

}
