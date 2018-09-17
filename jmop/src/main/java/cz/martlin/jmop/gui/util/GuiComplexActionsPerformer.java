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
import cz.martlin.jmop.gui.dial.CreatePlaylistDialog;
import cz.martlin.jmop.gui.dial.HelpDialog;
import cz.martlin.jmop.gui.dial.JMOPAboutDialog;
import cz.martlin.jmop.gui.dial.NewBundleDialog;
import cz.martlin.jmop.gui.dial.NewPlaylistDialog;
import cz.martlin.jmop.gui.dial.SavePlaylistDialog;
import cz.martlin.jmop.gui.dial.StartBundleDialog;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class GuiComplexActionsPerformer {

	private Scene scene;
	private final JMOPPlayer jmop;

	public GuiComplexActionsPerformer(JMOPPlayer jmop) {
		this.jmop = jmop;
	}

	public void specifyScene(Scene scene) {
		this.scene = scene;
	}

	///////////////////////////////////////////////////////////////////////////



	public void startNewBundle() {
		runInBackgroundWithDialog(() -> {
			NewBundleDialog dial = new NewBundleDialog();
			return dial;
		}, (data) -> {
			SourceKind kind = data.getKind();
			String bundleName = data.getName();
			String querySeed = data.getQuery();

			jmop.startNewBundle(kind, bundleName, querySeed);
		});
	}

	public void startBundle(String bundleName) {
		runInBackgroundWithDialog(() -> {
			List<String> playlistNames = jmop.listPlaylists(bundleName);
			String defaultPlaylistName = jmop.getConfig().getAllTracksPlaylistName();
			StartBundleDialog dial = new StartBundleDialog(playlistNames, defaultPlaylistName);
			return dial;
		}, (data) -> {
			String playlistName = data.getPlaylistName();
			jmop.startPlaylist(bundleName, playlistName);
		});

	}

	public void startPlaylist() {
		runInBackgroundWithDialog(() -> {
			List<String> bundles = jmop.listBundles();
			CreatePlaylistDialog dial = new CreatePlaylistDialog(bundles);
			return dial;
		}, (data) -> {
			String bundleName = data.getBundleName();
			String playlistName = data.getPlaylistName();

			jmop.startPlaylist(bundleName, playlistName);
		});

	}

	public void startPlaylist(String playlistName) {
		runAndHandleError(() -> {
			String bundleName = jmop.getData().playlistProperty().get().getBundle().getName();
			jmop.startPlaylist(bundleName, playlistName);
			return null;
		});
	}

	public void newPlaylist() {
		runInBackgroundWithDialog(() -> {
			NewPlaylistDialog dial = new NewPlaylistDialog();
			return dial;
		}, (data) -> {
			String querySeed = data.getQuery();
			jmop.startNewPlaylist(querySeed);
		});
	}

	public void savePlaylist() {
		runInBackgroundWithDialog(() -> {
			String currentPlaylistName = jmop.getData().playlistProperty().get().getName();
			SavePlaylistDialog dial = new SavePlaylistDialog(currentPlaylistName);
			return dial;
		}, (data) -> {
			String playlistName = data.getName();
			jmop.savePlaylistAs(playlistName);
		});
	}

	public void addTrack() {
		runInBackgroundWithDialog(() -> {
			AddTrackDialog dial = new AddTrackDialog();
			return dial;
		}, (data) -> {
			String query = data.getQuery();
			jmop.loadAndAddTrack(query);
		});

	}


	public void playTrack(int index) {
		runInBackground(() -> {
			jmop.playTrack(index);
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

	public void seek(Duration duration) {
		jmop.seek(duration);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	public void exit() {
		if (jmop.getData().hasActiveBundleAndPlaylistProperty().get() //
				&& jmop.getData().currentTrackProperty().isNotNull().get()) {
			jmop.stopPlaying();
		}
		System.exit(0);
	}

	public void openHelp() {
		runAndHandleError(() -> {
			HelpDialog dialog = new HelpDialog();
			dialog.show();
			return null;
		});

	}

	public void checkConfiguration() {
		runAndHandleError(() -> {
			String error = jmop.runCheck();
			if (error == null) {
				showInfo("Configuration check", "The configuration is OK",
						"The JMOP have checked some basic system settings and it seems it is all OK. "
								+ "If problems continues, see try to look into app logs.");
			} else {
				showErrorDialog("Configuration not OK", error);
			}
			return null;
		});

	}

	public void showAboutBox() {
		runAndHandleError(() -> {
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
			Bundle bundle = jmop.getData().playlistProperty().get().getBundle();
			if (bundle == null) {
				return Collections.emptyList();
			}
			return jmop.listPlaylists(bundle.getName());
		});
	}

	public List<Track> listTracks() {
		return runAndHandleError(() -> {
			Playlist playlist = jmop.getData().playlistProperty().get();
			if (playlist == null) {
				return Collections.emptyList();
			}
			return playlist.getTracks().getTracks();
		});
	}
	
	public int inferCurrentTrackIndex() {
		return runAndHandleError(() -> {
			Playlist playlist = jmop.getData().playlistProperty().get();
			if (playlist == null) {
				return 0;
			}
			return playlist.getCurrentTrackIndex();
		});
	}
	
	

	/////////////////////////////////////////////////////////////////////////////////////

	@Deprecated
	protected void changeCursor(Cursor cursor) {
		scene.getRoot().setCursor(cursor);
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private <T> void runInBackground(RunnableWithException<T> run) {
		Task<T> task = new Task<T>() {
			@Override
			protected T call() throws Exception {
				// Platform.runLater(() -> {
				runAndHandleError(run);
				// });
				return null;
			}

		};

		scene.setCursor(Cursor.WAIT);
		task.setOnSucceeded((e) -> {
			scene.setCursor(Cursor.DEFAULT);
		});
		task.setOnFailed((e) -> {
			scene.setCursor(Cursor.DEFAULT);
		});

		Thread thread = new Thread(task, "BackgroundGUIOperationThread");
		thread.start();
		// Platform.runLater(() -> {
		// runAndHandleError(run);
		// });
	}

	private <T> void runInBackgroundWithDialog(RunnableWithException<Dialog<T>> dialogConstructor,
			ConsumerWithException<T> dialogResultProcessor) {
		runAndHandleError(() -> {
			Platform.runLater(() -> { //
				runAndHandleError(() -> {

					Dialog<T> dialog = dialogConstructor.run();

					Optional<T> optional = dialog.showAndWait();
					if (optional.isPresent()) {
						T result = optional.get();
						runInBackground(() -> {
							dialogResultProcessor.consume(result);
							return null;
						});
					}

					return null;
				});
			});
			return null;
		});

	}

	private <T> T runAndHandleError(RunnableWithException<T> run) {
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

	@FunctionalInterface
	public static interface ConsumerWithException<T> {
		public void consume(T object) throws Exception;
	}


}
