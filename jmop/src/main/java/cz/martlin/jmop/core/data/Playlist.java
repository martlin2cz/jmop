package cz.martlin.jmop.core.data;

import cz.martlin.jmop.core.misc.ObservableObject;

public class Playlist extends ObservableObject<Playlist> {
	private final Bundle bundle;
	private String name;
	private Tracklist tracks;
	private int currentTrackIndex;

	public Playlist(Bundle bundle, String name, Tracklist tracks) {
		super();
		this.bundle = bundle;
		this.name = name;
		this.tracks = tracks;
	}

	public Playlist(Bundle bundle, String name) {
		super();
		this.bundle = bundle;
		this.name = name;
		this.tracks = new Tracklist();
	}

	public Bundle getBundle() {
		return bundle;
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

	public void setTracks(Tracklist tracks) {
		this.tracks = tracks;
		fireValueChangedEvent();
	}

	public int getCurrentTrackIndex() {
		return currentTrackIndex;
	}

	public void setCurrentTrackIndex(int currentTrackIndex) {
		this.currentTrackIndex = currentTrackIndex;
		fireValueChangedEvent();
	}

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
		} else if (!bundle.equals(other.bundle))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Playlist [bundle=" + bundle + ", name=" + name + ", tracks=" + tracks + "]";
	}

	public String toHumanString() {
		return "Bundle " + bundle.getName() + ", playlist " + name + ":\n\n" + tracks.toHumanString();
	}

}
