package cz.martlin.jmop.common.testing.testdata;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.common.data.model.Playlist;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * Abstract superclass. Use one of: {@link SimpleTestingMusicdata},
 * {@link TestingMusicdataWithMusicbase} or {@link TestingMusicdataWithStorage}.
 * 
 * @author martin
 *
 */
public abstract class AbstractTestingMusicdata implements AutoCloseable {

	/**
	 * May create the tracks files?
	 */
	private final boolean fileExisting;
	
	/**
	 * Contains playlist {@link #discovery}, {@link #randomAccessMemories}. Contains
	 * tracks {@link #oneMoreTime}, {@link #aerodynamic}, {@link #verdisQuo} and
	 * {@link #getLucky}.
	 */
	public Bundle daftPunk;
	/**
	 * Contains tracks {@link #oneMoreTime}, {@link #aerodynamic} and
	 * {@link #verdisQuo}.
	 */
	public Playlist discovery;
	/**
	 * Contains only {@link #getLucky} track.
	 */
	public Playlist randomAccessMemories;
	public Track oneMoreTime;
	public Track aerodynamic;
	public Track verdisQuo;
	public Track getLucky;

	/**
	 * Contains tracks {@link #justOneSecond}, {@link #allTheHellIsBreakingLoose},
	 * {@link #pointOfNoReturn}, {@link #meteorities}, {@link #invisibleWorlds},
	 * {@link #elektricityWillKeepMeWarm}. Contains playlists
	 * {@link #syncopatedCity}, {@link #yikes} and {@link #bestTracks}.
	 */
	public Bundle londonElektricity;
	/**
	 * Contains tracks {@link #justOneSecond}, {@link #allTheHellIsBreakingLoose}
	 * and {@link #pointOfNoReturn}.
	 */
	public Playlist syncopatedCity;
	/**
	 * Contains tracks {@link #meteorities}, {@link #invisibleWorlds} and
	 * {@link #elektricityWillKeepMeWarm}.
	 */
	public Playlist yikes;
	/**
	 * Contains tracks {@link #justOneSecond} and {@link #meteorities}.
	 */
	public Playlist bestTracks;
	public Track justOneSecond;
	public Track allTheHellIsBreakingLoose;
	public Track pointOfNoReturn;
	public Track meteorities;
	public Track invisibleWorlds;
	public Track elektricityWillKeepMeWarm;

	/**
	 * Contains playlist {@link #seventeen} and tracks {@link #seventeenPartI},
	 * {@link #seventeenPartII}, {@link #seventeenPartIII}, {@link #seventeenPartIV}
	 * and {@link #dontForgetToFly}, {@link #getLost} and
	 * {@link #dancingWithTheElepthant}.
	 */
	public Bundle cocolinoDeep;
	/**
	 * Contains tracks {@link #seventeenPartI}, {@link #seventeenPartII},
	 * {@link #seventeenPartIII}, {@link #seventeenPartIV}.
	 */
	public Playlist seventeen;
	public Track seventeenPartI;
	public Track seventeenPartII;
	public Track seventeenPartIII;
	public Track seventeenPartIV;
	public Track dontForgetToFly;
	public Track getLost;
	public Track dancingWithTheElepthant;

	/**
	 * Contains no playlist, but tracks: {@link #atZijiDuchove},
	 * {@link #znamkaPunku}, {@link #neniNutno} and {@link #ladyCarneval}.
	 */
	public Bundle robick;
	public Track atZijiDuchove;
	public Track znamkaPunku;
	public Track neniNutno;
	public Track ladyCarneval;

	public AbstractTestingMusicdata() {
		super();
		
		fileExisting = false;
	}

	public AbstractTestingMusicdata(boolean fileExisting) {
		super();
		
		this.fileExisting = fileExisting;
	}

	protected void prepare() {
		createDaftPunk();

		createLondonElektricity();

		createCocolinoDeep();

		createRobick();

	}

