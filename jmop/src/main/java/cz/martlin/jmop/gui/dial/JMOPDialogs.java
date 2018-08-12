package cz.martlin.jmop.gui.dial;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;

public class JMOPDialogs {

	public static String promptNewBundleName() {
		return promptTextual("bundle name");
	}

	public static String promptQuery() {
		return promptTextual("query");
	}

	public static SourceKind promptKind() {
		List<SourceKind> values = Arrays.asList(SourceKind.values());
		return promptForChoice("source kind", values);
	}

	public static String promptExistingBundle(JMOPPlayer jmop) throws JMOPSourceException {
		List<String> values = jmop.listBundles(); //TODO order?
		return promptForChoice("bundle", values);
	}

	public static String promptPlaylist(String bundleName, JMOPPlayer jmop) throws JMOPSourceException {
		List<String> values = jmop.listPlaylists(bundleName); //TODO order?
		return promptForChoice("playlist", values);
	}

	public static String promptPlaylistName(JMOPPlayer jmop) {
		return promptTextual("playlist name");
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
