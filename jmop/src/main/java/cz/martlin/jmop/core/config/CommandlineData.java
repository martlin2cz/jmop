package cz.martlin.jmop.core.config;

import java.io.File;

public class CommandlineData {
	private boolean isHelp;
	private boolean isVersion;
	private File root;
	private String language;
	private String bundleToPlayName;
	private String playlistToPlayName;

	// TODO command line args ....
	public CommandlineData() {
	}

	public boolean isHelp() {
		return isHelp;
	}

	public void setHelp(boolean isHelp) {
		this.isHelp = isHelp;
	}

	public boolean isVersion() {
		return isVersion;
	}

	public void setVersion(boolean isVersion) {
		this.isVersion = isVersion;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String lang) {
		this.language = lang;
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

	public void setBundleToPlayName(String bundleToPlayName) {
		this.bundleToPlayName = bundleToPlayName;
	}

	public String getPlaylistToPlayName() {
		return playlistToPlayName;
	}

	public void setPlaylistToPlayName(String playlistToPlayName) {
		this.playlistToPlayName = playlistToPlayName;
	}

	// TODO setters
}
