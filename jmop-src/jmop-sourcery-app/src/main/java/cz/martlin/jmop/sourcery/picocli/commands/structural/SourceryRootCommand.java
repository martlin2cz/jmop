package cz.martlin.jmop.sourcery.picocli.commands.structural;

import java.io.File;

import cz.martlin.jmop.sourcery.picocli.commands.impls.ImportPlaylistFromPlaylistFileCommand;
import cz.martlin.jmop.sourcery.picocli.commands.impls.ImportTracksFromDirectoryOrFileCommand;
import cz.martlin.jmop.sourcery.picocli.commands.impls.ImportTracksFromExternalPlaylist;
import cz.martlin.jmop.sourcery.picocli.commands.impls.RemoteAddCommand;
import cz.martlin.jmop.sourcery.picocli.commands.impls.ScanAndDownloadCommand;
import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;

/**
 * The sourcery root command. Its only responsibility is to read the root
 * directory and populate to the singleton JMOP sourcery.
 * 
 * @author martin
 *
 */
@Command(name = "sourcery", //
		description = "The sourcery, a tool for importing the music data (tracks, playlist and bundles) from either the computer location or via the remote services.", //
		subcommands = { //
				ImportTracksFromDirectoryOrFileCommand.class, //
				ImportTracksFromExternalPlaylist.class, //
				ImportPlaylistFromPlaylistFileCommand.class, //
				RemoteAddCommand.class, //
				ScanAndDownloadCommand.class, //
				HelpCommand.class //
		})
public class SourceryRootCommand {

	@Option(names = { "-mb", "--musicbase" }, required = true, //
			description = "The path to the musicbase storage directory.")
	private File root;

	@Option(names = { "-h", "--help" }, help = true, //
			description = "Shows the help about the supported sourcery actions.")
	private boolean help;

	public SourceryRootCommand() {
		super();

		JMOPSourceryProvider.get().setRoot(() -> root);
	}

}
