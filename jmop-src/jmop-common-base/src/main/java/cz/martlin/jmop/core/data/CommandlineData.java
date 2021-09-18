package cz.martlin.jmop.core.data;

import java.io.File;

/**
 * The data structure for command line parameters. It's just simple POJO with
 * getters and setters.
 * 
 * @author martin
 *
 */
//TODO move to app module's data package?
public class CommandlineData {
	/**
	 * Was given -h or --help flag?
	 */
	private boolean isHelp;
	/**
	 * Was given -v or --version flag?
	 */
	private boolean isVersion;
	/**
	 * Value of -dir parameter.
	 */
	private File root;
	/**
	 * Value of -lang parameter.
	 */
	private String language;
	/**
	 * Value of first -play parameter.
	 */
	private String bundleToPlayName;
	/**
	 * Value of second -play parameter.
	 */
	private String playlistToPlayName;

	public CommandlineData() {
		super();
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

	@Override
	public String toString() {
		return "CommandlineData [isHelp=" + isHelp + ", isVersion=" + isVersion + ", root=" + root + ", language=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				+ language + ", bundleToPlayName=" + bundleToPlayName + ", playlistToPlayName=" + playlistToPlayName //$NON-NLS-1$ //$NON-NLS-2$
				+ "]"; //$NON-NLS-1$
	}

}
