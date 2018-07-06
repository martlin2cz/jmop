package cz.martlin.jmop.core.data;

import java.util.List;
import java.util.Stack;

public class Playlist {
	private final Bundle bundle;
	private final String name;
	private final Tracklist tracks;
	
	public Playlist(Bundle bundle, String name, Tracklist tracks) {
		super();
		this.bundle = bundle;
		this.name = name;
		this.tracks = tracks;
	}
	public Bundle getBundle() {
		return bundle;
	}
	public String getName() {
		return name;
	}
	public Tracklist getTracks() {
		return tracks;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (tracks == null) {
			if (other.tracks != null)
				return false;
		} else if (!tracks.equals(other.tracks))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Playlist [bundle=" + bundle + ", name=" + name + ", tracks=" + tracks + "]";
	}
	
	
}
