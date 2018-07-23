package cz.martlin.jmop.core.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import cz.martlin.jmop.core.data.Track;

public class BasicPlaylistRuntime {

	private final Stack<Track> played;
	private Track current;
	private final Stack<Track> remaining;

	public BasicPlaylistRuntime() {
		this.played = new Stack<>();
		this.current = null;
		this.remaining = new Stack<>();
	}

	public BasicPlaylistRuntime(Track track) {
		this();
		this.remaining.add(track);
	}

	public BasicPlaylistRuntime(List<Track> tracks) {
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
		if (current != null) {
			list.add(current);
		}
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

	public String toHumanString() {
		StringBuilder stb = new StringBuilder();

		played.forEach((t) -> stb.append("  " + t.getTitle() + "\n"));

		if (current != null) {
			stb.append("> " + current.getTitle() + "\n");
		} else {
			stb.append("> " + "---" + "\n");
		}

		remaining.forEach((t) -> stb.append("  " + t.getTitle() + "\n"));

		return stb.toString();
	}

	@Override
	public String toString() {
		return "Playlist [" //
				+ "played=" + played.stream().map(Track::getTitle) //
				+ "current=" + (current != null ? current.getTitle() : null) + ", "//
				+ "remaining=" + remaining.stream().map(Track::getTitle) + ", "//
				+ "]";
	}

}
