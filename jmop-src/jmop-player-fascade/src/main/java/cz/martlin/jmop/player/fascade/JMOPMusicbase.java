package cz.martlin.jmop.player.fascade;

import java.util.Set;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseModyfiingEncapsulator;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class JMOPMusicbase {
	private final MusicbaseListingEncapsulator listing;
	private final MusicbaseModyfiingEncapsulator modyfiing;

	public JMOPMusicbase(BaseMusicbase musicbase) {
		super();

		this.listing = new MusicbaseListingEncapsulator(musicbase);
		this.modyfiing = new MusicbaseModyfiingEncapsulator(musicbase);
	}

/////////////////////////////////////////////////////////////////

	public Bundle bundleOfName(String bundleNameOrNot) throws JMOPMusicbaseException {
		return listing.getBundle(bundleNameOrNot);
	}

	public Playlist playlistOfName(Bundle bundleOrNull, String playlistNameOrNot) throws JMOPMusicbaseException {
		return listing.getPlaylist(bundleOrNull, playlistNameOrNot);
	}

	public Track trackOfTitle(Bundle bundleOrNull, String trackTitleOrNot) throws JMOPMusicbaseException {
		return listing.getTrack(bundleOrNull, trackTitleOrNot);
	}

/////////////////////////////////////////////////////////////////
	public Set<Bundle> bundles() throws JMOPMusicbaseException {
		return listing.bundles();
	}

	public Set<Playlist> playlists(Bundle bundleOrNull) throws JMOPMusicbaseException {
		return listing.playlists(bundleOrNull);
	}

	public Set<Track> tracks(Bundle bundleOrNull) throws JMOPMusicbaseException {
		return listing.tracks(bundleOrNull);
	}

/////////////////////////////////////////////////////////////////

	public Bundle createNewBundle(String name) throws JMOPMusicbaseException {
		return modyfiing.createNewBundle(name);
	}

	public void renameBundle(Bundle bundle, String newName) throws JMOPMusicbaseException {
		modyfiing.renameBundle(bundle, newName);
	}

	public void removeBundle(Bundle bundle) throws JMOPMusicbaseException {
		modyfiing.removeBundle(bundle);
	}

	public Playlist createNewPlaylist(Bundle bundle, String name) throws JMOPMusicbaseException {
		return modyfiing.createNewPlaylist(bundle, name);
	}

	public void renamePlaylist(Playlist playlist, String newName) throws JMOPMusicbaseException {
		modyfiing.renamePlaylist(playlist, newName);
	}

	public void movePlaylist(Playlist playlist, Bundle newBundle) throws JMOPMusicbaseException {
		// TODO what to do with track in it? move too, keep original or copy?
		modyfiing.movePlaylist(playlist, newBundle);
	}

	public void removePlaylist(Playlist playlist) throws JMOPMusicbaseException {
		modyfiing.removePlaylist(playlist);
	}

	public Track createNewTrack(Bundle bundle, TrackData data) throws JMOPMusicbaseException {
		return modyfiing.createNewTrack(bundle, data);
	}

	public void renameTrack(Track track, String newTitle) throws JMOPMusicbaseException {
		modyfiing.renameTrack(track, newTitle);
	}

	public void moveTrack(Track track, Bundle newBundle) throws JMOPMusicbaseException {
		// TODO remove from all playlists it contains?
		modyfiing.moveTrack(track, newBundle);
	}

	public void removeTrack(Track track) throws JMOPMusicbaseException {
		modyfiing.removeTrack(track);
	}

	public void updateTrack(Track track, TrackData newData) throws JMOPMusicbaseException {
		modyfiing.updateTrack(track, newData);
	}
}
