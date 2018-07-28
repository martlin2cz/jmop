package cz.martlin.jmop.gui.util;

import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class GuiComplexActionsPerformer {
	@FunctionalInterface
	public interface RunnableWithException {
		public void run() throws Exception;
	}

	private GuiComplexActionsPerformer() {
	}

	public static void showPlaylist(JMOPPlayer jmop) {
		String playlistText = jmop.currentPlaylistAsString();
		showInfo("Current playlist", "This is currently played playlist", playlistText);

	}

	public static void startNewBundle(JMOPPlayer jmop) {
		runInBackground(() -> {
			String bundleName = JMOPDialogs.promptNewBundleName();
			String querySeed = JMOPDialogs.promptQuery();
			SourceKind kind = JMOPDialogs.promptKind();
			jmop.startNewBundle(kind, bundleName, querySeed);
		});
	}

	public static void startPlaylist(JMOPPlayer jmop) {
		runAndHandleError(() -> {
			String bundleName = JMOPDialogs.promptExistingBundle(jmop);
			String playlistName = JMOPDialogs.promptPlaylist(bundleName, jmop);
			jmop.startPlaylist(bundleName, playlistName);
		});
	}

	public static void newPlaylist(JMOPPlayer jmop) {
		runAndHandleError(() -> {
			String querySeed = JMOPDialogs.promptQuery();
			jmop.startNewPlaylist(querySeed);
		});
	}

	public static void savePlaylist(JMOPPlayer jmop) {
		runAndHandleError(() -> {
			String playlistName = JMOPDialogs.promptPlaylistName(jmop);
			jmop.savePlaylistAs(playlistName);
		});
	}

	/////////////////////////////////////////////////////////////////////////////////////
	private static void runInBackground(RunnableWithException run) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
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

	private static void runAndHandleError(RunnableWithException run) {
		try {
			run.run();
		} catch (Exception e) {
			showExceptionDialog(e);
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
		alert.setHeaderText(
				"The error " + e.getClass().getName() + " caused by " + e.getCause().getClass().getName() + " occured");
		alert.setContentText(e.toString());

		alert.show();
	}

}
