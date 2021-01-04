package cz.martlin.jmop.player.cli.repl;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.storages.dflt.BaseDefaultStorageConfig;
import cz.martlin.jmop.player.cli.repl.commands.InteractiveRootCommand;
import cz.martlin.jmop.player.cli.repl.converters.BundleConverter;
import cz.martlin.jmop.player.cli.repl.converters.DurationConverter;
import cz.martlin.jmop.player.cli.repl.converters.PlaylistConverter;
import cz.martlin.jmop.player.cli.repl.converters.TrackConverter;
import cz.martlin.jmop.player.cli.repl.misc.InteractiveCommandsFactory;
import cz.martlin.jmop.player.fascade.JMOPPlayerAdapter;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import javafx.util.Duration;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * @author martin
 *
 */
public class JmopRepl extends AbstractRepl {

	private static final String PROMPT = "> ";

	private final JMOPPlayerFascade fascade;

	public JmopRepl(JMOPPlayerFascade fascade) {
		super();
		this.fascade = fascade;
	}

	@Override
	protected String createPropmt() {
		return PROMPT;
	}

	@Override
	protected IFactory createCommandsFactory() {
		return new InteractiveCommandsFactory(fascade);
	}

	@Override
	protected Object createRootCommand() {
		return new InteractiveRootCommand(fascade);
	}

	@Override
	protected void initializeCommandLine(CommandLine cmd) {
		JMOPPlayerAdapter adapter = fascade.adapter();
		BaseDefaultStorageConfig config = fascade.config();

		cmd.registerConverter(Bundle.class, new BundleConverter(adapter));
		cmd.registerConverter(Playlist.class, new PlaylistConverter(adapter, config));
		cmd.registerConverter(Track.class, new TrackConverter(adapter));
		cmd.registerConverter(Duration.class, new DurationConverter());
	}

}
