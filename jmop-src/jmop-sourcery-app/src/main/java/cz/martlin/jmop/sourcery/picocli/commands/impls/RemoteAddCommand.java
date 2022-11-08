package cz.martlin.jmop.sourcery.picocli.commands.impls;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.sourcery.fascade.JMOPRemote;
import cz.martlin.jmop.sourcery.picocli.misc.Service;
import cz.martlin.jmop.sourcery.picocli.misc.SourceryPicocliUtilities;
import cz.martlin.jmop.sourcery.picocli.mixins.CreateOrAddToPlaylistGroup;
import cz.martlin.jmop.sourcery.picocli.mixins.CreateOrUseBundleGroup;
import cz.martlin.jmop.sourcery.remote.JMOPSourceryException;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "add", aliases = { "search-and-add" },//
	description = "Adds tracks into the musicbase by searching them via the specified music service.", //
	subcommands =  HelpCommand.class )
public class RemoteAddCommand implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteAddCommand.class);

	@Option(names = { "--service", "-s" }, required = true, //
		description = "Remote service name to use for the search and download. One of: ${COMPLETION-CANDIDATES}")
	private Service service;

	@ArgGroup(multiplicity = "1")
	private CreateOrUseBundleGroup bundleArgs;

	@Option(names = { "--download", "-D" }, required = false, //
		description = "Should the track files also get automatically downloaded or just added into the musicbase? "
		+ "Keep in mind the download can violate some copyright policies.")
	private boolean download = false;

	@ArgGroup(multiplicity = "0..*")
	private List<CreateOrAddToPlaylistGroup> playlistsArgs;

	@Parameters(descriptionKey = "QUERY", arity = "1..*", //
		description = "The actual search queries.")
	private List<String> queries;

	public RemoteAddCommand() {
		super();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public void run() {
		LOGGER.info("Searching for {} to download", queries);

		try {
			Bundle bundle = bundleArgs.getBundle();
			JMOPRemote remote = service.pickRemote();
			
			List<Track> tracks = remote.add(bundle, queries, download);
			
			LOGGER.info("Searched {} tracks", tracks.size());
			SourceryPicocliUtilities.playlistThem(bundle, tracks, playlistsArgs);
		} catch (JMOPSourceryException e) {
			LOGGER.error("The add command failed", e);
		}
	}



	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "RemoteAddCommand [service=" + service + ", bundleArgs=" + bundleArgs + ", download=" + download
				+ ", playlistsArgs=" + playlistsArgs + ", queries=" + queries + "]";
	}
}
