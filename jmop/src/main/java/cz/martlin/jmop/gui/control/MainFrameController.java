package cz.martlin.jmop.gui.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.core.wrappers.JMOPPlayerBuilder;
import cz.martlin.jmop.gui.DownloadGuiReporter;
import cz.martlin.jmop.gui.comp.DownloadPane;
import cz.martlin.jmop.gui.comp.GuiChangableSlider;
import cz.martlin.jmop.gui.comp.JMOPMainMenu;
import cz.martlin.jmop.gui.comp.PlaylistAndBundlePane;
import cz.martlin.jmop.gui.comp.TrackPane;
import cz.martlin.jmop.gui.comp.TwoStateButton;
import cz.martlin.jmop.gui.util.BindingsUtils;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import cz.martlin.jmop.gui.util.MediaPlayerGuiReporter;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MainFrameController implements Initializable, GuiDescriptor {

	@FXML
	private Pane playerPane;
	@FXML
	private BorderPane wellcomePane;
	
	@FXML
	private Button showPlaylistButt;
	@FXML
	private Button startPlaylistButt;
	@FXML
	private Button newBundleButt;
	@FXML
	private Button newPlaylistButt;
	@FXML
	private Button savePlaylistButt;
	@FXML
	private TwoStateButton playStopButt;
	@FXML
	private TwoStateButton pauseResumeButt;
	@FXML
	private GuiChangableSlider sliTrackProgress;
	@FXML
	private JMOPMainMenu mainMenu;

	// @FXML
	// private Button playButt;
	// @FXML
	// private Button stopButt;
	// @FXML
	// private Button pauseButt;
	// @FXML
	// private Button resumeButt;
	@FXML
	private Button nextButt;
	@FXML
	private Button prevButt;
	// @FXML
	// @Deprecated
	// private Label lblTrackName;
	// @FXML
	// @Deprecated
	// private Label lblDuration;
	// @Deprecated
	// @FXML
	// private Label lblProgressText;
	// @Deprecated
	// @FXML
	// private ProgressBar progressBar;
	@FXML
	private TrackPane trpnCurrentTrack;
	@FXML
	private TrackPane trpnPreviousTrack;
	@FXML
	private TrackPane trpnNextTrack;
	@FXML
	private PlaylistAndBundlePane playlistAndBundlePane;
	@FXML
	private DownloadPane dwnldPane;
	

	private final JMOPPlayer jmop;
	private final GuiComplexActionsPerformer actions;
	private ChangeListener<Duration> currentTimeChangeListener;
	/////////////////////////////////////////////////////////////////////////////////////

	public MainFrameController() {
		File rootDirectory = new File("/tmp/jmop-gui");

		this.jmop = JMOPPlayerBuilder.create(this, rootDirectory, null);
		this.actions = new GuiComplexActionsPerformer(jmop);
	}
	
	public GuiComplexActionsPerformer getActions() {
		return actions;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		mainMenu.jmopProperty().set(jmop);

		initBindings();

		// XXX debug:
		// nextButt.setGraphic(new ImageView(new
		// Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/next.svg"))));
	}

	private void initBindings() {
		playerPane.visibleProperty().bind(jmop.getDescriptor().hasActiveBundleAndPlaylistProperty());
		wellcomePane.visibleProperty().bind(jmop.getDescriptor().hasActiveBundleAndPlaylistProperty().not());
		
		
		trpnCurrentTrack.trackProperty().bind(jmop.getDescriptor().currentTrackProperty());
		trpnPreviousTrack.trackProperty().bind(jmop.getDescriptor().previousTrackProperty());
		trpnNextTrack.trackProperty().bind(jmop.getDescriptor().nextTrackProperty());

		playlistAndBundlePane.playlistProperty().bind(jmop.getDescriptor().currentPlaylistProperty());
		//dwnldPane.taskProperty().bind(jmop.getDescriptor().currentDownloadTaskProperty());
		Bindings.bindContent(dwnldPane.tasksProperty(), jmop.getDescriptor().currentDownloadTasksProperty());

		playStopButt.firstStateProperty().bind(jmop.getDescriptor().stoppedProperty());
		pauseResumeButt.firstStateProperty().bind(jmop.getDescriptor().pausedProperty());
		pauseResumeButt.disableProperty().bind(jmop.getDescriptor().stoppedProperty());

		jmop.getDescriptor().stoppedProperty().addListener((observable, oldVal, newVal) -> changeDefaultButton());

		jmop.getDescriptor().currentTrackProperty()
				.addListener((observable, oldVal, newVal) -> trackToSliderMax(newVal));
		sliTrackProgress.guiChangingProperty()
				.addListener((observable, oldVal, newVal) -> sliderGuiChangingChanged(newVal));
		sliTrackProgress.disableProperty().bind(jmop.getDescriptor().stoppedProperty());

		currentTimeChangeListener = (obs, oldv, newv) -> //
		sliTrackProgress.valueProperty().set(BindingsUtils.durationToMilis(newv));

		bindPlayerCurrentTimeToSlider();

		// jmop.getDescriptor().currentTimeProperty().addListener((o, ol, nw) ->
		// System.out.println(nw));
	}

	private void sliderGuiChangingChanged(boolean isGuiChanging) {
		DoubleProperty property = sliTrackProgress.valueProperty();

		if (isGuiChanging) {
			unbindPlayerCurrentTimeFromSlider(property);
		} else {
			Duration duration = BindingsUtils.milisToDuration(property.get());
			jmop.seek(duration);

			bindPlayerCurrentTimeToSlider();
		}
	}

	private void unbindPlayerCurrentTimeFromSlider(DoubleProperty property) {
		// FIXME binding not working :-O
		// property.unbind();

		jmop.getDescriptor().currentTimeProperty().removeListener(currentTimeChangeListener);
	}

	private void bindPlayerCurrentTimeToSlider() {
		// FIXME binding not working :-O
		// DoubleBinding binding = new
		// DurationToDoubleMilisBinding(jmop.getDescriptor().currentTimeProperty());
		// sliTrackProgress.valueProperty().bind(binding);

		jmop.getDescriptor().currentTimeProperty().addListener(currentTimeChangeListener);

	}

	private void trackToSliderMax(Track track) {
		Duration duration = track.getDuration();
		double milis = BindingsUtils.durationToMilis(duration);
		sliTrackProgress.setMax(milis);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private void changeDefaultButton() {
		boolean isStopped = jmop.getDescriptor().stoppedProperty().get();
		boolean isPlaying = !isStopped;

		playStopButt.setDefaultButton(isStopped);
		pauseResumeButt.setDefaultButton(isPlaying);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void showPlaylistButtAction() {
		actions.showPlaylist();
	}

	public void newBundleButtAction() {
		actions.startNewBundle();
	}

	public void startPlaylistButtAction() {
		actions.startPlaylist();
	}

	// TODO create new playlist in brand new bundle?

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

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public ProgressListener getProgressListener() {
		return (p) -> /* progressBar.setProgress(p / 100) */ {
		};
	}

	@Override
	public MediaPlayerGuiReporter getMediaPlayerGuiReporter() {
		return new MediaPlayerGuiReporter() {

			@Override
			public StringProperty trackNameProperty() {
				return new SimpleStringProperty();
			}

			@Override
			public Property<Status> statusProperty() {

				return new SimpleObjectProperty<>(); // TODO
			}

			@Override
			public Property<Duration> durationProperty() {
				return new SimpleObjectProperty<>(); // TODO
			}
		};
	}

	@Override
	public DownloadGuiReporter getDownloadGuiReporter() {
		return new DownloadGuiReporter() {

			@Override
			public StringProperty statusProperty() {
				return new SimpleStringProperty();
			}

			@Override
			public BooleanProperty runningProperty() {
				return new SimpleBooleanProperty();
			}

			@Override
			public DoubleProperty progressProperty() {
				return new SimpleDoubleProperty();
			}
		};
	}
}
