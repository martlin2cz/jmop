package cz.martlin.jmop.common.utils;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPMusicbaseException;

public class TestingMusicbase implements AutoCloseable {

	private final BaseMusicbaseModifing musicbase;
	
	public Bundle daftPunk;
	public Playlist discovery;
	public Playlist randomAccessMemories;
	public Track oneMoreTime;
	public Track aerodynamic;
	public Track verdisQuo;
	public Track getLucky;

	public Bundle londonElektricity;
	public Playlist syncopatedCity;
	public Playlist yikes;
	public Playlist bestTracks;
	public Track justOneSecond;
	public Track allTheHellIsBreakingLoose;
	public Track pointOfNoReturn;
	public Track meteorities;
	public Track invisibleWorlds;
	public Track elektricityWillKeepMeWarm;

	public Bundle cocolinoDeep;
	public Playlist seventeen;
	public Track seventeenPartI;
	public Track seventeenPartII;
	public Track seventeenPartIII;
	public Track seventeenPartIV;
	public Track dontForgetToFly;
	public Track getLost;
	public Track dancingWithTheElepthant;

	public Bundle robick;
	public Track atZijiDuchove;
	public Track znamkaPunku;
	public Track neniNutno;
	public Track ladyCarneval;

	public TestingMusicbase(BaseMusicbaseModifing musicbase, boolean fileExisting)  {
		super();
		
		this.musicbase = musicbase;

		prepare(musicbase, fileExisting);
	}

	private void prepare(BaseMusicbaseModifing musicbase, boolean fileExisting)  {
		createDaftPunk(musicbase, fileExisting);

		createLondonElektricity(musicbase, fileExisting);

		createCocolinoDeep(musicbase, fileExisting);

		createRobick(musicbase, fileExisting);

	}

	private void createDaftPunk(BaseMusicbaseModifing musicbase, boolean fileExisting)  {
		// bundle
		daftPunk = TestingDataCreator.bundle(musicbase, "Daft Punk");

		// playlists
		discovery = TestingDataCreator.playlist(musicbase, daftPunk, "Discovery");
		randomAccessMemories = TestingDataCreator.playlist(musicbase, daftPunk, "Random Acess Memories");

		// tracks
		oneMoreTime = TestingDataCreator.track(musicbase, daftPunk, "One More Time",
				"One More Time (featuring Romanthony)", "OMT", DurationUtilities.createDuration(0, 5, 20), fileExisting);
		aerodynamic = TestingDataCreator.track(musicbase, daftPunk, "Aerodynamic", "Aerodynamic", "AD",
				DurationUtilities.createDuration(0, 3, 27), fileExisting);
		verdisQuo = TestingDataCreator.track(musicbase, daftPunk, "Veridis Quo", "Veridis Quo", "VQ",
				DurationUtilities.createDuration(0, 5, 44), fileExisting);
		getLucky = TestingDataCreator.track(musicbase, daftPunk, "Get Lucky", "Get Lucky (featuring Pharrell Williams)",
				"GL", DurationUtilities.createDuration(0, 6, 8), fileExisting);

		// init the playlists
		discovery.addTrack(oneMoreTime);
		discovery.addTrack(aerodynamic);
		discovery.addTrack(verdisQuo);
		musicbase.playlistUpdated(discovery);

		randomAccessMemories.addTrack(getLucky);
		musicbase.playlistUpdated(randomAccessMemories);
	}

