package cz.martlin.jmop.player.cli.repl.converters;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martlin.jmop.common.data.misc.PlaylistModifier;
import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import picocli.CommandLine;

public class TrackIndexConverter extends AbstractJMOPConverter<TrackIndex> {

	private static final Pattern ABSOLUTE_TRACK_PATTERN = Pattern.compile("^([0-9]+)(\\.?)$");
	private static final Pattern RELATIVE_TRACK_PATTERN = Pattern.compile("^(\\+|\\-)([0-9]+)(\\.?)$");

	public TrackIndexConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public TrackIndex convert(String value) throws Exception {
		TrackIndex index;
		index = tryAbsoluteIndex(value);
		if (index != null) {
			return index;
		}

		index = tryRelativeIndex(value);
		if (index != null) {
			return index;
		}

		index = tryTrackTitle(value);
		if (index != null) {
			return index;
		}

		throw new CommandLine.TypeConversionException("No such track " + value);
	}

	private TrackIndex tryAbsoluteIndex(String value) {
		Matcher absoluteTrackMatcher = ABSOLUTE_TRACK_PATTERN.matcher(value);
		if (absoluteTrackMatcher.matches()) {
			String group = absoluteTrackMatcher.group(1);
			int indx = Integer.parseInt(group);

			TrackIndex index = TrackIndex.ofHuman(indx);
			validateIndex(index);
			return index;
		}

		return null;
	}

	private TrackIndex tryRelativeIndex(String value) {
		Matcher relativeTrackMatcher = RELATIVE_TRACK_PATTERN.matcher(value);
		if (relativeTrackMatcher.matches()) {
			String group = relativeTrackMatcher.group(2);
			int indexOffset = Integer.parseInt(group);

			TrackIndex currentTrackIndex = playlist().getCurrentTrackIndex();
			TrackIndex index = currentTrackIndex.offset(indexOffset);
			validateIndex(index);
			return index;
		}

		return null;
	}

	private TrackIndex tryTrackTitle(String value) {
		String trackTitle = value;
		Bundle bundle = jmop.playing().currentBundle();
		Track track = jmop.musicbase().trackOfTitle(bundle, trackTitle);
		if (track == null) {
			throw new CommandLine.TypeConversionException("Such track does not exist " + value);
		}

		PlaylistModifier modifier = jmop.musicbase().modifyPlaylist(playlist());
		List<TrackIndex> indexes = modifier.find(track);
		if (indexes.isEmpty()) {
			throw new CommandLine.TypeConversionException("Playlist does not contain track " + value);
		}
		if (indexes.size() > 1) {
			System.err.println("Warning, this track happens to be more than once in the playlist");
		}

		TrackIndex index = indexes.get(0);
		return index;
	}

	//TODO generalize to general playlist
	private Playlist playlist() {
		return jmop.playing().currentPlaylist();
	}

	private void validateIndex(TrackIndex index) {
		int count = playlist().getTracks().count();

		if (index.biggerOrEqualThan(count)) {
			throw new CommandLine.TypeConversionException("The playlist contains only " + count + " tracks, "
					+ "thus index " + index.getHuman() + " is invalid");
		}
	}

}
