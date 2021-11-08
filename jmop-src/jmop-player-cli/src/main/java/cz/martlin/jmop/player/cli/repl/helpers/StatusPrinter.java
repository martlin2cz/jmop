package cz.martlin.jmop.player.cli.repl.helpers;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

/**
 * Just simple printer of the current status.
 * 
 * @author martin
 *
 */
public class StatusPrinter {

	private final JMOPPlayer jmop;

	public StatusPrinter(JMOPPlayer jmop) {
		super();
		this.jmop = jmop;
	}

	public void printPlaying() {
		Bundle currentBundle = jmop.status().currentBundle();
		Playlist currentPlaylist = jmop.status().currentPlaylist();
		Track currentTrack = jmop.status().currentTrack();

		PrintUtil.print("Playing", currentPlaylist, "playlist from the", currentBundle, "bundle");

		if (currentTrack != null) {
			PrintUtil.print("Current track:", currentTrack);
		} else {
			PrintUtil.print("No current track.");
		}

		PlayerStatus currentStatus = jmop.status().currentStatus();
		PrintUtil.print("The player is " + currentStatus);

		if (currentTrack != null) {
			Duration currentTime = jmop.status().currentDuration();
			PrintUtil.print("Current time is", currentTime, "out of", currentTrack.getDuration());
		}
	}
}
