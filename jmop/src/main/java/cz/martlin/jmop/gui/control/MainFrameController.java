package cz.martlin.jmop.gui.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.player.AbstractPlayer;
import cz.martlin.jmop.core.player.AplayPlayer;
import cz.martlin.jmop.core.player.JMOPPlayerEnvironment;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.sources.local.BaseLocalSource;
import cz.martlin.jmop.gui.util.JMOPDialogs;
import cz.martlin.jmop.gui.util.JavaFXMediaPlayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainFrameController implements Initializable {
	@FXML
	private Button startPlaylistButt;
	@FXML
	private Button newBundleButt;
	@FXML
	private Button savePlaylistButt;
	@FXML
	private Button playButt;
	@FXML
	private Button stopButt;
	@FXML
	private Button nextButt;
	@FXML
	private Button prevButt;

	private final JMOPPlayerEnvironment jmop;
	private AbstractPlayer player;

	/////////////////////////////////////////////////////////////////////////////////////

	public MainFrameController() {
		File rootDirectory = new File("/tmp/jmop-gui");

		// TODO FIXME killme:
		BaseLocalSource local = JMOPPlayerEnvironment.createLocal(rootDirectory);
		player = new JavaFXMediaPlayer(local);
		//player = new AplayPlayer(local); 

		jmop = JMOPPlayerEnvironment.create(rootDirectory, player);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// TODO handle exceptions
	// TODO + run actions as Platform.later

	public void newBundleButtAction() throws JMOPSourceException {
		String bundleName = JMOPDialogs.promptBundleName();
		String querySeed = JMOPDialogs.promptQuery();
		SourceKind kind = JMOPDialogs.promptKind();
		jmop.startNewBundle(kind, bundleName, querySeed);
	}

	public void startPlaylistButtAction() {

		/* TODO here */ }

	public void savePlaylistButtAction() {

		/* TODO here */ }

	public void playButtAction() {
		// TODO FIXME: resume player.play(track);
	}

	public void stopButtAction() {
		player.stop();
	}

	public void nextButtAction() {
		//FIXME crashes jmop.getPlaylister().toNext();
	}

	public void prevButtAction() {
		/* TODO here */ }
	/////////////////////////////////////////////////////////////////////////////////////
}
