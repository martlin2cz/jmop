package cz.martlin.jmop.player.cli.repl.XXX_commands.playing;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.XXX_commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import javafx.util.Duration;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "seek")
public class SeekCommand extends AbstractCommand {
	@Parameters(index = "0", arity = "1")
	private Duration duration;

	public SeekCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun()  {
		fascade.seek(duration);
	}

}
