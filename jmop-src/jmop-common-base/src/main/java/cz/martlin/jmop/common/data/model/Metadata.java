package cz.martlin.jmop.common.data.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import javafx.util.Duration;

/**
 * An immutable structure holding the general metadata of bundle, playlist or
 * track.
 * 
 * USe the static {@link #createNew()} and {@link #createExisting(Calendar, Calendar, int)} to obtain instance.
 * 
 * 
 * @author martin
 *
 */
public class Metadata {

	/**
	 * The date of creation. Never null.
	 */
	private final LocalDateTime created;
	
	/**
	 * The date of last play. Can be null (if never played).
	 */
	private final LocalDateTime lastPlayed;
	
	/**
	 * Number of plays. 
	 */
	private final int numberOfPlays;
	
	/**
	 * Total played time. Never null.
	 */
	private final Duration totalTime;

	/**
	 * Create.
	 * 
	 * @param created
	 * @param lastPlayed
	 * @param numberOfPlays
	 * @param totalTime
	 */
	private Metadata(LocalDateTime created, LocalDateTime lastPlayed, int numberOfPlays, Duration totalTime) {
		super();
		this.created = created;
		this.lastPlayed = lastPlayed;
		this.numberOfPlays = numberOfPlays;
		this.totalTime = totalTime;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public LocalDateTime getLastPlayed() {
		return lastPlayed;
	}

	public int getNumberOfPlays() {
		return numberOfPlays;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public Duration getTotalTime() {
		return totalTime;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Constructs the metadata for the played element.
	 * 
	 * @param playedTime how long it was played.
	 * 
	 * @return
	 */
	public Metadata played(Duration playedTime) {
		LocalDateTime newCreated = this.created;
		int newNumberOfPlays = this.numberOfPlays + 1;
		LocalDateTime newLastPlayed = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		Duration newTotalTime = this.totalTime.add(playedTime);

		return createExisting(newCreated, newLastPlayed, newNumberOfPlays, newTotalTime);
	}

/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates metadata for thing just fresly created.
	 * 
	 * @return
	 */
	public static Metadata createNew() {
		LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		LocalDateTime lastPlayed = null;
		int numberOfPlays = 0;
		Duration totalTime = new Duration(0);

		return new Metadata(created, lastPlayed, numberOfPlays, totalTime);
	}

	/**
	 * Creates metadata from the given (existing) fields.
	 * 
	 * @param created
	 * @param lastPlayed
	 * @param numberOfPlays
	 * @param totalTime
	 * @return
	 */
	public static Metadata createExisting(LocalDateTime created, LocalDateTime lastPlayed, int numberOfPlays,
			Duration totalTime) {
		return new Metadata(created, lastPlayed, numberOfPlays, totalTime);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((created == null) ? 0 : created.hashCode());
		result = prime * result + ((lastPlayed == null) ? 0 : lastPlayed.hashCode());
		result = prime * result + numberOfPlays;
		result = prime * result + ((totalTime == null) ? 0 : totalTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Metadata other = (Metadata) obj;
		if (created == null) {
			if (other.created != null)
				return false;
		} else if (!created.equals(other.created))
			return false;
		if (lastPlayed == null) {
			if (other.lastPlayed != null)
				return false;
		} else if (!lastPlayed.equals(other.lastPlayed))
			return false;
		if (numberOfPlays != other.numberOfPlays)
			return false;
		if (totalTime == null) {
			if (other.totalTime != null)
				return false;
		} else if (!totalTime.equals(other.totalTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Metadata [created=" + created + ", lastPlayed=" + lastPlayed + ", numberOfPlays=" + numberOfPlays
				+ ", totalTime=" + totalTime + "]";
	}

}
