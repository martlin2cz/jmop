package cz.martlin.jmop.player.cli.repl.commands.playing;

import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "pause")
public class PauseCommand extends AbstractCommand {
	
	public PauseCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		fascade.pause();
	}

}