	private void createLondonElektricity(BaseMusicbaseModifing musicbase, boolean fileExisting)  {
		// bundle
		londonElektricity = TestingDataCreator.bundle(musicbase, "London Elektricity");

		// playlists
		syncopatedCity = TestingDataCreator.playlist(musicbase, londonElektricity, "Syncopated City");
		yikes = TestingDataCreator.playlist(musicbase, londonElektricity, "Yikes!");
		bestTracks = TestingDataCreator.playlist(musicbase, londonElektricity, "best tracks");

		// tracks
		justOneSecond = TestingDataCreator.track(musicbase, londonElektricity, "Just One Second", "2. Just One Second",
				"JOS", DurationUtilities.createDuration(0, 5, 39), fileExisting);

		allTheHellIsBreakingLoose = TestingDataCreator.track(musicbase, londonElektricity, "All Hell Is Breaking Loose",
				"6. All Hell Is Breaking Loose", "AtHBL", DurationUtilities.createDuration(0, 4, 44), fileExisting);

		pointOfNoReturn = TestingDataCreator.track(musicbase, londonElektricity, "Point of No Return",
				"7. Point of No Return", "PoNR", DurationUtilities.createDuration(0, 5, 52), fileExisting);

		meteorities = TestingDataCreator.track(musicbase, londonElektricity, "Meteorites",
				"2. Meteorites (feat. Elsa Esmeralda)", "M", DurationUtilities.createDuration(0, 6, 2), fileExisting);

		invisibleWorlds = TestingDataCreator.track(musicbase, londonElektricity, "Invisible Worlds",
				"Invisible Worlds (feat. Elsa Esmeralda)", "IW", DurationUtilities.createDuration(0, 6, 45), fileExisting);

		elektricityWillKeepMeWarm = TestingDataCreator.track(musicbase, londonElektricity,
				"Elektricity Will Keep Me Warm", "1. Elektricity Will Keep Me Warm (feat. Elsa Esmeralda)", "EWKMW",
				DurationUtilities.createDuration(0, 3, 30), fileExisting);

		// init the playlists
		syncopatedCity.addTrack(justOneSecond);
		syncopatedCity.addTrack(allTheHellIsBreakingLoose);
		syncopatedCity.addTrack(pointOfNoReturn);
		musicbase.playlistUpdated(syncopatedCity);

		yikes.addTrack(meteorities);
		yikes.addTrack(invisibleWorlds);
		yikes.addTrack(elektricityWillKeepMeWarm);
		musicbase.playlistUpdated(yikes);

		bestTracks.addTrack(justOneSecond);
		bestTracks.addTrack(meteorities);
		musicbase.playlistUpdated(bestTracks);
	}

	private void createCocolinoDeep(BaseMusicbaseModifing musicbase, boolean fileExisting)  {
		// bundle
		cocolinoDeep = TestingDataCreator.bundle(musicbase, "Cocolino deep");

		// playlists
		seventeen = TestingDataCreator.playlist(musicbase, cocolinoDeep, "Seventeen");

		// tracks
		seventeenPartI = TestingDataCreator.track(musicbase, cocolinoDeep, "Seventeen Part1",
				"\"Wherever you go becomes a part of you somehow.\"", "S1",
				DurationUtilities.createDuration(1, 42, 42), fileExisting);

		seventeenPartII = TestingDataCreator.track(musicbase, cocolinoDeep, "Seventeen Part2",
				"\"Wherever you go becomes a part of you somehow.\"", "S2",
				DurationUtilities.createDuration(1, 30, 50), fileExisting);

		seventeenPartIII = TestingDataCreator.track(musicbase, cocolinoDeep, "Seventeen Part3",
				"\"Wherever you go becomes a part of you somehow.\"", "S3",
				DurationUtilities.createDuration(1, 31, 31), fileExisting);

		seventeenPartIV = TestingDataCreator.track(musicbase, cocolinoDeep, "Seventeen Part4",
				"\"Wherever you go becomes a part of you somehow.\"", "S4",
				DurationUtilities.createDuration(1, 26, 53), fileExisting);

		dontForgetToFly = TestingDataCreator.track(musicbase, cocolinoDeep, "Don't forget to fly",
				"Coccolino Deep - Don't forget to fly", "DFtF", DurationUtilities.createDuration(0, 47, 32), fileExisting);

		getLost = TestingDataCreator.track(musicbase, cocolinoDeep, "Get Lost", "Coccolino Deep - Get Lost", "GL",
				DurationUtilities.createDuration(0, 50, 39), fileExisting);

		dancingWithTheElepthant = TestingDataCreator.track(musicbase, cocolinoDeep, "Dancing with Elephant",
				"\"Music is the strongest form of magic.\"", "DwE", DurationUtilities.createDuration(0, 59, 45), fileExisting);

		// init the playlists
		seventeen.addTrack(seventeenPartI);
		seventeen.addTrack(seventeenPartII);
		seventeen.addTrack(seventeenPartIII);
		seventeen.addTrack(seventeenPartIV);
		musicbase.playlistUpdated(seventeen);
	}

