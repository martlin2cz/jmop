package cz.martlin.jmop.sourcery.picocli.commands;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.exceptions.JMOPRuntimeException;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;
import cz.martlin.jmop.sourcery.fascade.JMOPRemote;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.picocli.misc.Service;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "download")
public class ScanAndDownloadCommand implements Runnable {

	private final JMOPSourcery sourcery;

	public ScanAndDownloadCommand(JMOPSourcery sourcery) {
		super();
		this.sourcery = sourcery;
	}

	@Option(names = { "--service", "-s" }, required = true)
	private Service service;

	@Option(names = { "--musicbase", "-m", "-mb" }, required = false)
	private boolean wholeMusicbase;

	@Option(names = { "--bundle", "-b" }, required = false)
	private Bundle bundle;

	@Option(names = { "--playlist", "-p" }, required = false)
	private Playlist playlist;

	@Option(names = { "--track", "-t" }, required = false)
	private Track track;

	@Override
	public void run() {
		Set<Track> tracks = listTracksToProcess();
		JMOPRemote youtube = pickRemote();

		for (Track track : tracks) {
			downloadAndSet(youtube, track);
		}

	}

	private Set<Track> listTracksToProcess() {
		if (wholeMusicbase) {
			return sourcery.musicbase().tracks(null);
		}
		if (bundle != null) {
			return sourcery.musicbase().tracks(bundle);
		}
		if (playlist != null) {
			return new HashSet<>(playlist.getTracks().getTracks());
		}
		if (track != null) {
			return Collections.singleton(track);
		}

		return Collections.emptySet();
	}

	private void downloadAndSet(JMOPRemote youtube, Track track) {
		File file = track.getFile();

		try {
			if (file == null) {
				youtube.download(track);
			} else {
				youtube.downloadToFile(track);
			}
		} catch (JMOPSourceryException e) {
			throw new JMOPRuntimeException("Cannot download track", e);
		}
	}

	private JMOPRemote pickRemote() {
		// TODO check service
		return sourcery.youtube();
	}

}
