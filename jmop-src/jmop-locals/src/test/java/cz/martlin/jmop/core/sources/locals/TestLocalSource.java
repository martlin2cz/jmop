package cz.martlin.jmop.core.sources.locals;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.config.ConstantConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.XXX_BaseLocalSource;

public class TestLocalSource {

	public static void main(String[] args) throws IOException, JMOPSourceException {
		// create local
		final File rootDir = File.createTempFile("xxx", "yyy").getParentFile(); // hehe //$NON-NLS-1$ //$NON-NLS-2$
		ConstantConfiguration config = new ConstantConfiguration();
		BaseFilesNamer namer = new FunkyFilesNamer();
		AbstractPlaylistLoader loader = new DefaultPlaylistLoader();
		AbstractFileSystemAccessor fileSystem = new FunkyFileSystemAccessor(rootDir, namer, loader);
		XXX_BaseLocalSource local = new FunkyLocalSource(config, fileSystem);
		// TestingLocalSource local = new TestingLocalSource();

		// create some content ...

		Bundle fooBundle = new Bundle(SourceKind.YOUTUBE, "foo"); //$NON-NLS-1$
		local.createBundle(fooBundle);

		Playlist firstPlaylist = new Playlist(fooBundle, "first", new Tracklist()); //$NON-NLS-1$
		local.savePlaylist(fooBundle, firstPlaylist);

		Playlist secondPlaylist = new Playlist(fooBundle, "second", new Tracklist()); //$NON-NLS-1$
		local.savePlaylist(fooBundle, secondPlaylist);

		Bundle barBundle = new Bundle(SourceKind.YOUTUBE, "bar"); //$NON-NLS-1$
		local.createBundle(barBundle);

		Playlist thirdPlaylist = new Playlist(barBundle, "third", new Tracklist()); //$NON-NLS-1$
		local.savePlaylist(barBundle, thirdPlaylist);

		// try to list it ...

		System.out.println("Bundles: " + local.listBundlesNames()); //$NON-NLS-1$

		System.out.println("Foo bundle: " + local.getBundle("foo")); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("Bar bundle: " + local.getBundle("bar")); //$NON-NLS-1$ //$NON-NLS-2$

		System.out.println("Playlists of foo: " + local.listPlaylistNames(fooBundle)); //$NON-NLS-1$
		System.out.println("Playlists of bar: " + local.listPlaylistNames(fooBundle)); //$NON-NLS-1$

		System.out.println("First playlist of foo: " + local.getPlaylist(fooBundle, "second")); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("Third playlist of bar: " + local.getPlaylist(barBundle, "third")); //$NON-NLS-1$ //$NON-NLS-2$

		// local.print(System.err);
	}

}
