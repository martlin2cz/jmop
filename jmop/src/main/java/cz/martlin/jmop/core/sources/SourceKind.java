package cz.martlin.jmop.core.sources;

public enum SourceKind {
	YOUTUBE("YouTube.com");

	private final String name;

	private SourceKind(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
