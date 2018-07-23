package cz.martlin.jmop.core.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.player.XXX_Playlist;
import cz.martlin.jmop.core.sources.SourceKind;

public class Bundle {
	private final SourceKind kind;
	private final String name;
	// TODO tracks map / full playlist
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

	public Track createTrack(String identifier, String title, String description) {
		Track track = new Track(this, identifier, title, description);
		this.tracks.put(identifier, track);
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

	@Deprecated
	public XXX_Playlist getFullPlaylist() {
		return new XXX_Playlist("TODO: full playlist", kind);
	}

	///////////////////////////////////////////////////////////////////////////

	private static Map<String, Track> toMap(Tracklist tracks) {
		return tracks.getTracks().stream() //
				.collect(Collectors.toMap( //
						(t) -> t.getIdentifier(), //
						(t) -> t));
	}

	@Override
	public String toString() {
		return "Bundle [name=" + name + "]";
	}

}
