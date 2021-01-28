package cz.martlin.jmop.player.cli.repl;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.player.cli.repl.commands.JMOPReplRootCommand;
import cz.martlin.jmop.player.cli.repl.converter.BundleOrCurrentConverter;
import cz.martlin.jmop.player.cli.repl.converters.DurationConverter;
import cz.martlin.jmop.player.cli.repl.converters.TrackIndexConverter;
import cz.martlin.jmop.player.cli.repl.misc.JMOPComponentsFactory;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import javafx.util.Duration;
import picocli.CommandLine;
import picocli.CommandLine.IFactory;

/**
 * @author martin
 *
 */
public class JmopRepl extends AbstractRepl {

	private static final String PROMPT = "> ";

	private final JMOPPlayer jmop;

	public JmopRepl(JMOPPlayer fascade) {
		super();
		this.jmop = fascade;
	}

	@Override
	protected String createPropmt() {
		return PROMPT;
	}

	@Override
	protected IFactory createCommandsFactory() {
		return new JMOPComponentsFactory(jmop);
	}

	@Override
	protected Object createRootCommand() {
		return new JMOPReplRootCommand(jmop);
	}

	@Override
	protected void initializeCommandLine(CommandLine cmd) {
		cmd.registerConverter(Bundle.class, new BundleOrCurrentConverter(jmop));
//		cmd.registerConverter(Playlist.class, new PlaylistOrCurrentConverter(jmop));
//		cmd.registerConverter(Track.class, new TrackOrCurrentConverter(jmop));
		cmd.registerConverter(TrackIndex.class, new TrackIndexConverter(jmop));
		
		cmd.registerConverter(Duration.class, new DurationConverter());
		
		
	}

}
