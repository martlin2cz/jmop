package cz.martlin.jmop.common.data.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

import javafx.util.Duration;

/**
 * An immutable structure holding the general metadata of bundle, playlist or
 * track.
 * 
 * @author martin
 *
 */
public class Metadata {
	// TODO replace Calendar with something more ...
	// TODO make the fields final

	private final LocalDateTime created;
	private final LocalDateTime lastPlayed;
	private final int numberOfPlays;
	private final Duration totalTime;

	private Metadata(LocalDateTime created, LocalDateTime lastPlayed, int numberOfPlays, Duration totalTime) {
		super();
		this.created = created;
		this.lastPlayed = lastPlayed;
		this.numberOfPlays = numberOfPlays;
		this.totalTime = totalTime;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	@Deprecated
	public Calendar getLastPlayedCal() {
		if (lastPlayed == null) {
			return null;
		}
		long ms = lastPlayed.toEpochSecond(ZoneOffset.UTC);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ms);
		return cal;
	}

	public LocalDateTime getLastPlayed() {
		return lastPlayed;
	}

	public int getNumberOfPlays() {
		return numberOfPlays;
	}

	@Deprecated
	public Calendar getCreatedCal() {
		if (lastPlayed == null) {
			return null;
		}
		long ms = created.toEpochSecond(ZoneOffset.UTC);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(ms);
		return cal;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public Duration getTotalTime() {
		return totalTime;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Deprecated
	public Metadata played() {
		LocalDateTime newCreated = this.created;
		int newNumberOfPlays = this.numberOfPlays + 1;
		LocalDateTime newLastPlayed = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		Duration newTotalTime = this.totalTime;

		return createExisting(newCreated, newLastPlayed, newNumberOfPlays, newTotalTime);
	}

	public Metadata played(Duration playedTime) {
		LocalDateTime newCreated = this.created;
		int newNumberOfPlays = this.numberOfPlays + 1;
		LocalDateTime newLastPlayed = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		Duration newTotalTime = this.totalTime.add(playedTime);

		return createExisting(newCreated, newLastPlayed, newNumberOfPlays, newTotalTime);
	}

/////////////////////////////////////////////////////////////////////////////////////

	public static Metadata createNew() {
		LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
		LocalDateTime lastPlayed = null;
		int numberOfPlays = 0;
		Duration totalTime = new Duration(0);

		return new Metadata(created, lastPlayed, numberOfPlays, totalTime);
	}

	@Deprecated
	public static Metadata createExisting(Calendar created, Calendar lastPlayed, int numberOfPlays) {
		return new Metadata(
				created == null ? null : LocalDateTime.ofInstant(created.toInstant(), ZoneId.systemDefault()),
				lastPlayed == null ? null : LocalDateTime.ofInstant(lastPlayed.toInstant(), ZoneId.systemDefault()),
				numberOfPlays, new Duration(0));
	}

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
