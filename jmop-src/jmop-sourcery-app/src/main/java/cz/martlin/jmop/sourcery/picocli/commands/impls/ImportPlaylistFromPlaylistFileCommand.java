package cz.martlin.jmop.sourcery.picocli.commands.impls;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.musicbase.TrackFileCreationWay;
import cz.martlin.jmop.sourcery.fascade.JMOPLocal;
import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import cz.martlin.jmop.sourcery.picocli.mixins.CreateOrUseBundleGroup;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

/**
 * Command executing
 * {@link JMOPLocal#importFromPlaylist(List, Bundle, boolean, TrackFileCreationWay)}.
 * 
 * @author martin
 *
 */
@Command(name = "import-playlist-from-file", aliases = { "ipfp", "ipff" }, //
		description = "Imports the playlist from the specified playlist file into the musicbase bundle.", //
		subcommands = HelpCommand.class)
public class ImportPlaylistFromPlaylistFileCommand implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteAddCommand.class);

	@ArgGroup(multiplicity = "1")
	private CreateOrUseBundleGroup bundleArgs;

	@Option(names = { "--create-file", "-f" }, //
			description = "Whether/How to create track file, one of: ${COMPLETION-CANDIDATES}")
	private TrackFileCreationWay createFiles = TrackFileCreationWay.JUST_SET;


	@Parameters(arity = "1", //
			description = "The playlist file scan for the tracks.")
	private File file;

	@Override
	public void run() {
		LOGGER.info("Importing tracks from {}", file);
		if (validateFileExistence(file)) {
			return;
		}

		Bundle bundle = bundleArgs.getBundle();
		JMOPLocal local = JMOPSourceryProvider.get().getSourcery().local();
		Playlist playlist = local.importPlaylistFromPlaylist(file, bundle, createFiles);

		LOGGER.info("Imported playlist {} with {} tracks", playlist.getName(), playlist.getTracks().count());
	}

	private boolean validateFileExistence(File file) {
		if (!file.isFile()) {
			LOGGER.error("{} is not file or doesn't exist, skipping", file);
			return true;
		}

		return false;
	}

}
