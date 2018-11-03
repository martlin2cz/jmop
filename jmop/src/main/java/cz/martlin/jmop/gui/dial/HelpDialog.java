package cz.martlin.jmop.gui.dial;

import java.net.URL;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class HelpDialog extends Dialog<Void> {

	public HelpDialog() {
		super();
		initialize();
	}

	private void initialize() {
		setTitle("Help");

		setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/help.png"))));

		setHeaderText("JMOP Help");
		setResizable(true);

		Control content = createContent();
		getDialogPane().setContent(content);

		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);

	}

	private ScrollPane createContent() {
		WebView webpage = new WebView();
		WebEngine engine = webpage.getEngine();
		String helpName = "help.html";

		URL helpFile = getClass().getResource("/cz/martlin/jmop/gui/help/" + helpName);
		String helpURL = helpFile.toExternalForm();
		engine.load(helpURL);

		ScrollPane scrolls = new ScrollPane(webpage);
		return scrolls;
	}

}
