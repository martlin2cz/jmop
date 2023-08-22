package cz.martlin.jmop.common.data.model;

import cz.martlin.jmop.common.data.misc.HasMetadata;
import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.misc.WithPlayedMarker;
import cz.martlin.jmop.core.misc.ObservableObject;
import javafx.util.Duration;

/**
 * The data structure for playlist. Each playlist is associated with some
 * bundle, and has name, list of tracks and current track index. Changes of
 * instance of this class fires invalidated events.
 * 
 * @author martin
 *
 */
public class Playlist extends ObservableObject<Playlist>
		implements Comparable<Playlist>, HasMetadata, WithPlayedMarker {

	/**
	 * The owning bundle.
	 */
	private Bundle bundle;

	/**
	 * The playlist name.
	 */
	private String name;
	/**
	 * The list of tracks.
	 */
	private Tracklist tracks;
	/**
	 * The current track.
	 */
	private TrackIndex currentTrackIndex;

	/**
	 * The metadata.
	 */
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

	public TrackIndex getCurrentTrackIndex() {
		return currentTrackIndex;
	}

	public void setCurrentTrackIndex(TrackIndex currentTrackIndex) {
		this.currentTrackIndex = currentTrackIndex;
		fireValueChangedEvent();
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
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
		int nameCmp = this.name.compareToIgnoreCase(other.name);
		if (nameCmp != 0) {
			return nameCmp;
		}

		int bundleCmp = this.bundle.compareTo(other.bundle);
		return bundleCmp;
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

	public Duration getTotalDuration() {
		return tracks.getTracks().stream() //
				.reduce(Duration.ZERO, //
						(Duration d, Track t) -> d.add(t.getDuration()), //
						(Duration x, Duration y) -> x.add(y)); //
	}

}
