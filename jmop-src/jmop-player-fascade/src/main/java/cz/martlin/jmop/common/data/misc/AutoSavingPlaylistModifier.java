package cz.martlin.jmop.common.data.misc;

import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;

public class AutoSavingPlaylistModifier extends ExtendedPlaylistModifier {

	private final MusicbaseModyfiingEncapsulator modyfiing;

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
	public void insertBefore(Track track, int index) {
		super.insertBefore(track, index);
		reportChanged();
	}

	@Override
	public void remove(int index) {
		super.remove(index);
		reportChanged();
	}

	@Override
	public void removeAll(Track track) {
		super.removeAll(track);
		reportChanged();
	}

	@Override
	public void move(int sourceIndex, int targetIndex) {
		super.move(sourceIndex, targetIndex);
		reportChanged();
	}

	@Override
	public void moveToEnd(int sourceIndex) {
		super.moveToEnd(sourceIndex);
		reportChanged();
	}

	@Override
	public void shuffle(int amount) {
		super.shuffle(amount);
		reportChanged();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void reportChanged() {
		modyfiing.playlistUpdated(playlist);
	}

}
