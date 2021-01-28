package cz.martlin.jmop.common.data.misc;

import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

public class PlaylistModifier {

	protected final Playlist playlist;

	public PlaylistModifier(Playlist playlist) {
		super();
		this.playlist = playlist;
	}

///////////////////////////////////////////////////////////////////////////

	public List<TrackIndex> find(Track track) {
		return IntStream.range(0, playlist.getTracks().count()) //
				.mapToObj(i -> TrackIndex.ofIndex(i)) //
				.filter(ti -> 
				playlist.getTracks().getTrack(ti).equals(track)
				) //
				.collect(Collectors.toList());
	}

	///////////////////////////////////////////////////////////////////////////
	public void append(Track track) {
		addTrack(track);
	}

	public void insertBefore(Track track, TrackIndex index) {
		checkIndex(index);
		insertTrack(track, index);
	}

	public void remove(TrackIndex index) {
		boolean removingCurrent = playlist.getCurrentTrackIndex().equal(index);

		removeTrack(index);

		if (removingCurrent) {
			incrementCurrentTrack();
		}
	}

	public void removeAll(Track track) {
		iterate((t, i) -> {
			if (t.equals(track)) {
				i.remove();
			}
		});
	}

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

	public void moveToEnd(TrackIndex sourceIndex) {
		boolean movingCurrent = playlist.getCurrentTrackIndex().equal(sourceIndex);

		Track track = removeTrack(sourceIndex);
		addTrack(track);

		if (movingCurrent) {
			setCurrentTrackToEnd();
		}
	}

	///////////////////////////////////////////////////////////////////////////

	private Track insertTrack(Track track, TrackIndex index) {
		int indx = index.getIndex();
		playlist.getTracks().getTracks().add(indx, track);

		if (index.smallerOrEqual(playlist.getCurrentTrackIndex())) {
			incrementCurrentTrack();
		}

		return track;
	}

	private Track addTrack(Track track) {
		playlist.getTracks().getTracks().add(track);

		return track;
	}

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

	private void iterate(BiConsumer<Track, Iterator<Track>> action) {
		Iterator<Track> iter = playlist.getTracks().getTracks().iterator();
		while (iter.hasNext()) {
			Track iTrack = iter.next();
			action.accept(iTrack, iter);
		}
	}

}
