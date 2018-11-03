package cz.martlin.jmop.core.sources.locals;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.config.DefaultConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.AbstractFileSystemAccessor;
import cz.martlin.jmop.core.sources.local.AbstractPlaylistLoader;
import cz.martlin.jmop.core.sources.local.BaseFilesNamer;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

public class TestLocalSource {

	public static void main(String[] args) throws IOException, JMOPSourceException {
		// create local
		final File rootDir = File.createTempFile("xxx", "yyy").getParentFile(); // hehe
		DefaultConfiguration config = new DefaultConfiguration();
		BaseFilesNamer namer = new DefaultFilesNamer();
		AbstractPlaylistLoader loader = new DefaultPlaylistLoader();
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(rootDir, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(config, fileSystem);
		// TestingLocalSource local = new TestingLocalSource();

		// create some content ...

		Bundle fooBundle = new Bundle(SourceKind.YOUTUBE, "foo");
		local.createBundle(fooBundle);

		Playlist firstPlaylist = new Playlist(fooBundle, "first", new Tracklist());
		local.savePlaylist(fooBundle, firstPlaylist);

		Playlist secondPlaylist = new Playlist(fooBundle, "second", new Tracklist());
		local.savePlaylist(fooBundle, secondPlaylist);

		Bundle barBundle = new Bundle(SourceKind.YOUTUBE, "bar");
		local.createBundle(barBundle);

		Playlist thirdPlaylist = new Playlist(barBundle, "third", new Tracklist());
		local.savePlaylist(barBundle, thirdPlaylist);

		// try to list it ...

		System.out.println("Bundles: " + local.listBundlesNames());

		System.out.println("Foo bundle: " + local.getBundle("foo"));
		System.out.println("Bar bundle: " + local.getBundle("bar"));

		System.out.println("Playlists of foo: " + local.listPlaylistNames(fooBundle));
		System.out.println("Playlists of bar: " + local.listPlaylistNames(fooBundle));

		System.out.println("First playlist of foo: " + local.getPlaylist(fooBundle, "second"));
		System.out.println("Third playlist of bar: " + local.getPlaylist(barBundle, "third"));

		// local.print(System.err);
	}

}
