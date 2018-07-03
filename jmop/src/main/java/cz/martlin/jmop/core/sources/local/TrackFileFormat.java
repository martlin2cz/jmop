package cz.martlin.jmop.core.sources.local;

public enum TrackFileFormat {
	MP3("mp3"), OPUS("opus");

	private final String extension;

	private TrackFileFormat(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}
}
