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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundleName == null) ? 0 : bundleName.hashCode());
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + ((playlistName == null) ? 0 : playlistName.hashCode());
		result = prime * result + ((tracklist == null) ? 0 : tracklist.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaylistFileData other = (PlaylistFileData) obj;
		if (bundleName == null) {
			if (other.bundleName != null)
				return false;
		} else if (!bundleName.equals(other.bundleName))
			return false;
		if (kind != other.kind)
			return false;
		if (playlistName == null) {
			if (other.playlistName != null)
				return false;
		} else if (!playlistName.equals(other.playlistName))
			return false;
		if (tracklist == null) {
			if (other.tracklist != null)
				return false;
		} else if (!tracklist.equals(other.tracklist))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PlaylistFileData [bundleName=" + bundleName + ", playlistName=" + playlistName + ", kind=" + kind
				+ ", tracklist=" + tracklist + "]";
	}

}
