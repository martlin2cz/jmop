package cz.martlin.jmop.gui.comp;

import java.io.IOException;

import cz.martlin.jmop.core.data.Playlist;
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

	private final ObjectProperty<Playlist> playlistProperty;

	public PlaylistAndBundlePane() throws IOException {
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
				getClass().getResource("/cz/martlin/jmop/gui/fx/PlaylistAndBundlePane.fxml"));
		loader.setController(this);

		Parent root = loader.load();
		getChildren().addAll(root);
	}

	private void initBindings() {
		playlistProperty.addListener((observable, oldVal, newVal) -> playlistChanged(newVal));
	}

	private void playlistChanged(Playlist newPlaylist) {
		String bundleName = newPlaylist.getBundle().getName();
		lblBundleName.setText(bundleName);

		String playlistName = newPlaylist.getName();
		lblPlaylistName.setText(playlistName);
	}
}
