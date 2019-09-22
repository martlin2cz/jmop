package cz.martlin.jmop.core.sources.local;

public abstract class AbstractLocalSource implements BaseLocalSource {

	private final BaseBundlesLocalSource bundles;
	private final BaseTracksLocalSource tracks;
	private final BasePlaylistsLocalSource playlists;

	public AbstractLocalSource() {
		super();
		
		this.bundles = createBundlesSource();
		this.tracks = createTracksSource();
		this.playlists = createPlaylistsSource();
	}

	protected abstract BaseTracksLocalSource createTracksSource();

	protected abstract BaseBundlesLocalSource createBundlesSource();

	protected abstract BasePlaylistsLocalSource createPlaylistsSource();

	@Override
	public BaseBundlesLocalSource bundles() {
		return bundles;
	}

	@Override
	public BaseTracksLocalSource tracks() {
		return tracks;
	}

	@Override
	public BasePlaylistsLocalSource playlists() {
		return playlists;
	}

}
