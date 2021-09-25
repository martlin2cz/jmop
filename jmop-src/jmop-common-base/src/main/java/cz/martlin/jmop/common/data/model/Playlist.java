package cz.martlin.jmop.common.data.model;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.misc.WithPlayedMarker;
import cz.martlin.jmop.core.misc.ObservableObject;
import javafx.util.Duration;

/**
 * The data structure for playlist. Each playlist is associated with some
 * bundle, and has name, list of tracks and current track index.
 * Changes of instance of this class fires
 * invalidated events.
 * 
 * @author martin
 *
 */
public class Playlist extends ObservableObject<Playlist> implements Comparable<Playlist>, WithPlayedMarker {
	private Bundle bundle;

	private String name;
	private Tracklist tracks;
	private TrackIndex currentTrackIndex;
	@Deprecated
	private boolean locked;
	private Metadata metadata;

	public Playlist(Bundle bundle, String name, Metadata metadata) {
		super();
		this.bundle = bundle;
		this.name = name;
		this.tracks = new Tracklist();
		this.currentTrackIndex = TrackIndex.ofIndex(0);
		this.metadata = metadata;
	}
	
	public Playlist(Bundle bundle, String name, Tracklist tracks, TrackIndex currentTrackIndex, Metadata metadata) {
		super();
		this.bundle = bundle;
		this.name = name;
		this.tracks = tracks;
		this.currentTrackIndex = currentTrackIndex;
		this.metadata = metadata;
	}
	
	/**
	 * 
	 * @param bundle
	 * @param name
	 * @param tracks
	 * @param currentTrackIndex
	 * @param locked
	 * @deprecated use the {@link #Playlist(Bundle, String, Tracklist, int, Metadata))}
	 */
	@Deprecated
	public Playlist(Bundle bundle, String name, Tracklist tracks, int currentTrackIndex, boolean locked) {
		super();
		throw new UnsupportedOperationException("nope");
	}

	/**
	 * 
	 * @param bundle
	 * @param name
	 * @param tracks
	 * @deprecated use the {@link #Playlist(Bundle, String, Tracklist, int, Metadata))}
	 */
	@Deprecated
	public Playlist(Bundle bundle, String name, Tracklist tracks) {
		super();
		throw new UnsupportedOperationException("nope");
	}

	/**
	 * @deprecated use the {@link #Playlist(Bundle, String, Tracklist, int, Metadata))}
	 * @param bundle
	 * @param name
	 */
	@Deprecated
	public Playlist(Bundle bundle, String name) {
		super();
		throw new UnsupportedOperationException("nope");
	}

	/**
	 * 
	 * @param bundle
	 * @param name
	 * @param currentTrack
	 * @param locked
	 * @param metadata
	 * @param tracks
	 * @deprecated use the {@link #Playlist(Bundle, String, Tracklist, int, Metadata))}
	 */
	public Playlist(Bundle bundle, String name, int currentTrack, boolean locked, Metadata metadata, Tracklist tracks) {
		super();
		throw new UnsupportedOperationException("nope");
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public Bundle getBundle() {
		return bundle;
	}
	
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		fireValueChangedEvent();
	}

	public Tracklist getTracks() {
		return tracks;
	}
	
	/**
	 * use the {@link PlaylistModifier} instead
	 * @param track
	 */
	@Deprecated
	public void addTrack(Track track) {
		//TODO little tricky
		tracks.getTracks().add(track);
	}
	
	/**
	 * use the {@link PlaylistModifier} instead
	 * @param track
	 */
	@Deprecated
	public void removeTrack(Track track) {
		//TODO little tricky
		tracks.getTracks().remove(track);
	}

	/**
	 * use the {@link PlaylistModifier} when needed
	 * @param track
	 */
	@Deprecated
	public void setTracks(Tracklist tracks) {
		this.tracks = tracks;
		fireValueChangedEvent();
	}

	public TrackIndex getCurrentTrackIndex() {
		return currentTrackIndex;
	}

	public void setCurrentTrackIndex(TrackIndex currentTrackIndex) {
		this.currentTrackIndex = currentTrackIndex;
		fireValueChangedEvent();
	}

	@Deprecated
	public boolean isLocked() {
		return locked;
	}

	@Deprecated
	public void setLocked(boolean locked) {
		this.locked = locked;
		fireValueChangedEvent();
	}
	
	public Metadata getMetadata() {
		return metadata; 
	}

	/**
	 * Use {@link #played(Duration)}
	 * @param metadata
	 */
	@Deprecated
	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Playlist other = (Playlist) obj;
		if (bundle == null) {
			if (other.bundle != null)
				return false;
		} else if (!bundle.getName().equals(other.bundle.getName()))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Playlist other) {
		return this.name.compareToIgnoreCase(other.name);
	}

	@Override
	public String toString() {
		return "Playlist [bundle=" + bundle + ", name=" + name + ", tracks=" + tracks + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

	///////////////////////////////////////////////////////////////////////////////////////////////
	public String toHumanString() {
		return "Bundle " + bundle.getName() + ", playlist " + name + ":\n\n" + tracks.toHumanString(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	@Override
	public void played(Duration time) {
		metadata = metadata.played(time);
	}
	

}