	protected void createDaftPunk() {
		// bundle
		daftPunk = createTheBundle("Daft Punk");

		// playlists
		discovery = createThePlaylist(daftPunk, "Discovery");
		randomAccessMemories = createThePlaylist(daftPunk, "Random Acess Memories");

		// tracks
		oneMoreTime = createTheTrack(daftPunk, "One More Time", "One More Time (featuring Romanthony)", "OMT",
				DurationUtilities.createDuration(0, 5, 20), fileExisting);
		aerodynamic = createTheTrack(daftPunk, "Aerodynamic", "Aerodynamic", "AD",
				DurationUtilities.createDuration(0, 3, 27), fileExisting);
		verdisQuo = createTheTrack(daftPunk, "Veridis Quo", "Veridis Quo", "VQ",
				DurationUtilities.createDuration(0, 5, 44), fileExisting);
		getLucky = createTheTrack(daftPunk, "Get Lucky", "Get Lucky (featuring Pharrell Williams)", "GL",
				DurationUtilities.createDuration(0, 6, 8), fileExisting);

		// init the playlists
		discovery.addTrack(oneMoreTime);
		discovery.addTrack(aerodynamic);
		discovery.addTrack(verdisQuo);
		
		randomAccessMemories.addTrack(getLucky);
		
		// mark something played
		daftPunk.played(DurationUtilities.createDuration(0, 14, 8));
		
		discovery.played(DurationUtilities.createDuration(0, 13, 00));
		discovery.played(DurationUtilities.createDuration(0, 0, 47));

		oneMoreTime.played(DurationUtilities.createDuration(0, 0, 1));
		oneMoreTime.played(DurationUtilities.createDuration(0, 0, 2));
		oneMoreTime.played(DurationUtilities.createDuration(0, 0, 3));
		
		aerodynamic.played(aerodynamic.getDuration());
		aerodynamic.played(aerodynamic.getDuration());
		
		verdisQuo.played(verdisQuo.getDuration());
		
		randomAccessMemories.played(DurationUtilities.createDuration(0, 0, 21));
		getLucky.played(DurationUtilities.createDuration(0, 0, 21));
		
	}

	protected void createLondonElektricity() {
		// bundle
		londonElektricity = createTheBundle("London Elektricity");

		// playlists
		syncopatedCity = createThePlaylist(londonElektricity, "Syncopated City");
		yikes = createThePlaylist(londonElektricity, "Yikes!");
		bestTracks = createThePlaylist(londonElektricity, "best tracks");

		// tracks
		justOneSecond = createTheTrack(londonElektricity, "Just One Second", "2. Just One Second", "JOS",
				DurationUtilities.createDuration(0, 5, 39), fileExisting);

		allTheHellIsBreakingLoose = createTheTrack(londonElektricity, "All Hell Is Breaking Loose",
				"6. All Hell Is Breaking Loose", "AtHBL", DurationUtilities.createDuration(0, 4, 44), fileExisting);

		pointOfNoReturn = createTheTrack(londonElektricity, "Point of No Return", "7. Point of No Return", "PoNR",
				DurationUtilities.createDuration(0, 5, 52), fileExisting);

		meteorities = createTheTrack(londonElektricity, "Meteorites", "2. Meteorites (feat. Elsa Esmeralda)", "M",
				DurationUtilities.createDuration(0, 6, 2), fileExisting);

		invisibleWorlds = createTheTrack(londonElektricity, "Invisible Worlds",
				"Invisible Worlds (feat. Elsa Esmeralda)", "IW", DurationUtilities.createDuration(0, 6, 45),
				fileExisting);

		elektricityWillKeepMeWarm = createTheTrack(londonElektricity, "Elektricity Will Keep Me Warm",
				"1. Elektricity Will Keep Me Warm (feat. Elsa Esmeralda)", "EWKMW",
				DurationUtilities.createDuration(0, 3, 30), fileExisting);

		// init the playlists
		syncopatedCity.addTrack(justOneSecond);
		syncopatedCity.addTrack(allTheHellIsBreakingLoose);
		syncopatedCity.addTrack(pointOfNoReturn);

		yikes.addTrack(meteorities);
		yikes.addTrack(invisibleWorlds);
		yikes.addTrack(elektricityWillKeepMeWarm);

		bestTracks.addTrack(justOneSecond);
		bestTracks.addTrack(meteorities);
	}

