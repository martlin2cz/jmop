package cz.martlin.jmop.sourcery.picocli.commands.impls;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.sourcery.fascade.JMOPRemote;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import cz.martlin.jmop.sourcery.picocli.misc.Service;
import cz.martlin.jmop.sourcery.remote.JMOPSourceryException;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;

/**
 * The download track files command.
 * 
 * @author martin
 *
 */
@Command(name = "download", aliases = { "donwload-track-files", "d" }, //
	description = "Scans the whole musicbase, choosen bundle, playlist or just one single track, " //
	+ "and if its tracks doesn't have the track file, attepmts to download it from the provided service. " //
	+ "Keep in mind that downloading the tracks can violate the copyright policies or laws.", //
	subcommands =  HelpCommand.class )
public class ScanAndDownloadCommand implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ScanAndDownloadCommand.class);

	public ScanAndDownloadCommand() {
		super();
	}

	@Option(names = { "--service", "-s" }, required = true, //
		description = "The remote service to use for the download. One of: ${COMPLETION-CANDIDATES}")
	private Service service;

	@Option(names = { "--musicbase", "-m", "-mb" }, required = false, //
		description = "Check and download all the missing tracks in the whole musicbase.")
	private boolean wholeMusicbase;

	@Option(names = { "--bundle", "-b" }, required = false, //
		description = "Check and download all the missing tracks in the specified bundle.")
	private Bundle bundle;

	@Option(names = { "--playlist", "-p" }, required = false, //
		description = "Check and download all the missing tracks in the specified playlist.")
	private Playlist playlist;

	@Option(names = { "--track", "-t" }, required = false, //
		description = "Check and download provided track.")
	private Track track;

	@Override
	public void run() {
		Set<Track> tracks = listTracksToProcess();
		JMOPRemote youtube = pickRemote();

		for (Track track : tracks) {
			LOGGER.debug("Downloading track {}", track.getTitle());
			downloadAndSet(youtube, track);
		}

	}
	
	/**
	 * Lists tracks to progrees based on the playlist/bundle/or the single track.
	 * 
	 * @return
	 */
	private Set<Track> listTracksToProcess() {
		JMOPSourcery sourcery = JMOPSourceryProvider.get().getSourcery();
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

	/**
	 * Runs the download.
	 * 
	 * @param youtube
	 * @param track
	 */
	private void downloadAndSet(JMOPRemote youtube, Track track) {
		File file = track.getFile();

		try {
			if (file == null) {
				youtube.download(track);
			} else {
				youtube.downloadToFile(track);
			}
		} catch (JMOPSourceryException e) {
//			throw new JMOPRuntimeException("Cannot download track", e);
			LOGGER.error("Cannot download track {} (from {})", track.getTitle(), track.getSource());
		}
	}

	private JMOPRemote pickRemote() {
		return service.pickRemote();
	}

}
