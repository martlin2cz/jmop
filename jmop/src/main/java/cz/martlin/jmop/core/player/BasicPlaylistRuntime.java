package cz.martlin.jmop.core.player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import cz.martlin.jmop.core.data.Track;
@Deprecated
public class BasicPlaylistRuntime {

	private final Stack<Track> played;
	private Track current;
	private final Deque<Track> remaining;

	public BasicPlaylistRuntime() {
		this.played = new Stack<>();
		this.current = null;
		this.remaining = new LinkedList<>();
	}

	public BasicPlaylistRuntime(Track track) {
		this();
		this.remaining.add(track);
	}

	public BasicPlaylistRuntime(List<Track> tracks) {
		this();
		this.remaining.addAll(tracks);
	}

	protected BasicPlaylistRuntime(Track... tracks) {
		this();
		this.remaining.addAll(Arrays.asList(tracks));
	}

	protected Stack<Track> getPlayed() {
		return played;
	}

	protected Track getCurrent() {
		return current;
	}

	protected Queue<Track> getRemaining() {
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

	public synchronized List<Track> list() {
		List<Track> list = new ArrayList<>(played.size() + 1 + remaining.size());

		list.addAll(played);
		if (current != null) {
			list.add(current);
		}
		list.addAll(remaining);

		return list;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public synchronized Track startToPlay() {
		Track track = remaining.removeFirst();
		current = track;

		return current;
	}

	public synchronized Track stopPlaying() {
		remaining.addFirst(current);
		current = null;

		return current;
	}

	public synchronized Track toNext() {
		Track next = remaining.removeFirst();

		if (current != null) {
			played.push(current);
		}

		current = next;

		return next;
	}

	public synchronized Track toPrevious() {
		Track previous = played.pop();

		if (current != null) {
			remaining.addFirst(current);
		}

		current = previous;

		return previous;
	}

	public synchronized void append(Track track) {
		remaining.addLast(track);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	public  synchronized String toHumanString() {
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
