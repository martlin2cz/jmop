package cz.martlin.jmop.player.cli.repl.converters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.fascade.JMOPPlayer;

public class TrackOfPlaylistConverter extends AbstractJMOPConverter<Integer> {

	private static final Pattern ABSOLUTE_TRACK_PATTERN = Pattern.compile("^([0-9]+)(\\.?)$");
	private static final Pattern RELATIVE_TRACK_PATTERN = Pattern.compile("^(\\+|\\-)([0-9]+)(\\.?)$");
	
	public TrackOfPlaylistConverter(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	public Integer convert(String value) throws Exception {
		Matcher absoluteTrackMatcher = ABSOLUTE_TRACK_PATTERN.matcher(value);
		if (absoluteTrackMatcher.matches()) {
			String group = absoluteTrackMatcher.group(1);
			int index = Integer.parseInt(group);
			//TODO validate
			return index; 
		}

		Matcher relativeTrackMatcher = RELATIVE_TRACK_PATTERN.matcher(value);
		if (relativeTrackMatcher.matches()) {
			String group = relativeTrackMatcher.group(2);
			int indexOffset = Integer.parseInt(group);
			
			int index = jmop.playing().currentPlaylist().getCurrentTrackIndex() + indexOffset;
			//TODO validate
			return index;
		}
		
		String trackTitle = value; //TODO validate
		Bundle bundle = jmop.playing().currentBundle();
		Track track = jmop.musicbase().trackOfTitle(bundle , trackTitle);
		return jmop.playing().currentPlaylist().getTracks().getTracks().indexOf(track); //FIXMEEEEE
	}

}
