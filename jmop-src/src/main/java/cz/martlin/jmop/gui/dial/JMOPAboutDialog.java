package cz.martlin.jmop.gui.dial;

import cz.martlin.jmop.gui.local.Msg;
import cz.martlin.jmop.mains.JMOPGUIApp;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JMOPAboutDialog extends Dialog<Void> {

	public JMOPAboutDialog() {
		super();
		initialize();
	}

	private void initialize() {
		setTitle(Msg.get("About_JMOP")); //$NON-NLS-1$

		setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/logo.png")))); //$NON-NLS-1$

		setHeaderText("JMOP " + JMOPGUIApp.VERSION + ", (c) m@rtlin, 2018"); //$NON-NLS-1$

		Label content = new Label("" // //$NON-NLS-1$
				+ Msg.get("JMOP_is_open_source_music_player") // //$NON-NLS-1$
				+ Msg.get("See_more_on_project_github") // //$NON-NLS-1$
		);

		getDialogPane().setContent(content);

		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

	}

}
