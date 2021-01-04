package cz.martlin.jmop.player.cli.repl.commands.playing;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.commands.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "next")
public class NextCommand extends AbstractCommand {

	public NextCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		fascade.toNext();
		
		Track track = fascade.currentTrack();
		PrintUtil.print("Playing", track);
	}

}
