package cz.martlin.jmop.gui.util;

import java.util.Collections;
import java.util.List;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.dial.JMOPAboutDialog;
import cz.martlin.jmop.gui.dial.JMOPDialogs;
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
			String bundleName = JMOPDialogs.promptNewBundleName();
			String querySeed = JMOPDialogs.promptQuery();
			SourceKind kind = JMOPDialogs.promptKind();
			jmop.startNewBundle(kind, bundleName, querySeed);
			return null;
		});
	}

	public void startBundle(String bundleName) {
		runInBackground(() -> {
			String playlistName = JMOPDialogs.promptPlaylist(bundleName, jmop);
			jmop.startPlaylist(bundleName, playlistName);
			return null;
		});
	}

	public void startPlaylist() {
		runAndHandleError(() -> {
			String bundleName = JMOPDialogs.promptExistingBundle(jmop);
			String playlistName = JMOPDialogs.promptPlaylist(bundleName, jmop);
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
			String querySeed = JMOPDialogs.promptQuery();
			jmop.startNewPlaylist(querySeed);
			return null;
		});
	}

	public void savePlaylist() {
		runAndHandleError(() -> {
			String playlistName = JMOPDialogs.promptPlaylistName(jmop);
			jmop.savePlaylistAs(playlistName);
			return null;
		});
	}

	public void addTrack() {
		runInBackground(() -> {
			String querySeed = JMOPDialogs.promptQuery();
			jmop.loadAndAddTrack(querySeed);
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
		jmop.stopPlaying();
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

	private static void showInfo(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);

		Label label = new Label(content);
		label.setWrapText(true);
		alert.getDialogPane().setContent(label);

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
