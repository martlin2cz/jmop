package cz.martlin.jmop.common.data.misc;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

/**
 * The tool allowing manipulation with the playlist (not violating some of its
 * internals).
 * 
 * @author martin
 *
 */
public class PlaylistModifier {

	/**
	 * The playlist to manipulate.
	 */
	protected final Playlist playlist;

	/**
	 * Creates for the given playlist.
	 * 
	 * @param playlist
	 */
	public PlaylistModifier(Playlist playlist) {
		super();
		this.playlist = playlist;
	}

///////////////////////////////////////////////////////////////////////////

	/**
	 * Finds all the indexes of the given track.
	 * 
	 * @param track
	 * @return
	 */
	public List<TrackIndex> find(Track track) {
		return IntStream.range(0, playlist.getTracks().count()) //
				.mapToObj(i -> TrackIndex.ofIndex(i)) //
				.filter(ti -> playlist.getTracks().getTrack(ti).equals(track)) //
				.collect(Collectors.toList());
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Appends the track to the end of the playlist.
	 * 
	 * @param track
	 */
	public void append(Track track) {
		addTrack(track);
	}

	/**
	 * Inserts the track before the given index.
	 * 
	 * @param track
	 * @param index
	 */
	public void insertBefore(Track track, TrackIndex index) {
		checkIndex(index);
		insertTrack(track, index);
	}

	/**
	 * Removes the track on the given index.
	 * 
	 * @param index
	 */
	public void remove(TrackIndex index) {
		boolean removingCurrent = playlist.getCurrentTrackIndex().equal(index);

		removeTrack(index);

		if (removingCurrent) {
			incrementCurrentTrack();
		}
	}

	/**
	 * Removes all the occurences of the given track.
	 * 
	 * @param track
	 */
	public void removeAll(Track track) {
		iterate((t, i) -> {
			if (t.equals(track)) {
				i.remove();
			}
		});
	}

	/**
	 * Moves the track from the given position to the given position.
	 * 
	 * @param sourceIndex
	 * @param targetIndex
	 */
	public void move(TrackIndex sourceIndex, TrackIndex targetIndex) {
		boolean movingCurrent = playlist.getCurrentTrackIndex().equal(sourceIndex);

		Track track = removeTrack(sourceIndex);
		if (sourceIndex.smallerThan(targetIndex)) {
			targetIndex = targetIndex.decrement();
		}
		insertTrack(track, targetIndex);

		if (movingCurrent) {
			setCurrentTrack(targetIndex);
		}
	}

	/**
	 * Moves the track from the given position to the end.
	 * 
	 * @param sourceIndex
	 */
	public void moveToEnd(TrackIndex sourceIndex) {
		boolean movingCurrent = playlist.getCurrentTrackIndex().equal(sourceIndex);

		Track track = removeTrack(sourceIndex);
		addTrack(track);

		if (movingCurrent) {
			setCurrentTrackToEnd();
		}
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Inserts the track to given position.
	 * 
	 * @param track
	 * @param index
	 * @return
	 */
	private Track insertTrack(Track track, TrackIndex index) {
		int indx = index.getIndex();
		playlist.getTracks().getTracks().add(indx, track);

		if (index.smallerOrEqual(playlist.getCurrentTrackIndex())) {
			incrementCurrentTrack();
		}

		return track;
	}

	/**
	 * Adds the track to the end.
	 * 
	 * @param track
	 * @return
	 */
	private Track addTrack(Track track) {
		playlist.getTracks().getTracks().add(track);

		return track;
	}

	/**
	 * Removes the track.
	 * 
	 * @param index
	 * @return
	 */
	private Track removeTrack(TrackIndex index) {
		if (index.smallerOrEqual(playlist.getCurrentTrackIndex())) {
			decrementCurrentTrack();
		}

		int indx = index.getIndex();
		return playlist.getTracks().getTracks().remove(indx);
	}

	///////////////////////////////////////////////////////////////////////////

	private void incrementCurrentTrack() {
		TrackIndex currentTrackIndex = playlist.getCurrentTrackIndex();
		playlist.setCurrentTrackIndex(currentTrackIndex.increment());
	}

	private void decrementCurrentTrack() {
		TrackIndex currentTrackIndex = playlist.getCurrentTrackIndex();

		if (currentTrackIndex.biggerThan(TrackIndex.ofIndex(0))) {
			playlist.setCurrentTrackIndex(currentTrackIndex.decrement());
		}
	}

	private void setCurrentTrack(TrackIndex index) {
		playlist.setCurrentTrackIndex(index);
	}

	private void setCurrentTrackToEnd() {
		int end = playlist.getTracks().count();
		int endIndx = end - 1;

		TrackIndex index = TrackIndex.ofIndex(endIndx);
		playlist.setCurrentTrackIndex(index);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Makes sure the index is valid (non negative and not after the last index).
	 * 
	 * @param index
	 */
	private void checkIndex(TrackIndex index) {
		int indx = index.getIndex();
		if (indx < 0) {
			throw new IndexOutOfBoundsException("Track index " + index + " is illegal");
		}

		int count = playlist.getTracks().count();
		if (indx >= count) {
			throw new IndexOutOfBoundsException("Track index " + index + " is illegal, " //
					+ "playlist has only " + count + " tracks");
		}
	}

	/**
	 * Applies the given action to all tracks.
	 * 
	 * @param action
	 */
	private void iterate(BiConsumer<Track, Iterator<Track>> action) {
		Iterator<Track> iter = playlist.getTracks().getTracks().iterator();
		while (iter.hasNext()) {
			Track iTrack = iter.next();
			action.accept(iTrack, iter);
		}
	}

}
