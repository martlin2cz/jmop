package cz.martlin.jmop.common.data.misc;

import java.util.Iterator;
import java.util.function.BiConsumer;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

public class PlaylistModifier {

	protected final Playlist playlist;

	public PlaylistModifier(Playlist playlist) {
		super();
		this.playlist = playlist;
	}

	///////////////////////////////////////////////////////////////////////////
	public void append(Track track) {
		addTrack(track);
	}

	public void insertBefore(Track track, int index) {
		checkIndex(index);
		insertTrack(track, index);
	}

	public void remove(int index) {
		boolean removingCurrent = playlist.getCurrentTrackIndex() == index;

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

	public void move(int sourceIndex, int targetIndex) {
		boolean movingCurrent = playlist.getCurrentTrackIndex() == sourceIndex;

		Track track = removeTrack(sourceIndex);
		if (sourceIndex < targetIndex) {
			targetIndex--;
		}
		insertTrack(track, targetIndex);

		if (movingCurrent) {
			setCurrentTrack(targetIndex);
		}
	}

	public void moveToEnd(int sourceIndex) {
		boolean movingCurrent = playlist.getCurrentTrackIndex() == sourceIndex;

		Track track = removeTrack(sourceIndex);
		addTrack(track);

		if (movingCurrent) {
			setCurrentTrackToEnd();
		}
	}

	///////////////////////////////////////////////////////////////////////////
	
	private Track insertTrack(Track track, int index) {
		playlist.getTracks().getTracks().add(index, track);
		if (index <= playlist.getCurrentTrackIndex()) {
			incrementCurrentTrack();
		}

		return track;
	}

	private Track addTrack(Track track) {
		playlist.getTracks().getTracks().add(track);

		return track;
	}

	private Track removeTrack(int index) {
		if (index <= playlist.getCurrentTrackIndex()) {
			decrementCurrentTrack();
		}

		return playlist.getTracks().getTracks().remove(index);
	}

	///////////////////////////////////////////////////////////////////////////

	private void incrementCurrentTrack() {
		int currentTrackIndex = playlist.getCurrentTrackIndex();
		playlist.setCurrentTrackIndex(currentTrackIndex + 1);
	}

	private void decrementCurrentTrack() {
		int currentTrackIndex = playlist.getCurrentTrackIndex();
		playlist.setCurrentTrackIndex(currentTrackIndex - 1);
	}

	private void setCurrentTrack(int index) {
		playlist.setCurrentTrackIndex(index);
	}

	private void setCurrentTrackToEnd() {
		int end = playlist.getTracks().count();
		playlist.setCurrentTrackIndex(end - 1);
	}

	///////////////////////////////////////////////////////////////////////////

	private void checkIndex(int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Track index " + index + " is illegal");
		}

		int count = playlist.getTracks().count();
		if (index >= count) {
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
