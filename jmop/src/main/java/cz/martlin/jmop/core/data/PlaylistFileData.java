package cz.martlin.jmop.core.data;

import cz.martlin.jmop.core.sources.SourceKind;

public class PlaylistFileData {

	private String bundleName;
	private String playlistName;
	private SourceKind kind;
	private Tracklist tracklist;

	public PlaylistFileData() {
	}

	public PlaylistFileData(String bundleName, String playlistName, SourceKind kind, Tracklist tracklist) {
		this.bundleName = bundleName;
		this.playlistName = playlistName;
		this.kind = kind;
		this.tracklist = tracklist;
	}

	public String getBundleName() {
		return bundleName;
	}

	public void setBundleName(String bundleName) {
		this.bundleName = bundleName;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public SourceKind getKind() {
		return kind;
	}

	public void setKind(SourceKind kind) {
		this.kind = kind;
	}

	public Tracklist getTracklist() {
		return tracklist;
	}

	public void setTracklist(Tracklist tracklist) {
		this.tracklist = tracklist;
	}

	@Override
	public String toString() {
		return "PlaylistFileData [bundleName=" + bundleName + ", playlistName=" + playlistName + ", kind=" + kind
				+ ", tracklist=" + tracklist + "]";
	}

}
