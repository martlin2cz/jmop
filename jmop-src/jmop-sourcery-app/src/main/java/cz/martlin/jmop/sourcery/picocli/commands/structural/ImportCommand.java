package cz.martlin.jmop.sourcery.picocli.commands.structural;

import cz.martlin.jmop.sourcery.picocli.commands.impls.ImportFromDirectoryOrFileCommand;
import cz.martlin.jmop.sourcery.picocli.commands.impls.ImportPlaylistFromPlaylistFileCommand;
import cz.martlin.jmop.sourcery.picocli.commands.impls.ImportTracksFromExternalPlaylist;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

/**
 * The command for importing from various local spots. Delegates to subcommands.
 * 
 * @author martin
 *
 */
@Command(name = "import", //
		description = "Imports the musicabse data form some external, non-musicbase location and storage", //
		subcommands = { //
				ImportCommand.ImportTracks.class, //
				ImportCommand.ImportPlaylist.class, //
				HelpCommand.class })
public class ImportCommand {

	/**
	 * The command for importing TRACKS from the local spots. Delegates to
	 * subcommands.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "tracks", //
			description = "Imports ", //
			subcommands = { //
					ImportFromDirectoryOrFileCommand.class, //
					ImportTracksFromExternalPlaylist.class, //
					HelpCommand.class })
	public static class ImportTracks {

	}

	/**
	 * The command for importing PLAYIST(s) from the local spots. Delegates to
	 * subcommands.
	 * 
	 * @author martin
	 *
	 */
	@Command(name = "playlist", //
			description = "Imports ", //
			subcommands = { //
					ImportPlaylistFromPlaylistFileCommand.class, //
					HelpCommand.class })

	public static class ImportPlaylist {

	}
}
