package cz.martlin.jmop.core.strategy.impls;

import java.util.List;
import java.util.Random;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlister.PlayerEngine;
import cz.martlin.jmop.core.runtime.PlaylistRuntime;
import cz.martlin.jmop.core.strategy.base.AbstractNextInferringStrategy;

/**
 * The playlister inferring next tracks by simply picking random tracks from the
 * whole bundle and appending as they were loaded online.
 * 
 * @author martin
 *
 */
public class InfiniteOfflineStrategy extends AbstractNextInferringStrategy {
	private final Random random;
	private Bundle bundle;

	public InfiniteOfflineStrategy() {
		this.random = new Random();
	}

	//////////////////////////////////////////////////////////////////////////////////////
	@Override
	public void startPlayingPlaylist(PlayerEngine engine, Playlist playlist, PlaylistRuntime runtime) {
		this.bundle = playlist.getBundle();

		super.startPlayingPlaylist(engine, playlist, runtime);
	}

	@Override
	public void stopPlayingPlaylist() {
		super.stopPlayingPlaylist();

		this.bundle = null;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	@Override
	protected void loadNext(Track track) {
		Track next = pickRandom();

		addTrack(next);
	}

	@Override
	public void addTrack(Track track) {
		PlaylistRuntime runtime = getRuntime();
		runtime.append(track);
	}
	//////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Returns random track from all the bundle tracks.
	 * 
	 * @return
	 */
	private Track pickRandom() {
		List<Track> allTracks = bundle.tracks().getTracks();
		int count = allTracks.size();

		int index = random.nextInt(count);

		return allTracks.get(index);
	}

}
