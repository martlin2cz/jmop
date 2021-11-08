package cz.martlin.jmop.common.musicbase.stats;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.martlin.jmop.common.data.misc.HasMetadata;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseLoading;
import cz.martlin.jmop.common.musicbase.misc.MusicbaseListingEncapsulator;
import javafx.util.Duration;

/**
 * Tool which computes various stats of the provided musicbase.
 * 
 * @author martin
 *
 */
public class MusicbaseStats {

	/**
	 * What to list (in what order the items should be)?
	 * 
	 * @author martin
	 *
	 */
	public enum ListStatsSpecifier {
		FIRST_CREATED, LAST_PLAYED, MOST_PLAYED
	}

	private final MusicbaseListingEncapsulator listing;

	public MusicbaseStats(BaseMusicbaseLoading musicbase) {
		super();

		this.listing = new MusicbaseListingEncapsulator(musicbase);
	}

	///////////////////////////////////////////////////////////////////////////
	/**
	 * Lists the bundles based on the given conditions.
	 * 
	 * @param what       what bundles to list (the order)?
	 * @param countOrAll list all or just the specified amount?
	 * @return
	 */
	public Queue<Bundle> bundles(ListStatsSpecifier what, Integer countOrAll) {
		Comparator<Bundle> comparator = itemsComparator(what);
		Set<Bundle> bundles = listing.bundles();
		return list(bundles, comparator, countOrAll);
	}

