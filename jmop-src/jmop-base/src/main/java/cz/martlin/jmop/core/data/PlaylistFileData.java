package cz.martlin.jmop.core.data;

import cz.martlin.jmop.core.sources.SourceKind;

/**
 * The simple data structure quite simillar to {@link Playlist}, but contains
 * more raw data and also is not immutable.
 * 
 * @author martin
 * @see Playlist
 *
 */
public class PlaylistFileData {
	/**
	 * Name of bundle the playlist belongs to.
	 */
	private String bundleName;
	/**
	 * Name of playlist this represents.
	 */
	private String playlistName;
	/**
	 * Source kind.
	 */
	private SourceKind kind;
	/**
	 * List of tracks.
	 */
	private Tracklist tracklist;
	/**
	 * Index of current track.
	 */
	private int currentTrackIndex;
	/**
	 * Is locked?
	 */
	private boolean locked;

	public PlaylistFileData() {
	}

	public PlaylistFileData(String bundleName, String playlistName, SourceKind kind, Tracklist tracklist,
			int currentTrackIndex, boolean locked) {
		super();
		this.bundleName = bundleName;
		this.playlistName = playlistName;
		this.kind = kind;
		this.tracklist = tracklist;
		this.currentTrackIndex = currentTrackIndex;
		this.locked = locked;
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

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getCurrentTrackIndex() {
		return currentTrackIndex;
	}

	public void setCurrentTrackIndex(int currentTrackIndex) {
		this.currentTrackIndex = currentTrackIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundleName == null) ? 0 : bundleName.hashCode());
		result = prime * result + currentTrackIndex;
		result = prime * result + ((kind == null) ? 0 : kind.hashCode());
		result = prime * result + (locked ? 1231 : 1237);
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
		if (currentTrackIndex != other.currentTrackIndex)
			return false;
		if (kind != other.kind)
			return false;
		if (locked != other.locked)
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
		return "PlaylistFileData [bundleName=" + bundleName + ", playlistName=" + playlistName + ", kind=" + kind //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ ", tracklist=" + tracklist + ", currentTrackIndex=" + currentTrackIndex + ", locked=" + locked + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}

}