	protected void createCocolinoDeep() {
		// bundle
		cocolinoDeep = createTheBundle("Cocolino deep");

		// playlists
		seventeen = createThePlaylist(cocolinoDeep, "Seventeen");

		// tracks
		seventeenPartI = createTheTrack(cocolinoDeep, "Seventeen Part1",
				"\"Wherever you go becomes a part of you somehow.\"", "S1", DurationUtilities.createDuration(1, 42, 42),
				fileExisting);

		seventeenPartII = createTheTrack(cocolinoDeep, "Seventeen Part2",
				"\"Wherever you go becomes a part of you somehow.\"", "S2", DurationUtilities.createDuration(1, 30, 50),
				fileExisting);

		seventeenPartIII = createTheTrack(cocolinoDeep, "Seventeen Part3",
				"\"Wherever you go becomes a part of you somehow.\"", "S3", DurationUtilities.createDuration(1, 31, 31),
				fileExisting);

		seventeenPartIV = createTheTrack(cocolinoDeep, "Seventeen Part4",
				"\"Wherever you go becomes a part of you somehow.\"", "S4", DurationUtilities.createDuration(1, 26, 53),
				fileExisting);

		dontForgetToFly = createTheTrack(cocolinoDeep, "Don't forget to fly", "Coccolino Deep - Don't forget to fly",
				"DFtF", DurationUtilities.createDuration(0, 47, 32), fileExisting);

		getLost = createTheTrack(cocolinoDeep, "Get Lost", "Coccolino Deep - Get Lost", "GL",
				DurationUtilities.createDuration(0, 50, 39), fileExisting);

		dancingWithTheElepthant = createTheTrack(cocolinoDeep, "Dancing with Elephant",
				"\"Music is the strongest form of magic.\"", "DwE", DurationUtilities.createDuration(0, 59, 45),
				fileExisting);

		// init the playlists
		seventeen.addTrack(seventeenPartI);
		seventeen.addTrack(seventeenPartII);
		seventeen.addTrack(seventeenPartIII);
		seventeen.addTrack(seventeenPartIV);
	}