	/**
	 * Lists the bundles with the biggest amounts of tracks.
	 * 
	 * @param countOrAll list all or just the specified amount?
	 * @return
	 */
	public Queue<Bundle> biggestBundlesByTracks(Integer countOrAll) {
		Comparator<Bundle> comparator = (x, y) -> //
		- (listing.tracks(x).size() - listing.tracks(y).size());

		Set<Bundle> bundles = listing.bundles();
		return list(bundles, comparator, countOrAll);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Lists the playlists based on the given conditions.
	 * 
	 * @param bundleOrAll playlists just from the provided bundle or all?
	 * @param what        what playlists to list (the order)?
	 * @param countOrAll  list all or just the specified amount?
	 * @return
	 */
	public Queue<Playlist> playlists(Bundle bundleOrAll, ListStatsSpecifier what, Integer countOrAll) {
		Comparator<Playlist> comparator = itemsComparator(what);
		Set<Playlist> playlists = listing.playlists(bundleOrAll);
		return list(playlists, comparator, countOrAll);
	}

	/**
	 * Lists the playlists by the longest (most tracks).
	 * 
	 * @param bundleOrAll playlists just from the provided bundle or all?
	 * @param countOrAll  list all or just the specified amount?
	 * @return
	 */
	public Queue<Playlist> longestPlaylistByNumberOfTracks(Bundle bundleOrAll, Integer countOrAll) {
		Comparator<Playlist> comparator = (x, y) -> //
		-(x.getTracks().getTracks().size() - y.getTracks().getTracks().size());

		Set<Playlist> playlists = listing.playlists(bundleOrAll);
		return list(playlists, comparator, countOrAll);
	}

	/**
	 * Lists the playlists based by the longest (most total time).
	 * 
	 * @param bundleOrAll playlists just from the provided bundle or all?
	 * @param countOrAll  list all or just the specified amount?
	 * @return
	 */
	public Queue<Playlist> longestPlaylistByTotalDuration(Bundle bundleOrAll, Integer countOrAll) {
		Comparator<Playlist> comparator = (x, y) -> //
		-x.getTotalDuration().compareTo(y.getTotalDuration());

		Set<Playlist> playlists = listing.playlists(bundleOrAll);
		return list(playlists, comparator, countOrAll);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Lists the tracks based on the given conditions.
	 * 
	 * @param bundleOrAll tracks just from the provided bundle or all?
	 * @param what        what tracks to list (the order)?
	 * @param countOrAll  list all or just the specified amount?
	 * @return
	 */
	public Queue<Track> tracks(Bundle bundleOrAll, ListStatsSpecifier what, Integer countOrAll) {
		Comparator<Track> comparator = itemsComparator(what);
		Set<Track> tracks = listing.tracks(bundleOrAll);
		return list(tracks, comparator, countOrAll);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Returns total count of tracks.
	 * 
	 * @param bundleOrAll just from the provided bundle or all?
	 * @return
	 * @deprecated simply replace by musicbase#tracks(bundleOrAll)
	 */
	@Deprecated
	public int numberOfTracks(Bundle bundleOrAll) {
		Set<Track> tracks = listing.tracks(bundleOrAll);
		Function<Track, Integer> mapper = (t) -> 1;
		return sumInts(tracks, mapper);
	}

	/**
	 * Returns total number of plays of tracks.
	 * 
	 * @param bundleOrAll just from the provided bundle or all?
	 * @return
	 */
	public int totalPlayedTracks(Bundle bundleOrAll) {
		Set<Track> tracks = listing.tracks(bundleOrAll);
		Function<Track, Integer> mapper = (t) -> t.getMetadata().getNumberOfPlays();
		return sumInts(tracks, mapper);
	}

	/**
	 * Returns total duration of all the tracks.
	 * 
	 * @param bundleOrAll just from the provided bundle or all?
	 * @return
	 */
	public Duration totalDuration(Bundle bundleOrAll) {
		Set<Track> tracks = listing.tracks(bundleOrAll);
		Function<Track, Duration> mapper = (t) -> t.getDuration();
		return sumDurations(tracks, mapper);
	}

	/**
	 * Returns total played time of all the tracks.
	 * 
	 * @param bundleOrAll just from the provided bundle or all?
	 * @return
	 */
	public Duration totalPlayedTime(Bundle bundleOrAll) {
		Set<Track> tracks = listing.tracks(bundleOrAll);
		Function<Track, Duration> mapper = (t) -> t.getDuration().multiply(t.getMetadata().getNumberOfPlays());
		return sumDurations(tracks, mapper);
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Applies the mapper to each elem and sums.
	 * 
	 * @param <E>
	 * @param elems
	 * @param mapper
	 * @return
	 */
	private <E extends HasMetadata> int sumInts(Set<E> elems, Function<E, Integer> mapper) {
		return elems.stream() //
				.reduce(0, //
						(i, e) -> i + mapper.apply(e), //
						(x, y) -> x + y);
	}

	/**
	 * Applies the mapper to each elem and sums.
	 * 
	 * @param <E>
	 * @param elems
	 * @param mapper
	 * @return
	 */
	private <E extends HasMetadata> Duration sumDurations(Set<E> elems, Function<E, Duration> mapper) {
		return elems.stream() //
				.reduce(Duration.ZERO, //
						(d, e) -> d.add(mapper.apply(e)), //
						(x, y) -> new Duration(x.toMillis() + y.toMillis()));
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Lists the elements sorted by the comparator, optionally limiting to given
	 * number.
	 * 
	 * @param <E>
	 * @param <V>
	 * @param elements
	 * @param comparator
	 * @param countOrAll
	 * @return
	 */
	private static <E extends HasMetadata, V extends Comparable<V>> Queue<E> list(Set<E> elements,
			Comparator<E> comparator, Integer countOrAll) {

		long limit = countOrAll != null ? countOrAll : Long.MAX_VALUE;
		return new LinkedList<>( //
				elements.stream() //
						.sorted(comparator) //
						.limit(limit) //
						.collect(Collectors.toList())); //
	}

	/**
	 * The comparator producing ordering given by the given specifier.
	 * 
	 * @param <E>
	 * @param what
	 * @return
	 */
	private static <E extends HasMetadata> Comparator<E> itemsComparator(ListStatsSpecifier what) {
		switch (what) {
		case FIRST_CREATED:
			return (x, y) -> -compareNullSmaller(x.getMetadata().getCreated(), //
					y.getMetadata().getCreated());
		case LAST_PLAYED:
			return (x, y) -> -compareNullSmaller(x.getMetadata().getLastPlayed(), //
					y.getMetadata().getLastPlayed());
		case MOST_PLAYED:
			return ((x, y) -> -Integer.compare(x.getMetadata().getNumberOfPlays(), //
					y.getMetadata().getNumberOfPlays()));
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Compare method of two possible nulled Comparables. Null assumed the smaller
	 * one.
	 * 
	 * @param <E>
	 * @param first
	 * @param second
	 * @return
	 */
	private static <E extends Comparable<E>> int compareNullSmaller(E first, E second) {
		if (first == null && second == null) {
			return 0;
		}
		if (first != null && second == null) {
			return +1;
		}
		if (first == null && second != null) {
			return -1;
		}
		if (first != null && second != null) {
			return first.compareTo(second);
		}
		return 0; // will never happen
	}

	///////////////////////////////////////////////////////////////////////////

}
