package cz.martlin.jmop.gui.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.core.wrappers.JMOPPlayerBuilder;
import cz.martlin.jmop.gui.DownloadGuiReporter;
import cz.martlin.jmop.gui.comp.DownloadPane;
import cz.martlin.jmop.gui.comp.TrackPane;
import cz.martlin.jmop.gui.comp.TwoStateButton;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import cz.martlin.jmop.gui.util.MediaPlayerGuiReporter;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MainFrameController implements Initializable, GuiDescriptor {
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
	private DownloadPane dwnldPane;

	// TODO try to use menubar :)

	private final JMOPPlayer jmop;
	/////////////////////////////////////////////////////////////////////////////////////

	public MainFrameController() {
		File rootDirectory = new File("/tmp/jmop-gui");

		this.jmop = JMOPPlayerBuilder.create(this, rootDirectory, null);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initBindings();
	}

	private void initBindings() {
		trpnCurrentTrack.trackProperty().bind(jmop.getDescriptor().currentTrackProperty());
		trpnPreviousTrack.trackProperty().bind(jmop.getDescriptor().previousTrackProperty());
		trpnNextTrack.trackProperty().bind(jmop.getDescriptor().nextTrackProperty());

		dwnldPane.taskProperty().bind(jmop.getDescriptor().currentDownloadTaskProperty());

		playStopButt.firstStateProperty().bind(jmop.getDescriptor().stoppedProperty());
		pauseResumeButt.firstStateProperty().bind(jmop.getDescriptor().pausedProperty());
		pauseResumeButt.disableProperty().bind(jmop.getDescriptor().stoppedProperty());

		stopButt.disableProperty().bind(jmop.getDescriptor().stoppedProperty());
		playButt.disableProperty().bind(jmop.getDescriptor().stoppedProperty().not());

		pauseButt.disableProperty()
				.bind(jmop.getDescriptor().pausedProperty().or(jmop.getDescriptor().stoppedProperty()));
		resumeButt.disableProperty()
				.bind(jmop.getDescriptor().pausedProperty().not().or(jmop.getDescriptor().stoppedProperty()));

	}

	/////////////////////////////////////////////////////////////////////////////////////
	public void showPlaylistButtAction() {
		GuiComplexActionsPerformer.showPlaylist(jmop);
	}

	public void newBundleButtAction() {
		GuiComplexActionsPerformer.startNewBundle(jmop);
	}

	public void startPlaylistButtAction() {
		GuiComplexActionsPerformer.startPlaylist(jmop);
	}

	// TODO create new playlist in brand new bundle?

	public void newPlaylistButtAction() {
		GuiComplexActionsPerformer.newPlaylist(jmop);
	}

	public void savePlaylistButtAction() {
		GuiComplexActionsPerformer.savePlaylist(jmop);
	}

	public void playButtAction() {
		jmop.startPlaying();
	}

	public void stopButtAction() {
		jmop.stopPlaying();
	}

	public void pauseButtAction() {
		jmop.pausePlaying();
	}

	public void resumeButtAction() {
		jmop.resumePlaying();
	}

	public void nextButtAction() {
		jmop.toNext();
	}

	public void prevButtAction() {
		jmop.toPrevious();
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