	protected void createRobick() {
		// bundle
		robick = createTheBundle("Robick");

		// tracks
		atZijiDuchove = createTheTrack(robick, "At ziji duchove", "Robick feat. MC SPYDA - Ať žijí duchové (DNB Remix)",
				"AZD", DurationUtilities.createDuration(0, 6, 4), fileExisting);

		znamkaPunku = createTheTrack(robick, "Znamka punku", "Visaci Zamek: Znamka punku (Robick Remix)", "ZP",
				DurationUtilities.createDuration(0, 4, 6), fileExisting);

		neniNutno = createTheTrack(robick, "Neni nutno", "Robick - Není nutno (feat.Tenor Fly & Top Cat)", "NN",
				DurationUtilities.createDuration(0, 4, 30), fileExisting);

		ladyCarneval = createTheTrack(robick, "Lady Carneval", "Karel Gott Lady Carneval (DnB Remix by Robick)", "LC",
				DurationUtilities.createDuration(0, 4, 11), fileExisting);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void close() throws Exception {
		delete();
	}

	protected void delete() {
		deleteDaftPunk();
		deleteLondonElektricity();
		deleteCocolinoDeep();
		deleteRobick();

	}

	protected void deleteDaftPunk() {
		oneMoreTime = deleteTheTrack(oneMoreTime);
		aerodynamic = deleteTheTrack(aerodynamic);
		verdisQuo = deleteTheTrack(verdisQuo);
		getLucky = deleteTheTrack(getLucky);

		discovery = deleteThePlaylist(discovery);
		randomAccessMemories = deleteThePlaylist(randomAccessMemories);

		daftPunk = deleteTheBundle(daftPunk);
	}

	protected void deleteLondonElektricity() {
		justOneSecond = deleteTheTrack(justOneSecond);
		allTheHellIsBreakingLoose = deleteTheTrack(allTheHellIsBreakingLoose);
		pointOfNoReturn = deleteTheTrack(pointOfNoReturn);
		meteorities = deleteTheTrack(meteorities);
		invisibleWorlds = deleteTheTrack(invisibleWorlds);
		elektricityWillKeepMeWarm = deleteTheTrack(elektricityWillKeepMeWarm);

		syncopatedCity = deleteThePlaylist(syncopatedCity);
		yikes = deleteThePlaylist(yikes);
		bestTracks = deleteThePlaylist(bestTracks);

		londonElektricity = deleteTheBundle(londonElektricity);
	}

	protected void deleteCocolinoDeep() {
		seventeenPartI = deleteTheTrack(seventeenPartI);
		seventeenPartII = deleteTheTrack(seventeenPartII);
		seventeenPartIII = deleteTheTrack(seventeenPartIII);
		seventeenPartIV = deleteTheTrack(seventeenPartIV);

		dontForgetToFly = deleteTheTrack(dontForgetToFly);
		getLost = deleteTheTrack(getLost);
		dancingWithTheElepthant = deleteTheTrack(dancingWithTheElepthant);

		seventeen = deleteThePlaylist(seventeen);

		cocolinoDeep = deleteTheBundle(cocolinoDeep);
	}

	protected void deleteRobick() {
		atZijiDuchove = deleteTheTrack(atZijiDuchove);
		znamkaPunku = deleteTheTrack(znamkaPunku);
		neniNutno = deleteTheTrack(neniNutno);
		ladyCarneval = deleteTheTrack(ladyCarneval);

		robick = deleteTheBundle(robick);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	protected abstract Bundle createTheBundle(String name);

	protected abstract Playlist createThePlaylist(Bundle bundle, String name);

	protected abstract Track createTheTrack(Bundle bundle, String title, String description, String id,
			Duration duration, boolean fileExisting);

	protected abstract Track deleteTheTrack(Track track);

	protected abstract Playlist deleteThePlaylist(Playlist playlist);

	protected abstract Bundle deleteTheBundle(Bundle bundle);

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "AbstractTestingMusicdata [fileExisting=" + fileExisting + ", daftPunk=" + daftPunk + ", discovery="
				+ discovery + ", randomAccessMemories=" + randomAccessMemories + ", oneMoreTime=" + oneMoreTime
				+ ", aerodynamic=" + aerodynamic + ", verdisQuo=" + verdisQuo + ", getLucky=" + getLucky
				+ ", londonElektricity=" + londonElektricity + ", syncopatedCity=" + syncopatedCity + ", yikes=" + yikes
				+ ", bestTracks=" + bestTracks + ", justOneSecond=" + justOneSecond + ", allTheHellIsBreakingLoose="
				+ allTheHellIsBreakingLoose + ", pointOfNoReturn=" + pointOfNoReturn + ", meteorities=" + meteorities
				+ ", invisibleWorlds=" + invisibleWorlds + ", elektricityWillKeepMeWarm=" + elektricityWillKeepMeWarm
				+ ", cocolinoDeep=" + cocolinoDeep + ", seventeen=" + seventeen + ", seventeenPartI=" + seventeenPartI
				+ ", seventeenPartII=" + seventeenPartII + ", seventeenPartIII=" + seventeenPartIII
				+ ", seventeenPartIV=" + seventeenPartIV + ", dontForgetToFly=" + dontForgetToFly + ", getLost="
				+ getLost + ", dancingWithTheElepthant=" + dancingWithTheElepthant + ", robick=" + robick
				+ ", atZijiDuchove=" + atZijiDuchove + ", znamkaPunku=" + znamkaPunku + ", neniNutno=" + neniNutno
				+ ", ladyCarneval=" + ladyCarneval + "]";
	}

}