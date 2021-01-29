package cz.martlin.jmop.player.cli.repl.commands;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.player.cli.repl.command.AbstractRunnableCommand;
import cz.martlin.jmop.player.cli.repl.converters.BundleConverter;
import cz.martlin.jmop.player.cli.repl.converters.CurrentPlaylistTrackIndexConverter;
import cz.martlin.jmop.player.cli.repl.converters.PlaylistConverter;
import cz.martlin.jmop.player.cli.repl.converters.TrackConverter;
import cz.martlin.jmop.player.fascade.JMOPPlayer;
import cz.martlin.jmop.player.players.PlayerStatus;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "p")
public class TheCommandP extends AbstractRunnableCommand {

	@Parameters(arity = "0..1")
	private String argument;

	public TheCommandP(JMOPPlayer jmop) {
		super(jmop);
	}

	@Override
	protected void doRun() {
		if (argument == null) {
			doWithNoArgument();
		} else {
			doWithArgument(argument);
		}
	}

	private void doWithNoArgument() {
		if (jmop.playing().currentPlaylist() == null) {
			throw new CommandLine.TypeConversionException("Specify what to play");
		}
		
		PlayerStatus status = jmop.playing().currentStatus();
		if (status.isNotPlayingTrack()) {
			jmop.playing().play();
			return;
		}
		if (status.isPlaying()) {
			jmop.playing().pause();
			return;
		}
		if (status.isPaused()) {
			jmop.playing().resume();
			return;
		}
		
		throw new IllegalStateException("This should never happen");
	}

	private void doWithArgument(String argument) {
		try {
			TrackIndex index = CurrentPlaylistTrackIndexConverter.convertIndex(jmop, argument);
			jmop.playing().play(index);
			return;
		} catch (Exception e) {
			// okay, go on
		}
		
		try {
			Bundle bundle = null; // assume all bundles
			Track track = TrackConverter.convertTrack(jmop, bundle, argument);
			jmop.playing().play(track);
			return;
		} catch (Exception e) {
			// okay, go on
		}
		
		try {
			Bundle bundle = null; // assume all bundles
			Playlist playlist = PlaylistConverter.convertPlaylist(jmop, bundle, argument);
			jmop.playing().play(playlist);
			return;
		} catch (Exception e) {
			// okay, go on
		}
		
		try {
			Bundle bundle = BundleConverter.convertBundle(jmop, argument);
			jmop.playing().play(bundle);
			return;
		} catch (Exception e) {
			// okay, go on
		}
		
		throw new CommandLine.TypeConversionException("Nothing named " + argument + " can be played");
	}

}
