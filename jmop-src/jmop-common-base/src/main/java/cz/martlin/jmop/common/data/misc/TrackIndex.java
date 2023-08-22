package cz.martlin.jmop.common.data.misc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(TrackIndex.class);
	
	/**
	 * The actual index (zero-indexed).
	 */
	private final int index;

	/**
	 * Creates.
	 * 
	 * @param index
	 */
	private TrackIndex(int index) {
		if (index < 0) {
			throw new IllegalArgumentException("The index cannot be " + index);
		}

		this.index = index;
	}

	/////////////////////////////////////////////////////////////////

	/**
	 * Returns the index (zero indexed).
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Returns the index in human-readable counting (starting by 1).
	 * @return
	 */
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

	/**
	 * Constructs the index of the zero-index index.
	 * 
	 * @param index
	 * @return
	 */
	public static TrackIndex ofIndex(int index) {
		return new TrackIndex(index);
	}

	/**
	 * Constructs the index of the human readable format (starting from 1).
	 * 
	 * @param human
	 * @return
	 */
	public static TrackIndex ofHuman(int human) {
		int index = humanToIndex(human);
		return new TrackIndex(index);
	}

	/////////////////////////////////////////////////////////////////

	/**
	 * Lists the elements of the given map ordered by the indexes.
	 * 
	 * @param <E>
	 * @param map
	 * @return
	 */
	public static <E> List<E> list(Map<TrackIndex, E> map) {
		TreeMap<TrackIndex, E> ordered = new TreeMap<>(map);

		// just verify there are no missing indexes
		Set<TrackIndex> orderedKeys = ordered.keySet();
		orderedKeys.stream().sequential().reduce(null, (previous, current) -> {
			// if not yet any previous to compare to, terminate immediatelly
			if (previous == null) {
				return current;
			}
			
			// if current is not exactly following the previous, warn
			if (previous.index + 1 != current.index) {
				LOGGER.warn("There is/are no item(s) between indexes {} and {}", previous, current);
			}
			
			// but return the current anyway
			return current;
		});

		Collection<E> orderedValues = ordered.values();
		return new ArrayList<>(orderedValues);
	}

	/**
	 * Converts the list of elements into indexed map.
	 * 
	 * @param <E>
	 * @param list
	 * @return
	 */
	public static <E> Map<TrackIndex, E> map(List<E> list) {
		Map<TrackIndex, E> map = new TreeMap<>();

		for (int i = 0; i < list.size(); i++) {
			E item = list.get(i);

			TrackIndex index = TrackIndex.ofIndex(i);
			map.put(index, item);
		}

		return map;
	}

	/////////////////////////////////////////////////////////////////

}
