package cz.martlin.jmop.gui.comp;

import java.io.IOException;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.misc.ObservableListenerBinding;
import cz.martlin.jmop.gui.local.Msg;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class PlaylistAndBundlePane extends VBox {

	@FXML
	private Label lblBundleName;
	@FXML
	private Label lblPlaylistName;

	private final ObservableListenerBinding<Playlist> playlistBinding;
	private final ObjectProperty<Playlist> playlistProperty;

	public PlaylistAndBundlePane() throws IOException {
		this.playlistBinding = new ObservableListenerBinding<>();
		this.playlistProperty = new SimpleObjectProperty<>();

		initialize();
	}

	public ObjectProperty<Playlist> playlistProperty() {
		return playlistProperty;
	}

	private void initialize() throws IOException {
		loadFXML();
		initBindings();

	}

	private void loadFXML() throws IOException {
		FXMLLoader loader = new FXMLLoader(
				getClass().getResource("/cz/martlin/jmop/gui/fx/PlaylistAndBundlePane.fxml")); //$NON-NLS-1$

		loader.setResources(Msg.getResourceBundle());
		loader.setController(this);

		Parent root = loader.load();
		getChildren().addAll(root);
	}

	private void initBindings() {
		playlistProperty.addListener((observable, oldVal, newVal) -> playlistPropertyChanged(oldVal, newVal));
	}

	private void playlistPropertyChanged(Playlist oldPlaylistValue, Playlist newPlaylistValue) {
		// System.out.println("PlaylistAndBundlePane.playlistPropertyChanged()");
		playlistBinding.rebind(oldPlaylistValue, newPlaylistValue, (p) -> playlistChanged((Playlist) p));
		playlistChanged(newPlaylistValue);
	}

	private void playlistChanged(Playlist playlist) {
		Platform.runLater(() -> {
			String bundleName;
			String playlistName;

			if (playlist != null) {
				bundleName = playlist.getBundle().getName();
				playlistName = playlist.getName();
			} else {
				bundleName = "-"; //$NON-NLS-1$
				playlistName = "-"; //$NON-NLS-1$
			}
			lblBundleName.setText(bundleName);
			lblPlaylistName.setText(playlistName);
		});
	}
}
