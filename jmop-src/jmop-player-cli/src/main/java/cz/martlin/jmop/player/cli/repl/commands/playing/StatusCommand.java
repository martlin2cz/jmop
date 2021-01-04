package cz.martlin.jmop.player.cli.repl.commands.playing;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.commands.AbstractCommand;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;
import picocli.CommandLine.Command;

@Command(name = "status")
public class StatusCommand extends AbstractCommand {

	public StatusCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun() throws JMOPMusicbaseException {
		Bundle currentBundle = fascade.currentBundle();
		Playlist currentPlaylist = fascade.currentPlaylist();
		Track currentTrack = fascade.currentTrack();
		
		if (currentBundle == null) {
			PrintUtil.print("Nothing beeing played");
			return;
		} else {
			PrintUtil.print("Playing", currentPlaylist, "playlist from the", currentBundle, "bundle");
			
			if (currentTrack != null) {
				PrintUtil.print("Current track:", currentTrack.getTitle());	
			} else {
				PrintUtil.print("No current track.");
			}
			
			PlayerStatus currentStatus = fascade.currentStatus();
			PrintUtil.print("The player is " + currentStatus);
			
			if (currentTrack != null) {
				Duration currentTime = fascade.currentDuration();
				PrintUtil.print("Current time is", currentTime, "out of", currentTrack.getDuration());
			}
		}
	}
}
