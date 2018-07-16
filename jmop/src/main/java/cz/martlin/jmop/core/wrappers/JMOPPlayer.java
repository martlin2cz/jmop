package cz.martlin.jmop.core.wrappers;

import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.JMOPPlaylister;
import cz.martlin.jmop.core.sources.AbstractRemoteSource;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.download.BaseSourceConverter;
import cz.martlin.jmop.core.sources.download.BaseSourceDownloader;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;

public class JMOPPlayer {
	private final JMOPSources sources;
	private final JMOPPlaying playing;
	private final GuiDescriptor gui;

	public JMOPPlayer(AbstractRemoteSource remote, BaseLocalSource local, BaseSourceDownloader downloader, BaseSourceConverter converter, GuiDescriptor gui, Playlist playlistToPlayOrNot, JMOPPlaylister playlister) {
		this.sources = new JMOPSources(local, remote, downloader, converter, gui);
		this.playing = new JMOPPlaying(playlister, playlistToPlayOrNot);
		this.gui = gui;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void startNewBundle(SourceKind kind, String bundleName, String querySeed) throws JMOPSourceException {
		Playlist playlist = sources.createNewBundleAndPrepare(kind, bundleName, querySeed);
		playing.startPlayingPlaylist(playlist);
	}

	public void startPlaylist(String bundleName, String playlistName) throws JMOPSourceException {
		Playlist playlist = sources.loadPlaylist(bundleName, playlistName);
		playing.startPlayingPlaylist(playlist);
	}

	public void savePlaylistAs(String newPlaylistName) throws JMOPSourceException {
		Playlist playlist = playing.getCurrentPlaylist();
		sources.savePlaylist(playlist, newPlaylistName);
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	public void startPlaying() {
		playing.startPlaying();
		//TODO sources -> check and load next
	}

	public void stopPlaying() {
		playing.stopPlaying();
	}

	public void pausePlaying() {
		playing.pausePlaying();
	}

	public void resumePlaying() {
		playing.resumePlaying();
	}

	public void toNext() {
		playing.toNext();
		//TODO sources -> check and load next
	}

	public void toPrevious() {
		playing.toPrevious();
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	public List<String> listBundles() throws JMOPSourceException {
		return sources.listAllBundles();
	}

	public List<String> listPlaylists(String bundleName) throws JMOPSourceException {
		return sources.listPlaylists(bundleName);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

}
