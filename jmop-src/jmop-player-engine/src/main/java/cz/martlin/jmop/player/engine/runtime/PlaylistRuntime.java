package cz.martlin.jmop.player.engine.runtime;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.data.model.Tracklist;
import cz.martlin.jmop.core.misc.ObservableObject;

/**
 * The runtime class of playlist(er). Holds list of tracks and position of
 * current track. Specifies current, previous (last played) and next (next to be
 * played) tracks, and performs actions like toNext, toPreivous or play choosen.
 * 
 * When changed fires event. To create instance use static methods
 * {@link #of(Playlist)} or {@link #of(Tracklist)}.
 * 
 * @author martin
 *
 */
public class PlaylistRuntime extends ObservableObject<PlaylistRuntime> {
	private final Playlist playlist;

	public PlaylistRuntime(Playlist playlist) {
		this.playlist = playlist;
	}

	/**
	 * Returns current count of tracks.
	 * 
	 * @return
	 */
	public int count() {
		return playlist.getTracks().getTracks().size();
	}

	/**
	 * Returns index of current track.
	 * 
	 * @return
	 */
	public int currentTrackIndex() {
		return playlist.getCurrentTrackIndex();
	}

	/**
	 * Returns count of played tracks.
	 * 
	 * @return
	 */
	public int playedCount() {
		return playlist.getCurrentTrackIndex();
	}

	/**
	 * Returns count of remaining tracks.
	 * 
	 * @return
	 */
	public int remainingCount() {
		return playlist.getTracks().count() - playlist.getCurrentTrackIndex() - 1;
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns track at given index.
	 * 
	 * @param index
	 * @return
	 */
	public Track get(int index) {
		return playlist.getTracks().getTrack(index);
	}

	/**
	 * Return track played before the current one.
	 * 
	 * @return
	 */
	public Track lastWasPlayed() {
		return playlist.getTracks().getTrack(playlist.getCurrentTrackIndex() - 1);
	}

	/**
	 * Returns the current track.
	 * 
	 * @return
	 */
	public Track current() {
		return playlist.getTracks().getTrack(playlist.getCurrentTrackIndex());
	}

	/**
	 * Returns next track to be played after the current one.
	 * 
	 * @return
	 */
	public Track nextToBePlayed() {
		return playlist.getTracks().getTrack(playlist.getCurrentTrackIndex() + 1);
	}

	/**
	 * Lists all played tracks.
	 * 
	 * @return
	 */
	public List<Track> played() {
		return playlist.getTracks().subList(0, playlist.getCurrentTrackIndex());
	}

	/**
	 * Lists all tracks which are enqued to be played.
	 * 
	 * @return
	 */
	public List<Track> toBePlayed() {
		return playlist.getTracks().subList(playlist.getCurrentTrackIndex() + 1, count());
	}

	/**
	 * Lists all tracks.
	 * 
	 * @return
	 */
	public List<Track> listAll() {
		return Collections.unmodifiableList(playlist.getTracks().getTracks());
	}


	/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Returns true if has next track to be played.
	 * 
	 * @return
	 */
	public boolean hasNextToPlay() {
		return remainingCount() > 0;
	}

	/**
	 * Returns true if has some track played.
	 * 
	 * @return
	 */
	public boolean hasPlayed() {
		return playedCount() > 0;
	}

	/**
	 * Shifts current track index to next. Returns new current track.
	 * 
	 * @return
	 */
	public Track toNext() {
		playlist.setCurrentTrackIndex(playlist.getCurrentTrackIndex() + 1);
		fireValueChangedEvent();

		return current();
	}

	/**
	 * Shifts current track index to previous. Returns new current track.
	 * 
	 * @return
	 */
	public Track toPrevious() {
		playlist.setCurrentTrackIndex(playlist.getCurrentTrackIndex() - 1);
		fireValueChangedEvent();

		return current();
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Plays the choosen track.
	 * 
	 * @param index
	 */
	public Track play(int index) {
		playlist.setCurrentTrackIndex(index);
		fireValueChangedEvent();
		
		return current();
	}
	

	/////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		return "PlaylistRuntime [count=" + count() + ", currentTrackIndex=" + currentTrackIndex() // //$NON-NLS-1$ //$NON-NLS-2$
				+ ", played=" + played().stream().map((t) -> t.getTitle()).collect(Collectors.joining(",")) // //$NON-NLS-1$ //$NON-NLS-2$
				+ ", current=" + current().getTitle() // //$NON-NLS-1$
				+ ", toBePlayed=" + toBePlayed().stream().map((t) -> t.getTitle()).collect(Collectors.joining(",")) // //$NON-NLS-1$ //$NON-NLS-2$
				+ "]"; //$NON-NLS-1$
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Constructs runtime for given tracklist.
	 * 
	 * @param tracklist
	 * @return
	 * @deprecated call directly the c-tor
	 */
	@Deprecated
	public static PlaylistRuntime of(Tracklist tracklist) {
		throw new UnsupportedOperationException("deprecated");
	}

	/**
	 * Constructs runtime for given playlist.
	 * 
	 * @param playlist
	 * @return
	 * @deprecated call directly the c-tor
	 */
	@Deprecated
	public static PlaylistRuntime of(Playlist playlist) {
		return new PlaylistRuntime(playlist);
	}

	/////////////////////////////////////////////////////////////////////////////////////////

}
