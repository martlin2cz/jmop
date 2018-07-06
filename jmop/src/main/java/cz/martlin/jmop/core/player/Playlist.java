package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.sources.SourceKind;

public class Playlist {
	private final String name;
	private final SourceKind source;
	// TODO tracklist instance?

	public Playlist(String name, SourceKind source) {
		super();
		this.name = name;
		this.source = source;
	}

	public String getName() {
		return name;
	}

	public SourceKind getSource() {
		return source;
	}

	@Override
	public String toString() {
		return "Playlist [name=" + name + "]";
	}

}
