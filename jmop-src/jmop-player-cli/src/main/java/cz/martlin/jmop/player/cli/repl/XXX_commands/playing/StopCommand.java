package cz.martlin.jmop.player.cli.repl.XXX_commands.playing;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.XXX_commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "stop")
public class StopCommand extends AbstractCommand {
	public StopCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun()  {
		fascade.stop();
	}

}
