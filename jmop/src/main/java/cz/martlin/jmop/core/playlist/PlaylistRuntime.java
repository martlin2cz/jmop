package cz.martlin.jmop.core.playlist;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.ObservableObject;

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

	public int count() {
		return tracks.size();
	}

	public int currentTrackIndex() {
		return currentTrack;
	}

	public int playedCount() {
		return currentTrack;
	}

	public int remainingCount() {
		return tracks.size() - currentTrack - 1;
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	public Track get(int index) {
		return tracks.get(index);
	}

	public Track nextToBePlayed() {
		return tracks.get(currentTrack + 1);
	}

	public Track current() {
		return tracks.get(currentTrack);
	}

	public Track lastWasPlayed() {
		return tracks.get(currentTrack - 1);
	}

	public Tracklist toTracklist() {
		return new Tracklist(tracks);
	}

	public List<Track> played() {
		return tracks.subList(0, currentTrack);
	}

	public List<Track> toBePlayed() {
		return tracks.subList(currentTrack + 1, count());
	}

	public List<Track> listAll() {
		return Collections.unmodifiableList(tracks);
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	public boolean hasNextToPlay() {
		return remainingCount() > 0;
	}

	public boolean hasPlayed() {
		return playedCount() > 0;
	}

	public Track toNext() {
		currentTrack++;
		fireValueChangedEvent();

		return current();
	}

	public Track toPrevious() {
		currentTrack--;
		fireValueChangedEvent();

		return current();
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public void markPlayedUpTo(int index) {
		currentTrack = index;

		fireValueChangedEvent();
	}

	public void popUp(int index) {
		Track track = tracks.remove(index);

		if (index < currentTrack) {
			currentTrack--;
		}
		tracks.add(currentTrack, track);

		fireValueChangedEvent();
	}

	public void append(Track track) {
		tracks.add(track);

		fireValueChangedEvent();
	}

	public void replaceRest(Track track) {
		int start = currentTrack + 1;
		int end = count();
		
		if (start < end) {
			tracks.subList(start, end).clear();
		}

		tracks.add(track);

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

	public static PlaylistRuntime of(Tracklist tracklist) {
		List<Track> tracks = tracklist.getTracks();
		return new PlaylistRuntime(tracks);
	}

	public static PlaylistRuntime of(Playlist playlist) {
		List<Track> tracks = playlist.getTracks().getTracks();
		return new PlaylistRuntime(tracks);
	}

	/////////////////////////////////////////////////////////////////////////////////////////

}
