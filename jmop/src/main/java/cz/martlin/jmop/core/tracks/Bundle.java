package cz.martlin.jmop.core.tracks;

import java.util.HashMap;
import java.util.Map;

import cz.martlin.jmop.core.sources.SourceKind;

public class Bundle {
	private final SourceKind kind;
	private final String name;
	private final Map<TrackIdentifier, Track> tracks;

	public Bundle(SourceKind kind, String name) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = new HashMap<>();
	}

	public Bundle(SourceKind kind, String name, Map<TrackIdentifier, Track> tracks) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = tracks;
	}

	public SourceKind getKind() {
		return kind;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Bundle [name=" + name + "]";
	}

	public Track getTrack(TrackIdentifier id) {
		return tracks.get(id);
	}

	public boolean contains(Track track) {
		TrackIdentifier id = track.getIdentifier();
		return tracks.containsKey(id);
	}

}
