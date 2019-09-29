package cz.martlin.jmop.core.sources;

import java.util.stream.Stream;

/**
 * Kinds (or types) used for loading (downloading) of tracks. Currently only
 * YouTube source is supported.
 * 
 * @author martin
 *
 */
public enum SourceKind {
	YOUTUBE("YouTube.com"); //$NON-NLS-1$

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

	public static SourceKind ofName(String name) {
		return Stream.of(values()) //
				.filter((k) -> k.name.equals(name)) //
				.findAny().get();
	}
}
