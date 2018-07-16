package cz.martlin.jmop.gui.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.core.wrappers.JMOPPlayerBuilder;
import cz.martlin.jmop.gui.util.JMOPDialogs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

public class MainFrameController implements Initializable, GuiDescriptor { 
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
	private Button pauseButt;
	@FXML
	private Button resumeButt;
	@FXML
	private Button nextButt;
	@FXML
	private Button prevButt;
	@FXML
	private ProgressBar progressBar;

	private final JMOPPlayer jmop;
	/////////////////////////////////////////////////////////////////////////////////////

	public MainFrameController() {
		File rootDirectory = new File("/tmp/jmop-gui");

		this.jmop = JMOPPlayerBuilder.create(this, rootDirectory, null); 

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	/////////////////////////////////////////////////////////////////////////////////////
	// TODO handle exceptions
	// TODO + run actions as Platform.later

	public void newBundleButtAction() throws JMOPSourceException {
		String bundleName = JMOPDialogs.promptNewBundleName();
		String querySeed = JMOPDialogs.promptQuery();
		SourceKind kind = JMOPDialogs.promptKind();
		jmop.startNewBundle(kind, bundleName, querySeed);
	}

	public void startPlaylistButtAction() throws JMOPSourceException {
		String bundleName = JMOPDialogs.promptExistingBundle(jmop);
		String playlistName = JMOPDialogs.promptPlaylist(bundleName, jmop);
		jmop.startPlaylist(bundleName, playlistName);
	}

	public void savePlaylistButtAction() throws JMOPSourceException {
		String playlistName = JMOPDialogs.promptPlaylistName(jmop);
		jmop.savePlaylistAs(playlistName);
	}

	public void playButtAction() throws JMOPSourceException {
		jmop.startPlaying();
	}

	public void stopButtAction() throws JMOPSourceException {
		jmop.stopPlaying();
	}

	public void pauseButtAction() throws JMOPSourceException {
		jmop.pausePlaying();
	}

	public void resumeButtAction() throws JMOPSourceException {
		jmop.resumePlaying();
	}

	public void nextButtAction() throws JMOPSourceException {
		jmop.toNext();
	}

	public void prevButtAction() throws JMOPSourceException {
		jmop.toPrevious();
	}
	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public ProgressListener getProgressListener() {
		return (p) -> progressBar.setProgress(p / 100);
	}
}
