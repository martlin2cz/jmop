package cz.martlin.jmop.common.musicbase.simple;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import cz.martlin.jmop.common.data.Bundle;
import cz.martlin.jmop.common.data.Playlist;
import cz.martlin.jmop.common.data.Track;
import cz.martlin.jmop.common.data.TrackData;
import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.MusicbaseDebugPrinter;
import cz.martlin.jmop.core.misc.DurationUtilities;

public class SimpleInMemoryMusicbaseTest {

	@Test
	public void test() {
		BaseMusicbase musicbase = new SimpleInMemoryMusicbase();
		
		// bundles
		Bundle fooBundle = musicbase.createBundle("FooBundle");
		assertEquals(fooBundle, musicbase.getBundle("FooBundle"));
		
		Bundle barBundle = musicbase.createBundle("BarBundle");
		assertEquals(barBundle, musicbase.getBundle("BarBundle"));
		
		// playlists
		Playlist loremPlaylist = musicbase.createPlaylist(fooBundle, "lorem-playlist");
		assertEquals(loremPlaylist, musicbase.getPlaylist(fooBundle, "lorem-playlist"));
		
		Playlist ipsumPlaylist = musicbase.createPlaylist(barBundle, "ipsum-playlist");
		assertEquals(ipsumPlaylist, musicbase.getPlaylist(barBundle, "ipsum-playlist"));
		
		// tracks
		Track helloTrack = musicbase.createTrack(barBundle, td("hell1", "hello"));
		assertEquals(helloTrack, musicbase.getTrack(barBundle, "hell1"));
		
		Track holaTrack = musicbase.createTrack(fooBundle, td("ho2", "hola"));
		assertEquals(holaTrack, musicbase.getTrack(fooBundle, "ho2"));
		
		Track alohaTrack = musicbase.createTrack(fooBundle, td("al3", "aloha"));
		assertEquals(alohaTrack, musicbase.getTrack(fooBundle, "al3"));
		
		Track olaTrack = musicbase.createTrack(fooBundle, td("o04", "ola"));
		assertEquals(olaTrack, musicbase.getTrack(fooBundle, "o04"));
		
		// fill the playlists
		loremPlaylist.addTrack(holaTrack);
		loremPlaylist.addTrack(alohaTrack);
		loremPlaylist.addTrack(olaTrack);
		//FIXME: assertThat(loremPlaylist.getTracks().getTracks(), contains(holaTrack, alohaTrack, olaTrack));
		
		ipsumPlaylist.addTrack(helloTrack);
		//FIXME: assertThat(ipsumPlaylist.getTracks().getTracks(), contains(helloTrack));
		
		// print
		MusicbaseDebugPrinter.print(musicbase);
		
	}

	private TrackData td(String id, String title) {
		return new TrackData(id, title, "Desc", DurationUtilities.createDuration(0, 4, 11));
	}

}
