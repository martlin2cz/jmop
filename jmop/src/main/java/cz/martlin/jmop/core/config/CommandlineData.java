package cz.martlin.jmop.core.config;

import java.io.File;

import cz.martlin.jmop.core.data.Playlist;

public class CommandlineData {
	private File root;
	private String bundleToPlayName;
	private String playlistToPlayName;

	// TODO command line args ....
	public CommandlineData() {
	}

	public File getRoot() {
		return root;
	}

	public void setRoot(File root) {
		this.root = root;
	}

	public String getBundleToPlayName() {
		return bundleToPlayName;
	}

	public String getPlaylistToPlayName() {
		return playlistToPlayName;
	}

	//TODO setters
}
