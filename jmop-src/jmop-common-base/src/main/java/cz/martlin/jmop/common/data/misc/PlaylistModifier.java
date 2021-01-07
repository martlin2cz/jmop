package cz.martlin.jmop.common.data.misc;

import java.util.Iterator;
import java.util.function.BiConsumer;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;

public class PlaylistModifier {

	private final Playlist playlist;

	public PlaylistModifier(Playlist playlist) {
		super();
		this.playlist = playlist;
	}

	///////////////////////////////////////////////////////////////////////////
	public void append(Track track) {
		addTrack(track);
	}

	public void insertBefore(Track track, int index) {
		insertTrack(track, index);
	}

	public void remove(int index) {
		removeTrack(index);
	}

	public void removeAll(Track track) {
		iterate((t, i) -> {
			if (t.equals(track)) {
				i.remove();
			}
		});
	}

	public void move(int sourceIndex, int targetIndex) {
		Track track = removeTrack(sourceIndex);
		insertTrack(track, targetIndex);
	}

	public void moveToEnd(int sourceIndex) {
		Track track = removeTrack(sourceIndex);
		addTrack(track);
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

	
	private void iterate(BiConsumer<Track, Iterator<Track>> action) {
		Iterator<Track> iter = playlist.getTracks().getTracks().iterator();
		while (iter.hasNext()) {
			Track iTrack = iter.next();
			action.accept(iTrack, iter);
		}
	}
}
