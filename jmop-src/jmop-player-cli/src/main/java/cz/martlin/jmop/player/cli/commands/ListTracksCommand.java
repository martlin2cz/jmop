package cz.martlin.jmop.player.cli.commands;

import java.util.Set;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "tracks")
public class ListTracksCommand extends AbstractCommand {

	@Parameters(arity = "0..1")
	private Bundle bundle;
	
	public ListTracksCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Set<Track> tracks = fascade.tracks(bundle);
		
		if (bundle != null) {
		PrintUtil.print("Tracks in " + bundle.getName() + ":");
		} else {
			PrintUtil.print("All the tracks:");
		}
		for (Track track: tracks) {
			PrintUtil.printTrackTitle(track, bundle == null);
		}
		PrintUtil.emptyLine();
	}

}
