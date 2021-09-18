package cz.martlin.jmop.gui.control;

import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.comp.JMOPMainMenu;
import cz.martlin.jmop.gui.comp.OperationsPane;
import cz.martlin.jmop.gui.comp.PlayerPane;
import cz.martlin.jmop.gui.comp.PlaylistAndBundlePane;
import cz.martlin.jmop.gui.comp.WelcomePane;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainFrameController implements Initializable, RequiresJMOP {

	@FXML
	private JMOPMainMenu mainMenu;

	@FXML
	private PlayerPane playerPane;
	@FXML
	private WelcomePane welcomePane;

	@FXML
	private OperationsPane operationsPane;
	@FXML
	private PlaylistAndBundlePane playlistAndBundlePane;

	private JMOPPlayer jmop;

	/////////////////////////////////////////////////////////////////////////////////////

	public MainFrameController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// we need to wait for the JMOP
	}

	@Override
	public void setupJMOP(JMOPPlayer jmop, GuiComplexActionsPerformer actions) {
		this.jmop = jmop;

		initBindings();

		mainMenu.setupJMOP(jmop, actions);
		playerPane.setupJMOP(jmop, actions);
	}

	private void initBindings() {
		playerPane.visibleProperty().bind(jmop.getData().inPlayModeProperty());

		welcomePane.visibleProperty().bind(jmop.getData().inPlayModeProperty().not());

		playlistAndBundlePane.visibleProperty().bind(jmop.getData().inPlayModeProperty());
		playlistAndBundlePane.playlistProperty().bind(jmop.getData().playlistProperty());

		Bindings.bindContent(operationsPane.operationsProperty(), jmop.getData().currentOperationsProperty());
	}
	//
	// private void preparingChanged(boolean isSomePreparing) {
	// if (isSomePreparing) {
	// actions.changeCursor(Cursor.WAIT);
	// } else {
	// actions.changeCursor(Cursor.DEFAULT);
	// }
	// }

}
