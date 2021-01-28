package cz.martlin.jmop.common.data.misc;

/**
 * An index of the track in the playlist. Since java uses zero-based indexing
 * but user's native indexing is one-based, this class combines both. You can
 * choose, which indexing system you desire to use, no matter what the actual
 * value is.
 * 
 * @author martin
 *
 */
public class TrackIndex implements Comparable<TrackIndex> {

	private final int index;

	private TrackIndex(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("The index cannot be " + index);
		}

		this.index = index;
	}

	/////////////////////////////////////////////////////////////////

	public int getIndex() {
		return index;
	}

	public int getHuman() {
		return indexToHuman(index);
	}

	/////////////////////////////////////////////////////////////////

	public boolean equal(TrackIndex another) {
		return this.index == another.index;
	}


	public boolean notEqual(TrackIndex another) {
		return this.index != another.index;
	}


	
	public boolean smallerThan(TrackIndex big) {
		return this.index < big.index;
	}

	public boolean smallerThan(int big) {
		return this.index < big;
	}

	public boolean smallerOrEqual(TrackIndex big) {
		return this.index <= big.index;
	}

	public boolean biggerThan(TrackIndex small) {
		return this.index > small.index;
	}

	public boolean biggerThan(int small) {
		return this.index > small;
	}

	public boolean biggerOrEqualThan(int small) {
		return this.index >= small;
	}

	public TrackIndex increment() {
		return new TrackIndex(index + 1);
	}

	public TrackIndex decrement() {
		return new TrackIndex(index - 1);
	}

	public TrackIndex offset(int offset) {
		return new TrackIndex(index + offset);
	}

	/////////////////////////////////////////////////////////////////

	private static int indexToHuman(int index) {
		return index + 1;
	}

	private static int humanToIndex(int human) {
		return human - 1;
	}

	/////////////////////////////////////////////////////////////////

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		TrackIndex other = (TrackIndex) obj;
		if (index != other.index)
			return false;
		return true;
	}

	@Override
	public int compareTo(TrackIndex another) {
		return Integer.compare(this.index, another.index);
	}

	@Override
	public String toString() {
		return "TrackIndex [index=" + index + "]";
	}

	/////////////////////////////////////////////////////////////////

	public static TrackIndex ofIndex(int index) {
		return new TrackIndex(index);
	}

	public static TrackIndex ofHuman(int human) {
		int index = humanToIndex(human);
		return new TrackIndex(index);
	}

	/////////////////////////////////////////////////////////////////

}
