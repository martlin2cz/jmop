package cz.martlin.jmop.core.player;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.sources.Sources;

public class Playlister {
	private final InternetConnectionStatus connection;
	private final Sources sources;
	private final BasicPlaylist playlist;

	public Playlister() {
		this.connection = new InternetConnectionStatus(); //TODO it should be shared
		this.sources = null;
		this.playlist = null;
		// TODO init
	}

	public void play() {
		//TODO this should be in fact quite same as #toNext but the toNext assumes current != null but this may work with current == null
	}

	public void stop() {
		// TODO just *somehow* make current := null
	}

	public void toNext() {
		boolean isOffline = connection.isOffline();

		// current := next

		if (isOffline) {
			//FIXME if has no next, do ... TODO (what to do? play random? how?) 
			playlist.toNext();
		} else {
			//TODO IMPLEMENTME
			// next := next by remote
			// (also ask for similar video, check existence, download if not
			// yet)
		}

	}

}
