package cz.martlin.jmop.common.data.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.HasMetadata;
import cz.martlin.jmop.common.data.misc.WithPlayedMarker;
import cz.martlin.jmop.core.misc.ObservableObject;
import cz.martlin.jmop.core.sources.SourceKind;
import javafx.util.Duration;

/**
 * The data structure representing bundle. Contains its name and metadata.
 * 
 * @author martin
 *
 */
public class Bundle extends ObservableObject<Bundle> implements Comparable<Bundle>, HasMetadata, WithPlayedMarker {
	@Deprecated
	private final SourceKind kind;
	
	private String name;
	private Metadata metadata;

	@Deprecated
	private final Map<String, Track> tracks;
	@Deprecated
	private final Map<String, Playlist> playlists;

	/**
	 * Creates the bundle.
	 * @param name
	 * @param metadata
	 */
	public Bundle(String name, Metadata metadata) {
		this.name = name;
		this.metadata = metadata;
		
		this.kind = null;
		this.tracks = null;
		this.playlists = null;
	}
	
	/**
	 * 
	 * @param kind
	 * @param name
	 * @deprecated use the {@link #Bundle(String, Metadata)}.
	 */
	@Deprecated
	public Bundle(SourceKind kind, String name) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = new LinkedHashMap<>();
		this.playlists = new LinkedHashMap<>();
	}

	/**
	 * 
	 * @param kind
	 * @param name
	 * @param tracks
	 * @deprecated use the {@link #Bundle(String, Metadata)}.
	 */
	@Deprecated
	public Bundle(SourceKind kind, String name, Tracklist tracks) {
		super();
		this.kind = kind;
		this.name = name;
		this.tracks = toMap(tracks);
		this.playlists = new LinkedHashMap<>();
	}

	/**
	 * 
	 * @param kind
	 * @param name
	 * @param metadata
	 * @deprecated use the {@link #Bundle(String, Metadata)}.
	 */
	@Deprecated
	public Bundle(SourceKind kind, String name, Metadata metadata) {
		this.kind = kind;
		this.name = name;
		this.metadata = metadata;
		this.tracks = new TreeMap<>(); // TODO tree map?
		this.playlists = new LinkedHashMap<>();
	}

	/**
	 * 
	 * @param kind
	 * @param name
	 * @param metadata
	 * @param tracks
	 * @deprecated use the {@link #Bundle(String, Metadata)}.
	 */
	@Deprecated
	public Bundle(SourceKind kind, String name, Metadata metadata, Tracklist tracks) {
		this.kind = kind;
		this.name = name;
		this.metadata = metadata;
		this.tracks = toMap(tracks);
		this.playlists = new LinkedHashMap<>();
	}

	@Deprecated
	public SourceKind getKind() {
		return kind;
	}

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}
	
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
	
	@Override
	public void played(Duration time) {
		metadata = metadata.played(time);
	}
	

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Finds track of given id within this bundle. Returns null if no such.
	 * 
	 * @param id
	 * @return
	 * @deprecated do not
	 */
	@Deprecated
	public Track getTrack(String id) {
		return tracks.get(id);
	}

	/**
	 * 
	 * @param name
	 * @return
	 * @deprecated do not
	 */
	@Deprecated
	public Playlist getPlaylist(String name) {
		return playlists.get(name);
	}

	/**
	 * Returns true if this bundle contains given track. In fact, track with same
	 * ID.
	 * 
	 * @param track
	 * @return
	 * @deprecated do not
	 */
	@Deprecated
	public boolean contains(Track track) {
		String id = track.getTitle();
		return tracks.containsKey(id);
	}

	/**
	 * Lists all tracks as a tracklist.
	 * 
	 * @return
	 * @deprecated do not
	 */
	@Deprecated
	public Tracklist tracks() {
		Collection<Track> col = tracks.values();
		List<Track> list = new ArrayList<>(col);
		return new Tracklist(list);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Converts given tracklist to map, where key is track title.
	 * 
	 * @param tracks
	 * @return
	 * @deprecated do not
	 */
	@Deprecated
	private static Map<String, Track> toMap(Tracklist tracks) {
		return tracks.getTracks().stream() //
				.collect(Collectors.toMap( //
						(t) -> t.getTitle(), //
						(t) -> t, //
						(t1, t2) -> t1));
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public int compareTo(Bundle another) {
		return this.name.compareToIgnoreCase(another.name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((playlists == null) ? 0 : playlists.hashCode());
		result = prime * result + ((tracks == null) ? 0 : tracks.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bundle other = (Bundle) obj;
		if (kind != other.kind)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (playlists == null) {
			if (other.playlists != null)
				return false;
		} else if (!playlists.equals(other.playlists))
			return false;
		if (tracks == null) {
			if (other.tracks != null)
				return false;
		} else if (!tracks.equals(other.tracks))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bundle [name=" + name + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}


}
