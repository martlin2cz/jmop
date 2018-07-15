package cz.martlin.jmop.gui.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cz.martlin.jmop.core.sources.SourceKind;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public class JMOPDialogs {

	public static String promptBundleName() {
		return promptTextual("bundle name");
	}

	public static String promptQuery() {
		return promptTextual("query");
	}

	public static SourceKind promptKind() {
		List<SourceKind> values = Arrays.asList(SourceKind.values());
		return promptForChoice("source kind", values);
	}
	/////////////////////////////////////////////////////////////////////////////////////
	

	private static <E> E promptForChoice(String title, List<E> values) {
		ChoiceDialog<E> dialog = new ChoiceDialog<>(null, values);
		dialog.setTitle(title);
		dialog.setHeaderText("Plase, choose " + title);
		dialog.setContentText(title + ":");

		Optional<E> result = dialog.showAndWait();
		return result.orElse(null);
	}

	private static String promptTextual(String title) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle(title);
		dialog.setHeaderText("Plase, specify " + title);
		dialog.setContentText(title + ":");

		Optional<String> result = dialog.showAndWait();
		return result.orElse(null);
	}

}