	private void createRobick(BaseMusicbaseModifing musicbase, boolean fileExisting) {
		// bundle
		robick = TestingDataCreator.bundle(musicbase, "Robick");

		// tracks
		atZijiDuchove = TestingDataCreator.track(musicbase, robick, "At ziji duchove",
				"Robick feat. MC SPYDA - Ať žijí duchové (DNB Remix)", "AZD",
				DurationUtilities.createDuration(0, 6, 4), fileExisting);

		znamkaPunku = TestingDataCreator.track(musicbase, robick, "Znamka punku",
				"Visaci Zamek: Znamka punku (Robick Remix)", "ZP", DurationUtilities.createDuration(0, 4, 6), fileExisting);

		neniNutno = TestingDataCreator.track(musicbase, robick, "Neni nutno",
				"Robick - Není nutno (feat.Tenor Fly & Top Cat)", "NN", DurationUtilities.createDuration(0, 4, 30), fileExisting);

		ladyCarneval = TestingDataCreator.track(musicbase, robick, "Lady Carneval",
				"Karel Gott Lady Carneval (DnB Remix by Robick)", "LC",
				DurationUtilities.createDuration(0, 4, 11), fileExisting);
	}

	///////////////////////////////////////////////////////////////////////////
	
	@Override
	public void close() throws Exception {
		delete(musicbase);
	}

	private void delete(BaseMusicbaseModifing musicbase)  {
		deleteDaftPunk(musicbase);
		deleteLondonElektricity(musicbase);
		deleteCocolinoDeep(musicbase);
		deleteRobick(musicbase);
		
	}

	private void deleteDaftPunk(BaseMusicbaseModifing musicbase)  {
		musicbase.removeTrack(oneMoreTime);
		musicbase.removeTrack(aerodynamic);
		musicbase.removeTrack(verdisQuo);
		musicbase.removeTrack(getLucky);
		
		musicbase.removePlaylist(discovery);
		musicbase.removePlaylist(randomAccessMemories);
		
		musicbase.removeBundle(daftPunk);
	}
	private void deleteLondonElektricity(BaseMusicbaseModifing musicbase)  {
		musicbase.removeTrack(justOneSecond);
		musicbase.removeTrack(allTheHellIsBreakingLoose);
		musicbase.removeTrack(pointOfNoReturn);
		musicbase.removeTrack(meteorities);
		musicbase.removeTrack(invisibleWorlds);
		musicbase.removeTrack(elektricityWillKeepMeWarm);
		
		musicbase.removePlaylist(syncopatedCity);
		musicbase.removePlaylist(yikes);
		musicbase.removePlaylist(bestTracks);
		
		musicbase.removeBundle(londonElektricity);
	}

	private void deleteCocolinoDeep(BaseMusicbaseModifing musicbase)  {
		musicbase.removeTrack(seventeenPartI);
		musicbase.removeTrack(seventeenPartII);
		musicbase.removeTrack(seventeenPartIII);
		musicbase.removeTrack(seventeenPartIV);
		musicbase.removeTrack(dontForgetToFly);
		musicbase.removeTrack(getLost);
		musicbase.removeTrack(dancingWithTheElepthant);
		
		musicbase.removePlaylist(seventeen);
		
		musicbase.removeBundle(cocolinoDeep);
	}


	private void deleteRobick(BaseMusicbaseModifing musicbase)  {
		musicbase.removeTrack(atZijiDuchove);
		musicbase.removeTrack(znamkaPunku);
		musicbase.removeTrack(neniNutno);
		musicbase.removeTrack(ladyCarneval);
		
		musicbase.removeBundle(robick);
	}

}
