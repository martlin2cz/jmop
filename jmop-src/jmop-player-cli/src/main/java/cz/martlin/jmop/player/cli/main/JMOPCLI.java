package cz.martlin.jmop.player.cli.main;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.commands.InteractiveRootCommand;
import cz.martlin.jmop.player.cli.converters.BundleConverter;
import cz.martlin.jmop.player.cli.converters.DurationConverter;
import cz.martlin.jmop.player.cli.converters.PlaylistConverter;
import cz.martlin.jmop.player.cli.converters.TrackConverter;
import cz.martlin.jmop.player.cli.misc.InteractiveCommandsFactory;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.fascade.dflt.DefaultPlayerFascadeBuilder;
import cz.martlin.jmop.player.fascade.dflt.config.ConstantDefaultFascadeConfig;
import javafx.util.Duration;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

public class JMOPCLI {

	public static void main(String[] args) throws JMOPMusicbaseException {
		System.out.println("JMOPCLI.main()");

		CommandLine cl = prepareCL();

		//experimenting moved to Junit test
		cl.execute("bundles");
	}

	protected static CommandLine prepareCL() throws JMOPMusicbaseException {
		JMOPPlayerFascade fascade = DefaultPlayerFascadeBuilder.createTesting();
		JMOPPlayerAdapter adapter = fascade.adapter();
		BaseDefaultStorageConfig config = new ConstantDefaultFascadeConfig(); // TODO FIXME
		fascade.load();

		InteractiveRootCommand command = new InteractiveRootCommand(fascade);
		IFactory factory = new InteractiveCommandsFactory(fascade);

		CommandLine cl = new CommandLine(command, factory);

		cl.registerConverter(Bundle.class, new BundleConverter(adapter));
		cl.registerConverter(Playlist.class, new PlaylistConverter(adapter, config));
		cl.registerConverter(Track.class, new TrackConverter(adapter));
		cl.registerConverter(Duration.class, new DurationConverter());
		return cl;
	}

}
