package cz.martlin.jmop.player.cli.repl.XXX_commands.musicbase;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;
import cz.martlin.jmop.player.cli.repl.XXX_commands.AbstractCommand;
import cz.martlin.jmop.player.fascade.JMOPPlayerFascade;
import javafx.util.Duration;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "track")
public class CreateTrackCommand extends AbstractCommand {



	@Parameters(arity = "1", index = "0")
	private Bundle bundle;

	@Parameters(arity = "1", index = "1")
	private String title;

	@Option(names = "description", required = false)
	private String description;

	@Option(names = "identifier", required = false)
	private String identifier;

	@Option(names = "duration", required = true)
	private Duration duration;

	public CreateTrackCommand(JMOPPlayerFascade fascade) {
		super(fascade);
	}

	@Override
	protected void doRun()  {
		TrackData data = new TrackData(identifier, title, description, duration);
		fascade.createNewTrack(bundle, data);
	}

}
