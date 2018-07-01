package cz.martlin.jmop.core.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cz.martlin.jmop.core.tracks.Track;

public class BasicPlaylist {

	private final Stack<Track> played;
	private Track current;
	private final Stack<Track> remaining;

	public BasicPlaylist() {
		this.played = new Stack<>();
		this.current = null;
		this.remaining = new Stack<>();
	}

	public BasicPlaylist(Track track) {
		this();
		this.remaining.add(track);
	}

	public BasicPlaylist(List<Track> tracks) {
		this();
		this.remaining.addAll(tracks);
	}

	protected Stack<Track> getPlayed() {
		return played;
	}

	protected Track getCurrent() {
		return current;
	}

	protected Stack<Track> getRemaining() {
		return remaining;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	// TODO assume at least one has to be in remaining?

	public boolean hasLastPlayed() {
		return !played.isEmpty();
	}

	public Track getLastPlayed() {
		return played.peek();
	}

	public boolean hasCurrentlyPlayed() {
		return current != null;
	}

	public Track getCurrentlyPlayed() {
		return current;
	}

	public boolean hasNextToPlay() {
		return !remaining.isEmpty();
	}

	public Track getNextToPlay() {
		return remaining.peek();
	}

	public List<Track> list() {
		List<Track> list = new ArrayList<>(played.size() + 1 + remaining.size());

		list.addAll(played);
		list.add(current);
		list.addAll(remaining);

		return list;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Track startToPlay() {
		Track track = remaining.pop();
		current = track;

		return current;
	}

	public Track stopPlaying() {
		remaining.push(current);
		current = null;

		return current;
	}

	public Track toNext() {
		Track next = remaining.pop();
		played.push(current);

		current = next;

		return next;
	}

	public Track toPrevious() {
		Track previous = played.pop();
		remaining.push(previous);

		current = previous;

		return previous;
	}

	public void append(Track track) {
		remaining.push(track);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "Playlist [" //
				+ "played=" + played.stream().map(Track::getTitle) //
				+ "current=" + (current != null ? current.getTitle() : null) + ", "//
				+ "remaining=" + remaining.stream().map(Track::getTitle) + ", "//
				+ "]";
	}

}
