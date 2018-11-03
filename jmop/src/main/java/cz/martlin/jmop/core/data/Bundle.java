package cz.martlin.jmop.core.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.misc.ObservableObject;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

public class Bundle extends ObservableObject<Bundle> {
	private final SourceKind kind;
	private final String name;
	private final Map<String, Track> tracks;

	public Bundle(SourceKind kind, String name) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = new LinkedHashMap<>();
	}

	public Bundle(SourceKind kind, String name, Tracklist tracks) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = toMap(tracks);
	}

	public SourceKind getKind() {
		return kind;
	}

	public String getName() {
		return name;
	}

	///////////////////////////////////////////////////////////////////////////

	public Track createTrack(String identifier, String title, String description, Duration duration) {
		Track track = new Track(this, identifier, title, description, duration);
		this.tracks.put(identifier, track);

		fireValueChangedEvent();

		return track;
	}

	///////////////////////////////////////////////////////////////////////////

	public Track getTrack(String id) {
		return tracks.get(id);
	}

	public boolean contains(Track track) {
		String id = track.getIdentifier();
		return tracks.containsKey(id);
	}

	public Tracklist tracks() {
		Collection<Track> col = tracks.values();
		List<Track> list = new ArrayList<>(col);
		return new Tracklist(list);
	}

	///////////////////////////////////////////////////////////////////////////

	private static Map<String, Track> toMap(Tracklist tracks) {
		return tracks.getTracks().stream() //
				.collect(Collectors.toMap( //
						(t) -> t.getIdentifier(), //
						(t) -> t, //
						(t1, t2) -> t1));
	}

	@Override
	public String toString() {
		return "Bundle [name=" + name + "]";
	}

}
