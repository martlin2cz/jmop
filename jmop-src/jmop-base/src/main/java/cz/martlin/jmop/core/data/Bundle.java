package cz.martlin.jmop.core.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.misc.ObservableObject;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

/**
 * The data structure representing bundle. Contains its kind, name and also list
 * of all tracks. Changes of instance of this class fires invalidated events.
 * 
 * @author martin
 *
 */
public class Bundle extends ObservableObject<Bundle> implements Comparable<Bundle> {
	private final SourceKind kind;
	private final String name;
	private Metadata metadata;
	private final Map<String, Track> tracks;

	@Deprecated
	public Bundle(SourceKind kind, String name) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = new LinkedHashMap<>();
	}

	@Deprecated
	public Bundle(SourceKind kind, String name, Tracklist tracks) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = toMap(tracks);
	}

	public Bundle(SourceKind kind, String name, Metadata metadata) {
		this.kind = kind;
		this.name = name;
		this.metadata = metadata;
		this.tracks = new TreeMap<>(); //TODO tree map?
	}
	
	public Bundle(SourceKind kind, String name, Metadata metadata, Tracklist tracks) {
		this.kind = kind;
		this.name = name;
		this.metadata = metadata;
		this.tracks = toMap(tracks);
	}

	public SourceKind getKind() {
		return kind;
	}

	public String getName() {
		return name;
	}

	public Metadata getMetadata() {
		return metadata;
	}
	///////////////////////////////////////////////////////////////////////////

	/**
	 * Creates track within this bundle.
	 * 
	 * @param identifier
	 * @param title
	 * @param description
	 * @param duration
	 * @return
	 */
	public Track createTrack(String identifier, String title, String description, Duration duration, Metadata metadata) {
		Track track = new Track(this, identifier, title, description, duration, metadata);
		this.tracks.put(identifier, track);

		fireValueChangedEvent();

		return track;
	}

	public Playlist createPlaylist(String name, int currentTrack, boolean locked, Metadata metadata, Tracklist tracks) {
		return new Playlist(this, name, currentTrack, locked, metadata, tracks);
		//TODO fire event?
	}
	
	public Playlist createNewPlaylist(String name) {
		return new Playlist(this, name);
		//TODO fire event?
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Finds track of given id within this bundle. Returns null if no such.
	 * 
	 * @param id
	 * @return
	 */
	public Track getTrack(String id) {
		return tracks.get(id);
	}

	/**
	 * Returns true if this bundle contains given track. In fact, track with same
	 * ID.
	 * 
	 * @param track
	 * @return
	 */
	public boolean contains(Track track) {
		String id = track.getIdentifier();
		return tracks.containsKey(id);
	}

	/**
	 * Lists all tracks as a tracklist.
	 * 
	 * @return
	 */
	public Tracklist tracks() {
		Collection<Track> col = tracks.values();
		List<Track> list = new ArrayList<>(col);
		return new Tracklist(list);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Converts given tracklist to map, where key is track ID.
	 * 
	 * @param tracks
	 * @return
	 */
	private static Map<String, Track> toMap(Tracklist tracks) {
		return tracks.getTracks().stream() //
				.collect(Collectors.toMap( //
						(t) -> t.getIdentifier(), //
						(t) -> t, //
						(t1, t2) -> t1));
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public int compareTo(Bundle another) {
		return this.name.compareTo(another.name);
	}

	@Override
	public String toString() {
		return "Bundle [name=" + name + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
