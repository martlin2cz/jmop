package cz.martlin.jmop.gui.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.dial.AddTrackDialog;
import cz.martlin.jmop.gui.dial.AddTrackDialog.AddTrackData;
import cz.martlin.jmop.gui.dial.JMOPAboutDialog;
import cz.martlin.jmop.gui.dial.NewBundleDialog;
import cz.martlin.jmop.gui.dial.NewBundleDialog.NewBundleData;
import cz.martlin.jmop.gui.dial.NewPlaylistDialog;
import cz.martlin.jmop.gui.dial.NewPlaylistDialog.NewPlaylistData;
import cz.martlin.jmop.gui.dial.SavePlaylistDialog;
import cz.martlin.jmop.gui.dial.SavePlaylistDialog.SavePlaylistData;
import cz.martlin.jmop.gui.dial.StartBundleDialog;
import cz.martlin.jmop.gui.dial.StartBundleDialog.StartBundleData;
import cz.martlin.jmop.gui.dial.CreatePlaylistDialog;
import cz.martlin.jmop.gui.dial.CreatePlaylistDialog.CreatePlaylistData;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class GuiComplexActionsPerformer {

	private final JMOPPlayer jmop;

	public GuiComplexActionsPerformer(JMOPPlayer jmop) {
		this.jmop = jmop;
	}

	public void showPlaylist() {
		String playlistText = jmop.currentPlaylistAsString();
		showInfo("Current playlist", "This is currently played playlist", playlistText);

	}

	public void startNewBundle() {
		runInBackground(() -> {
			NewBundleDialog dial = new NewBundleDialog();

			Optional<NewBundleData> optional = dial.showAndWait();
			if (!optional.isPresent()) {
				return null;
			}

			NewBundleData data = optional.get();
			SourceKind kind = data.getKind();
			String bundleName = data.getName();
			String querySeed = data.getQuery();

			jmop.startNewBundle(kind, bundleName, querySeed);
			return null;
		});
	}

	public void startBundle(String bundleName) {
		runInBackground(() -> {
			List<String> playlistNames = jmop.listPlaylists(bundleName);
			String defaultPlaylistName = "all_tracks"; //FIXME HACK
			System.out.println(bundleName + " & " + playlistNames);
			StartBundleDialog dial = new StartBundleDialog(playlistNames, defaultPlaylistName);

			Optional<StartBundleData> optional = dial.showAndWait();
			if (!optional.isPresent()) {
				return null;
			}
			
			StartBundleData data = optional.get();
			String playlistName = data.getPlaylistName();
			jmop.startPlaylist(bundleName, playlistName);
			return null;
		});
	}

	public void startPlaylist() {
		runAndHandleError(() -> {
			List<String> bundles = jmop.listBundles();
			CreatePlaylistDialog dial = new CreatePlaylistDialog(bundles);

			Optional<CreatePlaylistData> optional = dial.showAndWait();
			if (!optional.isPresent()) {
				return null;
			}
			
			CreatePlaylistData data = optional.get();
			String bundleName = data.getBundleName();
			String playlistName = data.getPlaylistName();
			
			jmop.startPlaylist(bundleName, playlistName);
			return null;
		});
	}

	public void startPlaylist(String playlistName) {
		runAndHandleError(() -> {
			String bundleName = jmop.getCurrentBundle().getName();
			jmop.startPlaylist(bundleName, playlistName);
			return null;
		});
	}

	public void newPlaylist() {
		runAndHandleError(() -> {
			NewPlaylistDialog dial = new NewPlaylistDialog();

			Optional<NewPlaylistData> optional = dial.showAndWait();
			if (!optional.isPresent()) {
				return null;
			}
			
			NewPlaylistData data = optional.get();
			String querySeed = data.getQuery();
			jmop.startNewPlaylist(querySeed);
			return null;
		});
	}

	public void savePlaylist() {
		runAndHandleError(() -> {
			String currentPlaylistName = jmop.getCurrentPlaylist().getName();
			SavePlaylistDialog dial = new SavePlaylistDialog(currentPlaylistName);

			Optional<SavePlaylistData> optional = dial.showAndWait();
			if (!optional.isPresent()) {
				return null;
			}
			
			SavePlaylistData data = optional.get();
			String playlistName = data.getName();
			jmop.savePlaylistAs(playlistName);
			return null;			
		});
	}

	public void addTrack() {
		runInBackground(() -> {
			AddTrackDialog dial = new AddTrackDialog();

			Optional<AddTrackData> optional = dial.showAndWait();
			if (!optional.isPresent()) {
				return null;
			}
			
			AddTrackData data = optional.get();
			String query = data.getQuery();
			jmop.loadAndAddTrack(query);
			return null;
		});
	}

	/////////////////////////////////////////////////////////////////////////////////////

	public void playButtAction() {
		runInBackground(() -> {
			jmop.startPlaying();
			return null;
		});
	}

	public void stopButtAction() {
		runInBackground(() -> {

			jmop.stopPlaying();
			return null;
		});
	}

	public void pauseButtAction() {

		runInBackground(() -> {
			jmop.pausePlaying();
			return null;
		});
	}

	public void resumeButtAction() {
		runInBackground(() -> {
			jmop.resumePlaying();
			return null;
		});
	}

	public void nextButtAction() {
		runInBackground(() -> {
			jmop.toNext();
			return null;
		});
	}

	public void prevButtAction() {
		runInBackground(() -> {
			jmop.toPrevious();
			return null;
		});
	}
	/////////////////////////////////////////////////////////////////////////////////////

	public void exit() {
		if (jmop.getDescriptor().currentTrackProperty().isNotNull().get()) {
			jmop.stopPlaying();
		}
		System.exit(0);
	}

	public void checkConfiguration() {
		showInfo("Configuration check", "Check whether JMOP you can use",
				"Please whether you have youtube-dl and ffmpeg properly installed.");
	}

	public void showAboutBox() {
		runInBackground(() -> {
			JMOPAboutDialog dialog = new JMOPAboutDialog();
			dialog.show();
			return null;
		});

	}

	/////////////////////////////////////////////////////////////////////////////////////

	public List<String> listBundles() {
		return runAndHandleError(() -> jmop.listBundles());
	}

	public List<String> listPlaylists() {
		return runAndHandleError(() -> {
			Bundle bundle = jmop.getCurrentBundle();
			if (bundle == null) {
				return Collections.emptyList();
			}
			return jmop.listPlaylists(bundle.getName());
		});
	}

	public List<Track> listTracks() {
		return runAndHandleError(() -> {
			Playlist playlist = jmop.getCurrentPlaylist();
			if (playlist == null) {
				return Collections.emptyList();
			}
			return playlist.getTracks().getTracks();
		});
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private static <T> void runInBackground(RunnableWithException<T> run) {
		Task<T> task = new Task<T>() {
			@Override
			protected T call() throws Exception {
				Platform.runLater(() -> {
					runAndHandleError(run);
				});
				return null;
			}
		};

		Thread thread = new Thread(task, "BackgroundGUIOperationThread");
		thread.start();
		// Platform.runLater(() -> {
		// runAndHandleError(run);
		// });
	}

	private static <T> T runAndHandleError(RunnableWithException<T> run) {
		try {
			return run.run();
		} catch (Exception e) {
			showExceptionDialog(e);
			return null;
		}
	}

	public static void showInfo(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);

		Label label = new Label(content);
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);

		alert.show();
	}

	public static void showErrorDialog(String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("An error occured");

		alert.setHeaderText(header);
		alert.setContentText(message);

		alert.show();
	}

	private static void showExceptionDialog(Exception e) {
		e.printStackTrace();
		// TODO log by logger

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("An error occured");

		if (e.getCause() != null) {
			alert.setHeaderText("The error " + e.getClass().getName() + " caused by "
					+ e.getCause().getClass().getName() + " occured");
		} else {
			alert.setHeaderText("The error " + e.getClass().getName() + " occured");
		}

		alert.setContentText(e.toString());

		alert.show();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@FunctionalInterface
	public static interface RunnableWithException<T> {
		public T run() throws Exception;
	}

}
