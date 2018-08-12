package cz.martlin.jmop.gui.dial;

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
		setTitle("About JMOP");

		setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/logo.png"))));

		setHeaderText("JMOP 0.1, (c) m@rtlin, 2018");

		Label content = new Label("" //
				+ "JMOP is open source music player. \n" //
				+ "See more on project GitHub: https://github.com/martlin2cz/jmop . \n" //
		);

		getDialogPane().setContent(content);

	}

}
