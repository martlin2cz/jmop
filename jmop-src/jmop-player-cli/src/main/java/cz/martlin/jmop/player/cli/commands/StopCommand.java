package cz.martlin.jmop.player.cli.commands;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "stop")
public class StopCommand extends AbstractCommand {
	public StopCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		fascade.stop();
	}

}
