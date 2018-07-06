package cz.martlin.jmop.core.tracks;

import java.util.HashMap;
import java.util.Map;

import cz.martlin.jmop.core.player.Playlist;
import cz.martlin.jmop.core.sources.SourceKind;

public class Bundle {
	private final SourceKind kind;
	private final String name;
	//TODO tracks map / full playlist
	private final Map<TrackIdentifier, Track> tracks;

	public Bundle(SourceKind kind, String name) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = new HashMap<>();
	}

	public Bundle(SourceKind kind, String name, Playlist full) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = null;
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

	public Playlist getFullPlaylist() {
		return new Playlist("TODO: full playlist", kind);
	}

}
