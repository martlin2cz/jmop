package cz.martlin.jmop.player.cli.repl.helpers;

import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.misc.PrintUtil;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.fascade.JMOPStatus;
import cz.martlin.jmop.player.players.PlayerStatus;
import javafx.util.Duration;

/**
 * The printer of the player bar/slider.
 * 
 * @author martin
 *
 */
public class BarPrinter {

	private static final int BAR_STEPS = 80;

	private final JMOPPlayer jmop;

	public BarPrinter(JMOPPlayer jmop) {
		super();
		this.jmop = jmop;
	}

	/**
	 * Prints the "bar" based on the current jmop status. Requires something to be
	 * played.
	 */
	public void printBar() {
		Track currentTrack = jmop.status().currentTrack();

		if (currentTrack != null) {
			Duration currentTime = jmop.status().currentDuration();
			Duration trackDuration = currentTrack.getDuration();
			PlayerStatus status = jmop.status().currentStatus();

			PrintUtil.print(currentTrack);
			printBar(currentTime, trackDuration, status);
			PrintUtil.print(currentTime, "/", trackDuration);
		} else {
			PrintUtil.print("---");
		}
	}

	/**
	 * Prints the actual bar.
	 * 
	 * @param currentTime
	 * @param trackDuration
	 * @param status
	 */
	private void printBar(Duration currentTime, Duration trackDuration, PlayerStatus status) {
		String button = chooseStatusButton(jmop.status());

		double currentMilis = currentTime != null ? currentTime.toMillis() : 0;
		double trackMilis = trackDuration.toMillis();

		double ratio = currentMilis / trackMilis;
		if (ratio >= 1.0) { // if track badly configured
			ratio = 1.0;
		}
		
		int stepsPlayed = (int) (BAR_STEPS * ratio);
		int stepsRemaining = BAR_STEPS - stepsPlayed;

		String playedPart = "▓".repeat(stepsPlayed);
		String remainingPart = "░".repeat(stepsRemaining);

		String line = "( " + button + " ) " + playedPart + remainingPart + "";
		PrintUtil.print(line);
	}

	/**
	 * Returns the play/pause/stop button char.
	 * 
	 * @param status
	 * @return
	 */
	private String chooseStatusButton(JMOPStatus status) {
		if (status.isPlaying()) {
			return "▶";
		}
		if (status.isPaused()) {
			return "⏸";
		}
		if (status.isStopped()) {
			return "⏹";
		}

		return "  ";
	}
}
