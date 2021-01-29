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

public abstract class AbstractTrackIndexConverter extends AbstractJMOPConverter<TrackIndex> {

	private static final Pattern ABSOLUTE_TRACK_PATTERN = Pattern.compile("^([0-9]+)(\\.?)$");
	private static final Pattern RELATIVE_TRACK_PATTERN = Pattern.compile("^(\\+|\\-)([0-9]+)(\\.?)$");

	public AbstractTrackIndexConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public TrackIndex convert(String value) throws Exception {
		return trackIndex(value);
	}

	public TrackIndex trackIndex(String trackIndexSpecifierOrTitle) {
		TrackIndex index;
		index = tryAbsoluteIndex(trackIndexSpecifierOrTitle);
		if (index != null) {
			return index;
		}
	
		index = tryRelativeIndex(trackIndexSpecifierOrTitle);
		if (index != null) {
			return index;
		}
	
		index = tryTrackTitle(trackIndexSpecifierOrTitle);
		if (index != null) {
			return index;
		}
	
		throw new CommandLine.TypeConversionException("No such track " + trackIndexSpecifierOrTitle);
	}

	private TrackIndex tryAbsoluteIndex(String trackIndexSpecifier) {
		Matcher matcher = ABSOLUTE_TRACK_PATTERN.matcher(trackIndexSpecifier);
		if (matcher.matches()) {
			String group = matcher.group(1);
			int indx = Integer.parseInt(group);
	
			TrackIndex index = TrackIndex.ofHuman(indx);
			validateIndex(index);
			return index;
		}
	
		return null;
	}

	private TrackIndex tryRelativeIndex(String trackIndexSpecifier) {
		Matcher matcher = RELATIVE_TRACK_PATTERN.matcher(trackIndexSpecifier);
		if (matcher.matches()) {
			String group = matcher.group(1) + matcher.group(2);
			int indexOffset = Integer.parseInt(group);
			
			Playlist playlist = playlist();
			if (playlist == null) {
				throw new CommandLine.TypeConversionException("No current track");
			}

			TrackIndex currentTrackIndex = playlist.getCurrentTrackIndex();
			TrackIndex index = currentTrackIndex.offset(indexOffset);
			
			validateIndex(index);
			return index;
		}
	
		return null;
	}

	private TrackIndex tryTrackTitle(String trackTitle) {
		Bundle bundle = bundle();
		Track track = jmop.musicbase().trackOfTitle(bundle, trackTitle);
		if (track == null) {
			throw new CommandLine.TypeConversionException("Such track does not exist " + trackTitle);
		}
		
		Playlist playlist = playlist();
		PlaylistModifier modifier = jmop.musicbase().modifyPlaylist(playlist);
		List<TrackIndex> indexes = modifier.find(track);
		
		if (indexes.isEmpty()) {
			throw new CommandLine.TypeConversionException("Playlist does not contain track " + trackTitle);
		}
		if (indexes.size() > 1) {
			System.err.println("Warning, this track happens to be more than once in the playlist");
		}
	
		TrackIndex index = indexes.get(0);
		return index;
	}

	private void validateIndex(TrackIndex index) {
		Playlist playlist = playlist();
		int count = playlist.getTracks().count();
	
		if (index.biggerOrEqualThan(count)) {
			throw new CommandLine.TypeConversionException( //
					"The playlist contains only " + count + " tracks, "
					+ "thus index " + index.getHuman() + " is invalid");
		}
	}
	
	protected abstract Bundle bundle();

	protected abstract Playlist playlist();

}