package cz.martlin.jmop.sourcery.picocli.commands;

import java.util.List;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;
import cz.martlin.jmop.sourcery.fascade.JMOPRemote;
import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import cz.martlin.jmop.sourcery.picocli.misc.Service;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "add")
public class RemoteAddCommand implements Runnable {

	private final JMOPSourcery sourcery;
	
	
	
	@Option(names = {"--service", "-s"}, required = true)
	private Service service;
	
	@Option(names = {"--bundle", "-b"}, required = true)
	private Bundle bundle;
	
	@Option(names = {"--download", "-D"}, required = false)
	private boolean download = true;
	
	@Option(names = {"--no-download", "-n"}, required = false)
	private boolean noDownload = false;
	
	//TODO allow multiple playlists?
	@Option(names = {"--add-to-playlist", "-ap"}, required = false)
	private Playlist addToPlaylist;
	
	@Option(names = {"--create-as-playlist", "-cp"}, required = false)
	private String createAsPlaylist;
	
	@Option(names = {"--playlist", "-p"}, required = false)
	private Playlist playlist;
	
	@Parameters(descriptionKey = "QUERY", arity = "1..*")
	private List<String> queries;
	

	public RemoteAddCommand(JMOPSourcery sourcery) {
		super();
		this.sourcery = sourcery;
	}

	/////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public void run() {
		System.out.println(this); //XXX debug
		
		try {
			JMOPRemote remote = pickRemote(service);
			List<Track> tracks = remote.add(bundle, queries, download);
			playlistThem(tracks);
		} catch (JMOPSourceryException e) {
			e.printStackTrace(); //XXX  debug
			System.err.println(e);
		}
	}

	private JMOPRemote pickRemote(Service service) {
		switch (service) {
		case YOUTUBE:
			return sourcery.youtube(); 
		default:
			throw new IllegalArgumentException("Unsupported service: " + service);
		}
	}


	private void playlistThem(List<Track> tracks) {
		
		if (addToPlaylist != null) {
//			sourcery.musicbase().addToPlaylist(addToPlaylist, tracks);
		}
		if (createAsPlaylist != null) {
//			sourcery.createAndAddToPlaylist(createAsPlaylist, tracks);
		}
		if (playlist != null) {
//			sourcery.createOrAddToPlaylist(playlist, tracks);
		}
		
		//TODO implementme
		throw new UnsupportedOperationException("Add as playlist not yet implemented.");
		
	}

	@Override
	public String toString() {
		return "RemoteAddCommand [service=" + service + ", bundle=" + bundle + ", download=" + download + ", noDownload="
				+ noDownload + ", addToPlaylist=" + addToPlaylist + ", createAsPlaylist=" + createAsPlaylist
				+ ", playlist=" + playlist + ", queries=" + queries + "]";
	}

	
	/////////////////////////////////////////////////////////////////////////////////////

	

}
