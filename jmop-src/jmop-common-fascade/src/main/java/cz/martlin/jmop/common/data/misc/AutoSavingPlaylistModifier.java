package cz.martlin.jmop.common.data.misc;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;

/**
 * The playlist modifier, which automatically triggers the playlist changed in
 * the musicbase after each change.
 * 
 * @author martin
 *
 */
public class AutoSavingPlaylistModifier extends ExtendedPlaylistModifier {

	/**
	 * The musicbase.
	 */
	private final MusicbaseModyfiingEncapsulator modyfiing;

	/**
	 * Creates.
	 * 
	 * @param playlist
	 * @param modyfiing
	 */
	public AutoSavingPlaylistModifier(Playlist playlist, MusicbaseModyfiingEncapsulator modyfiing) {
		super(playlist);

		this.modyfiing = modyfiing;
	}

	@Override
	public void append(Track track) {
		super.append(track);
		reportChanged();
	}

	@Override
	public void insertBefore(Track track, TrackIndex index) {
		super.insertBefore(track, index);
		reportChanged();
	}

	@Override
	public void remove(TrackIndex index) {
		super.remove(index);
		reportChanged();
	}

	@Override
	public void removeAll(Track track) {
		super.removeAll(track);
		reportChanged();
	}

	@Override
	public void move(TrackIndex sourceIndex, TrackIndex targetIndex) {
		super.move(sourceIndex, targetIndex);
		reportChanged();
	}

	@Override
	public void moveToEnd(TrackIndex sourceIndex) {
		super.moveToEnd(sourceIndex);
		reportChanged();
	}

	@Override
	public void shuffle(int amount) {
		super.shuffle(amount);
		reportChanged();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Saves the playlist changes in the musicbase.
	 */
	private void reportChanged() {
		modyfiing.playlistUpdated(playlist);
	}

}
