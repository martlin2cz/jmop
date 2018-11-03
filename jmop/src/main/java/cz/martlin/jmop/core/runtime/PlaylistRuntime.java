package cz.martlin.jmop.core.runtime;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.ObservableObject;

/**
 * The runtime class of playlist. Holds list of tracks and position of current
 * track. Specifies current, previous (last played) and next (next to be played)
 * tracks, and performs actions like toNext, toPreivous or play choosen.
 * 
 * @author martin
 *
 */
public class PlaylistRuntime extends ObservableObject<PlaylistRuntime> {
	private final List<Track> tracks;
	private int currentTrack;

	public PlaylistRuntime() {
		this.tracks = new LinkedList<>();
		this.currentTrack = 0;
	}

	public PlaylistRuntime(List<Track> tracks) {
		this.tracks = new LinkedList<>(tracks);
		this.currentTrack = 0;
	}

	/**
	 * Returns current count of tracks.
	 * 
	 * @return
	 */
	public int count() {
		return tracks.size();
	}

	/**
	 * Returns index of current track.
	 * 
	 * @return
	 */
	public int currentTrackIndex() {
		return currentTrack;
	}

	/**
	 * Returns count of played tracks.
	 * 
	 * @return
	 */
	public int playedCount() {
		return currentTrack;
	}

	/**
	 * Returns count of remaining tracks.
	 * 
	 * @return
	 */
	public int remainingCount() {
		return tracks.size() - currentTrack - 1;
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns track at given index.
	 * 
	 * @param index
	 * @return
	 */
	public Track get(int index) {
		return tracks.get(index);
	}

	/**
	 * Return track played before the current one.
	 * 
	 * @return
	 */
	public Track lastWasPlayed() {
		return tracks.get(currentTrack - 1);
	}

	/**
	 * Returns the current track.
	 * 
	 * @return
	 */
	public Track current() {
		return tracks.get(currentTrack);
	}

	/**
	 * Returns next track to be played after the current one.
	 * 
	 * @return
	 */
	public Track nextToBePlayed() {
		return tracks.get(currentTrack + 1);
	}

	/**
	 * Lists all played tracks.
	 * 
	 * @return
	 */
	public List<Track> played() {
		return tracks.subList(0, currentTrack);
	}

	/**
	 * Lists all tracks which are enqued to be played.
	 * 
	 * @return
	 */
	public List<Track> toBePlayed() {
		return tracks.subList(currentTrack + 1, count());
	}

	/**
	 * Lists all tracks.
	 * 
	 * @return
	 */
	public List<Track> listAll() {
		return Collections.unmodifiableList(tracks);
	}

	/**
	 * Returns this runtime tracks as tracklist.
	 * 
	 * @return
	 */
	public Tracklist toTracklist() {
		return new Tracklist(tracks);
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
		currentTrack++;
		fireValueChangedEvent();

		return current();
	}

	/**
	 * Shifts current track index to previous. Returns new current track.
	 * 
	 * @return
	 */
	public Track toPrevious() {
		currentTrack--;
		fireValueChangedEvent();

		return current();
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Skips (or rollbacks) up to given index. In other terms, marks track at
	 * given index the current one.
	 * 
	 * @param index
	 */
	public void markPlayedUpTo(int index) {
		currentTrack = index;

		fireValueChangedEvent();
	}

	/**
	 * Moves track at given index to current track position.
	 * 
	 * @param index
	 */
	public void popUp(int index) {
		Track track = tracks.remove(index);

		if (index < currentTrack) {
			currentTrack--;
		}
		tracks.add(currentTrack, track);

		fireValueChangedEvent();
	}

	/**
	 * Simply adds given track to the end of the queue.
	 * 
	 * @param track
	 */
	public void append(Track track) {
		tracks.add(track);

		fireValueChangedEvent();
	}

	/**
	 * Replaces all the remaining tracks with given track (if specified, if not,
	 * only removes remaining tracks).
	 * 
	 * @param trackOrNull
	 */
	public void replaceRest(Track trackOrNull) {
		int start = currentTrack + 1;
		int end = count();

		if (start < end) {
			tracks.subList(start, end).clear();
		}

		if (trackOrNull != null) {
			tracks.add(trackOrNull);
		}

		fireValueChangedEvent();
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String toString() {
		return "PlaylistRuntime [count=" + count() + ", currentTrackIndex=" + currentTrackIndex() //
				+ ", played=" + played().stream().map((t) -> t.getTitle()).collect(Collectors.joining(",")) //
				+ ", current=" + current().getTitle() //
				+ ", toBePlayed=" + toBePlayed().stream().map((t) -> t.getTitle()).collect(Collectors.joining(",")) //
				+ "]";
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Synchronize this runtime to given playlist.
	 * 
	 * @param playlist
	 */
	public void updateTo(Playlist playlist) {
		this.currentTrack = playlist.getCurrentTrackIndex();

		List<Track> newTracks = playlist.getTracks().getTracks();

		// optimalisation (check only for change)
		if (newTracks.size() != this.tracks.size() || newTracks.equals(this.tracks)) {
			this.tracks.clear();
			tracks.addAll(newTracks);
		}

		fireValueChangedEvent();
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	public static PlaylistRuntime of(Tracklist tracklist) {
		List<Track> tracks = tracklist.getTracks();
		return new PlaylistRuntime(tracks);
	}

	public static PlaylistRuntime of(Playlist playlist) {
		List<Track> tracks = playlist.getTracks().getTracks();
		int currentTrack = playlist.getCurrentTrackIndex();

		PlaylistRuntime runtime = new PlaylistRuntime(tracks);
		runtime.markPlayedUpTo(currentTrack);

		return runtime;
	}

	/////////////////////////////////////////////////////////////////////////////////////////

}
