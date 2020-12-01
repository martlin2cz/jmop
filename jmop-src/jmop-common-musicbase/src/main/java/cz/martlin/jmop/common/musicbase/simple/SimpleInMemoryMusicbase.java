package cz.martlin.jmop.common.musicbase.simple;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Metadata;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;

public class SimpleInMemoryMusicbase implements BaseMusicbase {

	private final List<Bundle> bundles;
	private final List<Playlist> playlists;
	private final List<Track> tracks;

	public SimpleInMemoryMusicbase() {
		this.bundles = new LinkedList<>();
		this.playlists = new LinkedList<>();
		this.tracks = new LinkedList<>();
	}

	@Override
	public List<String> bundlesNames() {
		return bundles.stream() //
				.map(Bundle::getName) //
				.collect(Collectors.toList()); //

	}

	@Override
	public Bundle getBundle(String name) {
		return bundles.stream() //
				.filter(b -> b.getName().equals(name)) //
				.findAny().get(); //
	}

	@Override
	public List<String> playlistsNames(Bundle bundle) {
		return playlists.stream() //
				.filter(p -> p.getBundle() == bundle) //
				.map(p -> p.getName()) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Playlist getPlaylist(Bundle bundle, String name) {
		return playlists.stream() //
				.filter(p -> p.getBundle() == bundle) //
				.filter(p -> p.getName() == name) //
				.findAny().get(); //
	}

	@Override
	public List<String> listTracksIDs(Bundle bundle) {
		return tracks.stream() //
				.filter(t -> t.getBundle() == bundle) //
				.map(t -> t.getIdentifier()) //
				.collect(Collectors.toList()); //
	}

	@Override
	public Track getTrack(Bundle bundle, String id) {
		return tracks.stream() //
				.filter(t -> t.getBundle() == bundle) //
				.filter(t -> t.getIdentifier() == id) //
				.findAny().get(); //
	}

	@Override
	public Bundle createBundle(String name) {
		Metadata metadata = Metadata.createNew();
		Bundle bundle = new Bundle(name, metadata);

		bundles.add(bundle);
		return bundle;
	}

	@Override
	public void renameBundle(Bundle bundle, String newName) {
		bundle.setName(newName);
	}

	@Override
	public void removeBundle(Bundle bundle) {
		bundles.remove(bundle);
	}

	@Override
	public void updateMetadata(Bundle bundle, Metadata newMetadata) {
		bundle.setMetadata(newMetadata);
	}

	@Override
	public Playlist createPlaylist(Bundle bundle, String name) {
		Metadata metadata = Metadata.createNew();
		Playlist playlist = new Playlist(bundle, name, metadata);

		playlists.add(playlist);
		return playlist;
	}

	@Override
	public void renamePlaylist(Playlist playlist, String newName) {
		playlist.setName(newName);

	}

	@Override
	public void movePlaylist(Playlist playlist, Bundle newBundle) {
		playlist.setBundle(newBundle);
	}

	@Override
	public void removePlaylist(Playlist playlist) {
		playlists.remove(playlist);
	}

	@Override
	public void updateMetadata(Playlist playlist, Metadata newMetadata) {
		playlist.setMetadata(newMetadata);
	}

	@Override
	public Track createTrack(Bundle bundle, TrackData data) {
		Metadata metadata = Metadata.createNew();
		Track track = new Track(bundle, //
				data.getIdentifier(), //
				data.getTitle(), //
				data.getDescription(), //
				data.getDuration(), //
				metadata); //

		tracks.add(track);
		return track;
	}

	@Override
	public void renameTrack(Track track, String newTitle) {
		track.setTitle(newTitle);
	}

	@Override
	public void moveTrack(Track track, Bundle newBundle) {
		track.setBundle(newBundle);
	}

	@Override
	public void removeTrack(Track track) {
		tracks.remove(track);
	}

	@Override
	public void updateMetadata(Track track, Metadata newMetadata) {
		track.setMetadata(newMetadata);
	}

}
