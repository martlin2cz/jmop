package cz.martlin.jmop.core.data;

import java.util.Calendar;

public class Metadata {
	private final Calendar created;
	private Calendar lastPlayed;
	private int numberOfPlays;

	private Metadata(Calendar created, Calendar lastPlayed, int numberOfPlays) {
		super();
		this.created = created;
		this.lastPlayed = lastPlayed;
		this.numberOfPlays = numberOfPlays;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Calendar getLastPlayed() {
		return lastPlayed;
	}

	public int getNumberOfPlays() {
		return numberOfPlays;
	}

	public Calendar getCreated() {
		return created;
	}

	public void markPlayed() {
		this.lastPlayed = Calendar.getInstance();
		this.numberOfPlays++;
		//TODO fire event?
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public static Metadata createNew() {
		Calendar created = Calendar.getInstance();
		Calendar lastPlayed = null;
		int numberOfPlays = 0;

		return new Metadata(created, lastPlayed, numberOfPlays);
	}

	public static Metadata createExisting(Calendar created, Calendar lastPlayed, int numberOfPlays) {
		return new Metadata(created, lastPlayed, numberOfPlays);
	}

}
