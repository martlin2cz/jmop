package cz.martlin.jmop.gui.control;

import java.net.URL;
import java.util.ResourceBundle;

import cz.martlin.jmop.core.wrappers.CoreGuiDescriptor;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.comp.JMOPMainMenu;
import cz.martlin.jmop.gui.comp.PlayerPane;
import cz.martlin.jmop.gui.comp.WelcomePane;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

public class MainFrameController implements Initializable, RequiresJMOP {

	@FXML
	private JMOPMainMenu mainMenu;
	@FXML
	private PlayerPane playerPane;
	@FXML
	private WelcomePane welcomePane;

	private CoreGuiDescriptor descriptor;
	private GuiComplexActionsPerformer actions;

	/////////////////////////////////////////////////////////////////////////////////////

	public MainFrameController() {
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// we need to wait for the JMOP
	}
	
	@Override
	public void setupJMOP(JMOPPlayer jmop, CoreGuiDescriptor descriptor, GuiComplexActionsPerformer actions) {
		this.descriptor = descriptor;
		this.actions = actions;

		initBindings();
		
		mainMenu.setupJMOP(jmop, descriptor, actions);
		welcomePane.setupJMOP(jmop, descriptor, actions);
		playerPane.setupJMOP(jmop, descriptor, actions);
	}



	private void initBindings() {
		playerPane.visibleProperty().bind(descriptor.hasActiveBundleAndPlaylistProperty());
		welcomePane.visibleProperty().bind(descriptor.hasActiveBundleAndPlaylistProperty().not());
		
//		descriptor.isPreparpingProperty().addListener((observable, oldVal, newVal) -> preparingChanged(newVal));
	}
//
//	private void preparingChanged(boolean isSomePreparing) {
//		if (isSomePreparing) {
//			actions.changeCursor(Cursor.WAIT);
//		} else {
//			actions.changeCursor(Cursor.DEFAULT);
//		}
//	}

}
