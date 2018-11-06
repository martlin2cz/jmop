package cz.martlin.jmop.core.sources;
/**
 * Kinds (or types) used for loading (downloading) of tracks. Currently only YouTube source is supported.
 * @author martin
 *
 */
public enum SourceKind {
	YOUTUBE("YouTube.com");

	/**
	 * Human readable name of the source.
	 */
	private final String name;

	private SourceKind(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
