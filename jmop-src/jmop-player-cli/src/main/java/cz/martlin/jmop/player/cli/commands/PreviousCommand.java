package cz.martlin.jmop.player.cli.commands;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;

@Command(name = "previous")
public class PreviousCommand extends AbstractCommand {

	public PreviousCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		fascade.toPrevious();
		
		Track track = fascade.currentTrack();
		PrintUtil.print("Playing", track);
	}

}
