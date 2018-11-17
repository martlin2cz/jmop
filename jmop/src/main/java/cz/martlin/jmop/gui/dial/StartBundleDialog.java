package cz.martlin.jmop.gui.dial;

import java.io.IOException;
import java.util.List;

import cz.martlin.jmop.gui.dial.StartBundleDialog.StartBundleData;
import cz.martlin.jmop.gui.local.Msg;
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

		load("/cz/martlin/jmop/gui/fx/StartBundleDialog.fxml"); //$NON-NLS-1$
	}

	@Override
	protected void specifyCustomDialogSettings() {
		setTitle(Msg.get("Start_playing_bundle")); //$NON-NLS-1$
		setHeaderText(Msg.get("Specify_playlist_which_should_be_started")); //$NON-NLS-1$

		setGraphic(
				new ImageView(new Image(getClass().getResourceAsStream("/cz/martlin/jmop/gui/img/play-bundle.png")))); //$NON-NLS-1$
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
			GuiComplexActionsPerformer.showErrorDialog(Msg.get("Missing_value"), //$NON-NLS-1$
					Msg.get("Choose_playlist")); //$NON-NLS-1$
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
