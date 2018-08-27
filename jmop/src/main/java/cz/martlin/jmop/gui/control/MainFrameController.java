package cz.martlin.jmop.gui.control;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.core.wrappers.CoreGuiDescriptor;
import cz.martlin.jmop.core.wrappers.GuiDescriptor;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.core.wrappers.JMOPPlayerBuilder;
import cz.martlin.jmop.gui.DownloadGuiReporter;
import cz.martlin.jmop.gui.comp.DownloadPane;
import cz.martlin.jmop.gui.comp.GuiChangableSlider;
import cz.martlin.jmop.gui.comp.JMOPMainMenu;
import cz.martlin.jmop.gui.comp.PlayerPane;
import cz.martlin.jmop.gui.comp.PlaylistAndBundlePane;
import cz.martlin.jmop.gui.comp.TrackPane;
import cz.martlin.jmop.gui.comp.TwoStateButton;
import cz.martlin.jmop.gui.comp.WelcomePane;
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
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

public class MainFrameController implements Initializable, RequiresJMOP {

	@FXML
	private JMOPMainMenu mainMenu;
	@FXML
	private PlayerPane playerPane;
	@FXML
	private WelcomePane welcomePane;

	private CoreGuiDescriptor descriptor;

	/////////////////////////////////////////////////////////////////////////////////////

	public MainFrameController() {
		File rootDirectory = new File("/tmp/jmop-gui");

		JMOPPlayer jmop = null;
		JMOPPlayerBuilder.create(null, rootDirectory, null);
		new GuiComplexActionsPerformer(jmop);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// we need to wait for the JMOP
	}
	
	@Override
	public void setupJMOP(JMOPPlayer jmop, CoreGuiDescriptor descriptor, GuiComplexActionsPerformer actions) {
		this.descriptor = descriptor;

		initBindings();
		
		mainMenu.setupJMOP(jmop, descriptor, actions);
		playerPane.setupJMOP(jmop, descriptor, actions);
	}



	private void initBindings() {
		playerPane.visibleProperty().bind(descriptor.hasActiveBundleAndPlaylistProperty());
		welcomePane.visibleProperty().bind(descriptor.hasActiveBundleAndPlaylistProperty().not());
	}

}
