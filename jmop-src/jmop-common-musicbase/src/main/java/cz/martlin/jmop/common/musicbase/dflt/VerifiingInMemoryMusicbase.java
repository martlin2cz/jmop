package cz.martlin.jmop.common.musicbase.dflt;

import java.io.InputStream;
import java.util.Set;
import java.util.function.Function;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;

public class VerifiingInMemoryMusicbase implements BaseInMemoryMusicbase {

	private static final boolean MUST_NOT_EXIST = false;
	private static final boolean MUST_EXIST = true;
	
	private final BaseInMemoryMusicbase delegee;
	
	
	public VerifiingInMemoryMusicbase(BaseInMemoryMusicbase delegee) {
		super();
		this.delegee = delegee;
	}

	public void addBundle(Bundle bundle) {
		mustNotExist(bundle, null);
		delegee.addBundle(bundle);
	}

	public Bundle createNewBundle(String name) {
		mustNotExist(null, name);
		return delegee.createNewBundle(name);
	}


	public void addPlaylist(Playlist playlist) {
		mustNotExist(playlist, null, null);
		delegee.addPlaylist(playlist);
	}


	public void addTrack(Track track) {
		mustNotExist(track, null, null);
		delegee.addTrack(track);
	}


	public void renameBundle(Bundle bundle, String newName) {
		mustExist(bundle, null);
		mustNotExist(bundle, newName);
		delegee.renameBundle(bundle, newName);
	}


	public Set<Bundle> bundles() {
		return delegee.bundles();
	}


	public void removeBundle(Bundle bundle) {
		mustExist(bundle, null);
		delegee.removeBundle(bundle);
	}


	public void bundleUpdated(Bundle bundle) {
		mustExist(bundle, null);
		delegee.bundleUpdated(bundle);
	}


	public Set<Playlist> playlists(Bundle bundle) {
		mustExist(bundle, null);
		return delegee.playlists(bundle);
	}


	public Playlist createNewPlaylist(Bundle bundle, String name) {
		mustNotExist((Playlist) null, bundle, name);
		return delegee.createNewPlaylist(bundle, name);
	}


	public Set<Track> tracks(Bundle bundle) {
		mustExist(bundle, null);
		return delegee.tracks(bundle);
	}


	public void renamePlaylist(Playlist playlist, String newName) {
		mustExist(playlist, null, playlist.getName());
		mustNotExist(playlist, null, newName);
		delegee.renamePlaylist(playlist, newName);
	}


	public void movePlaylist(Playlist playlist, Bundle newBundle) {
		mustExist(playlist, playlist.getBundle(), null);
		mustNotExist(playlist, newBundle, null);
		delegee.movePlaylist(playlist, newBundle);
	}


	public void removePlaylist(Playlist playlist) {
		mustExist(playlist, null, null);
		delegee.removePlaylist(playlist);
	}


	public void playlistUpdated(Playlist playlist) {
		mustExist(playlist, null, null);
		delegee.playlistUpdated(playlist);
	}


	public Track createNewTrack(Bundle bundle, TrackData data, InputStream trackFileContents) {
		mustNotExist((Track) null, bundle, data.getTitle());
		return delegee.createNewTrack(bundle, data, trackFileContents);
	}


	public void renameTrack(Track track, String newTitle) {
		mustExist(track, null, track.getTitle());
		mustNotExist(track, null, newTitle);
		delegee.renameTrack(track, newTitle);
	}


	public void moveTrack(Track track, Bundle newBundle) {
		mustExist(track, track.getBundle(), null);
		mustNotExist(track, newBundle, null);
		delegee.moveTrack(track, newBundle);
	}


	public void removeTrack(Track track) {
		mustExist(track, null, null);
		delegee.removeTrack(track);
	}


	public void trackUpdated(Track track) {
		mustExist(track, null, null);
		delegee.trackUpdated(track);
	}
	
	///////////////////////////////////////////////////////////////////////////


	private void mustNotExist(Bundle bundle, String otherName) {
		checkBundle(bundle, otherName, MUST_NOT_EXIST);
	}


	private void mustExist(Bundle bundle, String otherName) {
		checkBundle(bundle, otherName, MUST_EXIST);
	}

	private void checkBundle(Bundle bundle, String otherName, boolean requiredExistence) {
		String name = getOr(bundle, Bundle::getName, otherName);
		Set<Bundle> bundles = bundles();
		check(bundles, Bundle::getName, name, requiredExistence);
	}

	private void mustNotExist(Playlist playlist, Bundle otherBundle, String otherName) {
		checkPlaylist(playlist, otherBundle, otherName, MUST_NOT_EXIST);
	}
	private void mustExist(Playlist playlist, Bundle otherBundle, String otherName) {
		checkPlaylist(playlist, otherBundle, otherName, MUST_EXIST);
	}
	
	private void checkPlaylist(Playlist playlist, Bundle otherBundle, String otherName, boolean requiredExistence) {
		Bundle bundle = getOr(playlist, Playlist::getBundle, otherBundle);
		String name = getOr(playlist, Playlist::getName, otherName);
		
		Set<Playlist> playlists = playlists(bundle);
		check(playlists, Playlist::getName, name, requiredExistence);
	}

	private void mustNotExist(Track track, Bundle otherBundle, String otherTitle) {
		checkTrack(track, otherBundle, otherTitle, MUST_NOT_EXIST);
	}
	private void mustExist(Track track, Bundle otherBundle, String otherTitle) {
		checkTrack(track, otherBundle, otherTitle, MUST_EXIST);
	}
	
	private void checkTrack(Track track, Bundle otherBundle, String otherTitle, boolean requiredExistence) {
		Bundle bundle = getOr(track, Track::getBundle, otherBundle);
		String title = getOr(track, Track::getTitle, otherTitle);
		
		Set<Track> tracks = tracks(bundle);
		check(tracks, Track::getTitle, title, requiredExistence);
	}
	
///////////////////////////////////////////////////////////////////////////

	
	private <T, V> V getOr(T object, Function<T,V> valueObtainer, V otherValue) {
		if (otherValue == null && object == null) {
			throw new IllegalArgumentException("Specify the value and/or the object");
		}
			
		if (otherValue != null) {
			return otherValue;
		} else {
			return valueObtainer.apply(object);			
		}
	}

	private <T >void check(Set<T> objects, Function<T, String> nameObtainer, String name, boolean requiredExistence) {
		boolean hasAny = objects.stream() //
				.map(i -> nameObtainer.apply(i)) //
				.anyMatch(n -> n.equals(name));
		
		if (requiredExistence == MUST_NOT_EXIST) {
			if (hasAny) {
				throw new JMOPRuntimeException(name + " already exists");	
			}
		}
		if (requiredExistence == MUST_EXIST) {
			if (!hasAny) {
				throw new JMOPRuntimeException(name + " does not exist");	
			}
		}
	}


///////////////////////////////////////////////////////////////////////////

	
	
	@Override
	public String toString() {
		return "VerifiingInMemoryMusicbase [" + delegee + "]";
	}

}
