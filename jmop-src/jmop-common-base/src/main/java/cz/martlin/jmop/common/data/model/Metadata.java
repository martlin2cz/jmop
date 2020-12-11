package cz.martlin.jmop.common.data.model;

import java.util.Calendar;

/**
 * An immutable structure holding the general metadata of bundle, playlist or track.
 * @author martin
 *
 */
public class Metadata {
	//TODO replace Calendar with something more ...
	//TODO make the fields final
	
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

	/**
	 * @deprecated replaced by {@link #played()}.
	 */
	@Deprecated
	public void markPlayed() {
		this.lastPlayed = Calendar.getInstance();
		this.numberOfPlays++;
		//TODO fire event?
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public Metadata played() {
		Calendar newCreated = this.created;
		int newNumberOfPlays = numberOfPlays + 1;
		Calendar newLastPlayed = Calendar.getInstance();
		
		return createExisting(newCreated, newLastPlayed, newNumberOfPlays);
	}
	
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
