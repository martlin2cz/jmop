package cz.martlin.jmop.gui.dial;

import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.gui.dial.StartBundleDialog.StartBundleData;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Dialog prompting the playlist to start bundle.
 * 
 * @author martin
 *
 */
public class StartBundleDialog extends BaseCommonFXMLDialog<StartBundleData> {

	@FXML
	private ComboBox<String> cmbPlaylistName;

	private final List<String> playlistsNames;
	private final String defaultPlaylistName;

	public StartBundleDialog(List<String> playlistsNames, String defaultPlaylistName) throws IOException {
		super();

		this.playlistsNames = playlistsNames;
		this.defaultPlaylistName = defaultPlaylistName;

		load("/cz/martlin/jmop/gui/fx/StartBundleDialog.fxml");
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle("Start playing bundle");
		setHeaderText("Specify playlist which should be started.");

		setGraphic(
				new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/play-bundle.png"))));
	}

	@Override
	protected void initializeComponents() {
		System.out.println(playlistsNames);
		ObservableList<String> names = FXCollections.observableArrayList(playlistsNames);
		cmbPlaylistName.setItems(names);
		cmbPlaylistName.setValue(defaultPlaylistName);

	}

	///////////////////////////////////////////////////////////////////////////
	@Override
	protected boolean validate() {
		if (cmbPlaylistName.getValue() == null) {
			GuiComplexActionsPerformer.showErrorDialog("Missing value", "Choose playlist");
			return false;
		}

		return true;
	}

	@Override
	protected StartBundleData obtainResultFromInputs() {
		String playlistName = cmbPlaylistName.getValue();

		return new StartBundleData(playlistName);
	}

	///////////////////////////////////////////////////////////////////////////

	public static class StartBundleData {
		private final String playlistName;

		public StartBundleData(String playlistName) {
			super();
			this.playlistName = playlistName;
		}

		public String getPlaylistName() {
			return playlistName;
		}

	}
}
