package cz.martlin.jmop.core.data;

import cz.martlin.jmop.core.misc.ObservableObject;
import cz.martlin.jmop.core.player.BetterPlaylistRuntime;

public class Playlist extends ObservableObject<Playlist> {
	private final Bundle bundle;
	private String name;
	@Deprecated
	private final BetterPlaylistRuntime runtime;

	public Playlist(Bundle bundle, String name, Tracklist tracks) {
		super();
		this.bundle = bundle;
		this.name = name;
		this.runtime = new BetterPlaylistRuntime(tracks.getTracks());
	}

	public Playlist(Bundle bundle, String name, BetterPlaylistRuntime runtime) {
		super();
		this.bundle = bundle;
		this.name = name;
		this.runtime = runtime;
	}

	public Playlist(Bundle bundle, String name) {
		super();
		this.bundle = bundle;
		this.name = name;
		this.runtime = new BetterPlaylistRuntime();
	}

	public Bundle getBundle() {
		return bundle;
	}

	public String getName() {
		return name;
	}

	public void changeName(String name) {
		this.name = name;
		fireValueChangedEvent();
	}
	
	@Deprecated
	public BetterPlaylistRuntime getRuntime() {
		return runtime;
	}

	public Tracklist getTracks() {
		return new Tracklist(runtime.list());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((runtime == null) ? 0 : runtime.hashCode());
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
		if (runtime == null) {
			if (other.runtime != null)
				return false;
		} else if (!runtime.equals(other.runtime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Playlist [bundle=" + bundle + ", name=" + name + ", tracks=" + runtime + "]";
	}

	public String toHumanString() {
		return "Bundle " + bundle.getName() + ", playlist " + name + ":\n\n" +  runtime.toHumanString();
	}

}
