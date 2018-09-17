package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.control.RequiresJMOP;
import cz.martlin.jmop.gui.util.BindingsUtils;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class PlayerPane extends GridPane implements Initializable, RequiresJMOP {
	@FXML
	private TrackPane trpnCurrentTrack;

	@FXML
	private GuiChangableSlider sliTrackProgress;
	@FXML
	private TwoStateButton playStopButt;
	@FXML
	private TwoStateButton pauseResumeButt;
	@FXML
	private Button nextButt;
	@FXML
	private Button prevButt;
	@FXML
	private TrackPane trpnNextTrack;
	@FXML
	private DownloadPane dwnldPane;
	@FXML
	private PlaylistAndBundlePane playlistAndBundlePane;

	private JMOPPlayer jmop;
	private GuiComplexActionsPerformer actions;
	private ChangeListener<Duration> currentTimeChangeListener;

	public PlayerPane() throws IOException {
		load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// we need to wait for the JMOP
	}

	private void load() throws IOException {
		loadFXML();
	}

	@Override
	public void setupJMOP(JMOPPlayer jmop, GuiComplexActionsPerformer actions) {
		this.jmop = jmop;
		this.actions = actions;

		initBindings();

	}
	///////////////////////////////////////////////////////////////////////////

	private void loadFXML() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/PlayerPane.fxml"));

		loader.setController(this);
		Parent root = loader.load();
		getChildren().addAll(root);
	}

	private void initBindings() {

		trpnCurrentTrack.trackProperty().bind(jmop.getData().currentTrackProperty());
		trpnNextTrack.trackProperty().bind(jmop.getData().nextTrackProperty());

		playlistAndBundlePane.playlistProperty().bind(jmop.getData().playlistProperty());
		Bindings.bindContent(dwnldPane.tasksProperty(), jmop.getData().currentDownloadTasksProperty());

		playStopButt.firstStateProperty().bind(jmop.getData().stoppedProperty());
		pauseResumeButt.firstStateProperty().bind(jmop.getData().pausedProperty());
		pauseResumeButt.disableProperty().bind(jmop.getData().stoppedProperty());
		prevButt.disableProperty().bind(jmop.getData().hasPreviousProperty().not());
		nextButt.disableProperty().bind(jmop.getData().hasNextProperty().not());

		jmop.getData().stoppedProperty().addListener((observable, oldVal, newVal) -> changeDefaultButton());

		jmop.getData().currentTrackProperty().addListener((observable, oldVal, newVal) -> trackToSliderMax(newVal));
		sliTrackProgress.guiChangingProperty()
				.addListener((observable, oldVal, newVal) -> sliderGuiChangingChanged(newVal));
		sliTrackProgress.disableProperty().bind(jmop.getData().stoppedProperty());

		currentTimeChangeListener = (obs, oldv, newv) -> //
		sliTrackProgress.valueProperty().set(BindingsUtils.durationToMilis(newv));

		bindPlayerCurrentTimeToSlider();
	}

	///////////////////////////////////////////////////////////////////////////

	private void sliderGuiChangingChanged(boolean isGuiChanging) {
		DoubleProperty property = sliTrackProgress.valueProperty();

		if (isGuiChanging) {
			unbindPlayerCurrentTimeFromSlider(property);
		} else {
			Duration duration = BindingsUtils.milisToDuration(property.get());
			actions.seek(duration);

			bindPlayerCurrentTimeToSlider();
		}
	}

	private void unbindPlayerCurrentTimeFromSlider(DoubleProperty property) {
		// FIXME binding not working :-O
		jmop.getData().currentTimeProperty().removeListener(currentTimeChangeListener);
	}

	private void bindPlayerCurrentTimeToSlider() {
		// FIXME binding not working :-O
		jmop.getData().currentTimeProperty().addListener(currentTimeChangeListener);

	}

	private void trackToSliderMax(Track track) {
		double milis;
		
		if (track != null) {
			Duration duration = track.getDuration();
			milis = BindingsUtils.durationToMilis(duration);
		} else {
			milis = 0.0;
		}

		sliTrackProgress.setMax(milis);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void changeDefaultButton() {
		boolean isStopped = jmop.getData().stoppedProperty().get();
		boolean isPlaying = !isStopped;

		playStopButt.setDefaultButton(isStopped);
		pauseResumeButt.setDefaultButton(isPlaying);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////
	//
	// public void showPlaylistButtAction() {
	// actions.showPlaylist();
	// }

	public void newBundleButtAction() {
		actions.startNewBundle();
	}

	public void startPlaylistButtAction() {
		actions.startPlaylist();
	}

	public void newPlaylistButtAction() {
		actions.newPlaylist();
	}

	public void savePlaylistButtAction() {
		actions.savePlaylist();
	}

	public void playButtAction() {
		actions.playButtAction();
	}

	public void stopButtAction() {
		actions.stopButtAction();
	}

	public void pauseButtAction() {
		actions.pauseButtAction();
	}

	public void resumeButtAction() {
		actions.resumeButtAction();
	}

	public void nextButtAction() {
		actions.nextButtAction();
	}

	public void prevButtAction() {
		actions.prevButtAction();
	}

}
