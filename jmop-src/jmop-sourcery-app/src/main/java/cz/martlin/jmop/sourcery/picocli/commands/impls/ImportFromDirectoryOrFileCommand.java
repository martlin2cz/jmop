package cz.martlin.jmop.sourcery.picocli.commands.impls;

import java.io.File;
import java.util.Iterator;
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
 * Command executing {@link JMOPLocal#importTracksFromDirsOrFiles(List, Bundle, boolean, TrackFileCreationWay)}.
 * @author martin
 *
 */
@Command(name = "from-dir-or-files", aliases = { "from-dir", "from-files" }, //
	description = "Imports the tracks from the specified files and foldres into the musicbase bundle.", //
	subcommands =  HelpCommand.class )
public class ImportFromDirectoryOrFileCommand implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteAddCommand.class);

	@ArgGroup(multiplicity = "1")
	private CreateOrUseBundleGroup bundleArgs;
	
	@Option(names = { "--recursive", "-r" }, required = false, //
		description = "If directory, walk recursivelly the subtree?")
	private boolean recursive = false;

	@Option(names = { "--create-file", "-f" }, //
		description = "Whether/How to create track file, one of: ${COMPLETION-CANDIDATES}")
	private TrackFileCreationWay createFiles = TrackFileCreationWay.JUST_SET;
	

	@ArgGroup(multiplicity = "0..*")
	private List<CreateOrAddToPlaylistGroup> playlistsArgs;

	@Parameters(arity = "1..*", //
		description = "The files or directories to scan for the tracks.")
	private List<File> dirsOrFiles;


	@Override
	public void run() {
		LOGGER.info("Importing tracks from {}", dirsOrFiles);
		validateExistence(dirsOrFiles);

		Bundle bundle = bundleArgs.getBundle();
		JMOPLocal local = JMOPSourceryProvider.get().getSourcery().local();
		List<Track> tracks = local.importTracksFromDirsOrFiles(dirsOrFiles, bundle, recursive, createFiles);

		SourceryPicocliUtilities.playlistThem(bundle, tracks, playlistsArgs);
		LOGGER.info("Imported tracks {}", tracks.size());
	}

	private void validateExistence(List<File> dirsOrFiles) {
		for (Iterator<File> iterator = dirsOrFiles.iterator(); iterator.hasNext();) {
			File dir = iterator.next();
			
			if (!(dir.isFile() || dir.isDirectory())) {
				LOGGER.warn("{} is not file or directory or doesn't exist, skipping", dir);
				iterator.remove();
			}
		}
	}

}
