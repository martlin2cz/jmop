package cz.martlin.jmop.gui.util;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ErrorReporter;
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
import cz.martlin.jmop.gui.local.Msg;
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
	private final ErrorReporter reporter;
	private final JMOPPlayer jmop;

	public GuiComplexActionsPerformer(JMOPPlayer jmop) {
		this.jmop = jmop;
		this.reporter = jmop.getErrorReporter();
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

	public void lockUnlockPlaylist() {
		runInForegound(() -> {
			jmop.togglePlaylistLockedStatus();
			return null;
		});
	}

	public void clearRemaining() {
		runInForegound(() -> {
			jmop.clearRemainingTracks();
			return null;
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
		runInForegound(() -> {
			jmop.playTrack(index);
			return null;
		});
	}
	/////////////////////////////////////////////////////////////////////////////////////

	public void playButtAction() {
		runInForegound(() -> {
			jmop.startPlaying();
			return null;
		});
	}

	public void stopButtAction() {
		runInForegound(() -> {
			jmop.stopPlaying();
			return null;
		});
	}

	public void pauseButtAction() {
		runInForegound(() -> {
			jmop.pausePlaying();
			return null;
		});
	}

	public void resumeButtAction() {
		runInForegound(() -> {
			jmop.resumePlaying();
			return null;
		});
	}

	public void nextButtAction() {
		runInForegound(() -> {
			jmop.toNext();
			return null;
		});
	}

	public void prevButtAction() {
		runInForegound(() -> {
			jmop.toPrevious();
			return null;
		});
	}

	public void seek(Duration duration) {
		jmop.seek(duration);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	public void exit() {
		if (jmop.getData().inPlayModeProperty().get() //
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
				showInfo(Msg.get("Configuration_check"), Msg.get("The_configuration_is_OK"), //$NON-NLS-1$ //$NON-NLS-2$
						Msg.get("The_JMOP_have_checked_") //$NON-NLS-1$
								+ Msg.get("If_problem_continues_")); //$NON-NLS-1$
			} else {
				showErrorDialog(Msg.get("Configuration_not_OK"), error); //$NON-NLS-1$
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

	/////////////////////////////////////////////////////////////////////////////////////
	private <T> void runInForegound(RunnableWithException<T> run) {
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

		task.run();
		// Platform.runLater(() -> {
		// runAndHandleError(run);
		// });
	}

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

		Thread thread = new Thread(task, "BackgroundGUIOperationThread"); //$NON-NLS-1$
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
			reporter.internal(e);
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
		alert.setTitle(Msg.get("An_error_occured")); //$NON-NLS-1$

		alert.setHeaderText(header);
		alert.setContentText(message);

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
