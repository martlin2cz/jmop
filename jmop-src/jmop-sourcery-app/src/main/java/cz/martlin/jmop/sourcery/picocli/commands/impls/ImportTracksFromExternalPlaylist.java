package cz.martlin.jmop.sourcery.picocli.commands.impls;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.sourcery.fascade.JMOPLocal;
import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import cz.martlin.jmop.sourcery.picocli.misc.SourceryPicocliUtilities;
import cz.martlin.jmop.sourcery.picocli.mixins.CreateOrAddToPlaylistGroup;
import cz.martlin.jmop.sourcery.picocli.mixins.CreateOrUseBundleGroup;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * The import playlist from external playlist file command. Calls
 * {@link JMOPLocal#importPlaylistFromPlaylist(File, Bundle, TrackFileCreationWay)}
 * 
 * @author martin
 *
 */
@Command(name = "from-playlist", aliases = { "from-file" }, //
		description = "Imports the tracks from the specified external playlist file.", //
		subcommands = HelpCommand.class)
public class ImportTracksFromExternalPlaylist implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteAddCommand.class);

	@ArgGroup(multiplicity = "1")
	private CreateOrUseBundleGroup bundleArgs;

	@Option(names = { "--create-file", "-f" }, //
			description = "Whether/How to create track file, one of: ${COMPLETION-CANDIDATES}")
	private TrackFileCreationWay createFiles = TrackFileCreationWay.JUST_SET;

	@ArgGroup(multiplicity = "0..*")
	private List<CreateOrAddToPlaylistGroup> playlistsArgs;

	@Parameters(arity = "1", //
			description = "The playlist file scan for the tracks.")
	private File file;

	@Override
	public void run() {
		LOGGER.info("Importing playlist {}", file);
		if (validateFileExistence(file)) {
			return;
		}

		Bundle bundle = bundleArgs.getBundle();
		JMOPLocal local = JMOPSourceryProvider.get().getSourcery().local();
		List<Track> tracks = local.importTracksFromPlaylist(file, bundle, createFiles);

		SourceryPicocliUtilities.playlistThem(bundle, tracks, playlistsArgs);
		LOGGER.info("Imported {} tracks", tracks.size());
	}

	private boolean validateFileExistence(File file) {
		if (!file.isFile()) {
			LOGGER.error("{} is not file or doesn't exist, skipping", file);
			return true;
		}

		return false;
	}
}
