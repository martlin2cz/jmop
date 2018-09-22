package cz.martlin.jmop.core.playlister;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.playlist.PlaylistRuntime;
import cz.martlin.jmop.core.preparer.TrackPreparer;

public class TotallyOnlinePlaylister extends SimplePlaylister {

	private final TrackPreparer preparer;

	public TotallyOnlinePlaylister(TrackPreparer preparer) {
		super();
		this.preparer = preparer;
	}

	@Override
	public Track nextToPlay() {
		Track track = super.nextToPlay();

		preparer.startLoadingNextOfInBg(track, this);
		return track;
	}

	@Override
	public void addTrack(Track track) {
		PlaylistRuntime runtime = getRuntime();
		runtime.replaceRest(track);
	}

}
