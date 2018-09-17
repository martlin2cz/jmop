package cz.martlin.jmop.core.playlist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.ObservableObject;

public class PlaylistRuntime extends ObservableObject<PlaylistRuntime> {
	private final Stack<Track> played;
	private final Deque<Track> remaining;

	public PlaylistRuntime() {
		this.played = new Stack<>();
		this.remaining = new LinkedList<>();
	}

	public PlaylistRuntime(List<Track> tracks) {
		this.played = new Stack<>();
		this.remaining = new LinkedList<>(tracks);
	}

	public Tracklist toTracklist() {
		List<Track> tracks = listAll();
		return new Tracklist(tracks);
	}

	public int currentTrackIndex() {
		return playedCount() + 1;
	}

	public List<Track> listAll() {
		final int size = played.size() + remaining.size();
		List<Track> all = new ArrayList<>(size);

		all.addAll(played);
		all.addAll(remaining);

		return Collections.unmodifiableList(all);
	}

	public Track get(int index) {
		return getOrRemove(index, false);
	}

	public int count() {
		return played.size() + remaining.size();
	}

	public int playedCount() {
		return played.size();
	}

	public int remainingCount() {
		return remaining.size();
	}

	public Track nextToBePlayed() {
		return remaining.peek();
	}

	public Track lastWasPlayed() {
		return played.peek();
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	public boolean hasNextToPlay() {
		return !remaining.isEmpty();
	}

	public boolean hasPlayed() {
		return !played.isEmpty();
	}

	public Track nextToPlay() {
		Track track = remaining.pop();
		played.push(track);

		fireValueChangedEvent();
		return track;
	}

	public Track lastPlayed() {
		Track track = played.pop();
		remaining.push(track);

		fireValueChangedEvent();
		return track;
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public void markPlayedUpTo(int index) {//TODO TESME !!!
		List<Track> all = listAll();
		List<Track> newPlayed = all.subList(0, index);
		List<Track> newRemaining = all.subList(index + 1, all.size() - 1);

		played.clear();
		played.addAll(newPlayed);

		remaining.clear();
		remaining.addAll(newRemaining);

		fireValueChangedEvent();
	}

	public void popUp(int index) {
		Track track = getOrRemove(index, true);
		remaining.addLast(track);

		fireValueChangedEvent();
	}

	public void append(Track track) {
		remaining.push(track);
	}

	public void replaceRest(Track track) {
		remaining.clear();
		remaining.push(track);

		fireValueChangedEvent();
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	private Track getOrRemove(int index, boolean remove) {
		if (index < played.size()) {
			return getOrRemove(played, index, remove);
		} else {
			return getOrRemove(remaining, index, remove);
		}
	}

	private static Track getOrRemove(Deque<Track> queue, int index, boolean remove) {
		Track track = get(queue, index);
		if (remove) {
			queue.remove(index);
		}
		return track;
	}

	private static Track getOrRemove(Stack<Track> stack, int index, boolean remove) {
		if (remove) {
			return stack.remove(index);
		} else {
			return stack.get(index);
		}
	}

	private static Track get(Deque<Track> queue, int index) {
		int i = 0;
		for (Track track : queue) {
			if (i == index) {
				return track;
			}

			i++;
		}
		return null;
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
}
