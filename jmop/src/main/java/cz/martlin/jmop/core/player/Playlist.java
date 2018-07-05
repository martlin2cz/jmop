package cz.martlin.jmop.core.player;

public class Playlist {
	private final String name;
	//TODO tracklist instance?

	public Playlist(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Playlist [name=" + name + "]";
	}
	
	

}
