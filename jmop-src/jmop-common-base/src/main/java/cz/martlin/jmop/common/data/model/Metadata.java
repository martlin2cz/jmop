package cz.martlin.jmop.common.data.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;

import javafx.util.Duration;

/**
 * An immutable structure holding the general metadata of bundle, playlist or track.
 * @author martin
 *
 */
public class Metadata {
	//TODO replace Calendar with something more ...
	//TODO make the fields final
	
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
		LocalDateTime newLastPlayed = LocalDateTime.now();
		Duration newTotalTime = this.totalTime;
		
		return createExisting(newCreated, newLastPlayed, newNumberOfPlays, newTotalTime);
	}
	
	public Metadata played(Duration playedTime) {
		LocalDateTime newCreated = this.created;
		int newNumberOfPlays = this.numberOfPlays + 1;
		LocalDateTime newLastPlayed = LocalDateTime.now();
		Duration newTotalTime = this.totalTime.add(playedTime);
		
		return createExisting(newCreated, newLastPlayed, newNumberOfPlays, newTotalTime);
	}
	
	
	public static Metadata createNew() {
		LocalDateTime created = LocalDateTime.now();
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
				numberOfPlays, 
				new Duration(0));
	}
	
	public static Metadata createExisting(LocalDateTime created, LocalDateTime lastPlayed, int numberOfPlays, Duration totalTime) {
		return new Metadata(created, lastPlayed, numberOfPlays, totalTime);
	}

}
