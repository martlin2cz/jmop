package cz.martlin.jmop.core.sources.local;

import java.io.File;
import java.io.IOException;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.Playlist;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.tracks.Bundle;

public class TestLocalSource {

	public static void main(String[] args) throws IOException, JMOPSourceException {
		//create local
		final File rootDir = File.createTempFile("xxx", "yyy").getParentFile(); //hehe
		
		BaseFilesNamer namer = new DefaultFilesNamer();
		PlaylistLoader loader = null; //TODO
		AbstractFileSystemAccessor fileSystem = new DefaultFileSystemAccessor(rootDir, namer, loader);
		BaseLocalSource local = new DefaultLocalSource(fileSystem );
		
		//create some content ...
		
		Bundle fooBundle = new Bundle(SourceKind.YOUTUBE, "foo");
		local.createBundle(fooBundle );
		
		Playlist firstPlaylist = new Playlist("first", SourceKind.YOUTUBE);
		local.savePlaylist(fooBundle, firstPlaylist );
		
		Playlist secondPlaylist = new Playlist("second", SourceKind.YOUTUBE);
		local.savePlaylist(fooBundle, secondPlaylist );
		
		Bundle barBundle = new Bundle(SourceKind.YOUTUBE, "bar");
		local.createBundle(barBundle);
		
		Playlist thirdPlaylist = new Playlist("third", SourceKind.YOUTUBE);
		local.savePlaylist(barBundle, thirdPlaylist );
		
		// try to list it ...
		System.out.println("Bundles: " + local.listBundlesNames());
		
		System.out.println("Foo bundle: " + local.getBundle("foo"));
		System.out.println("Bar bundle: " + local.getBundle("bar"));
		
		System.out.println("Playlists of foo: " + local.listPlaylistNames(fooBundle));
		System.out.println("Playlists of bar: " + local.listPlaylistNames(fooBundle));
		
		System.out.println("First playlist of foo: " + local.getPlaylist(fooBundle, "second"));
		System.out.println("Third playlist of bar: " + local.getPlaylist(barBundle, "third"));
		
		
	}

}
