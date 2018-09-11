package cz.martlin.jmop.gui.control;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.comp.HalfDynamicMenu;
import cz.martlin.jmop.gui.comp.JMOPMainMenu;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
/**
 * use {@link JMOPMainMenu}.
 * @author martin
 *
 */
@Deprecated
public class MainMenuController implements Initializable {
	@FXML
	JMOPMainMenu menu;

	@FXML
	private MenuItem miPlay;
	@FXML
	private MenuItem miStop;
	@FXML
	private MenuItem miPause;
	@FXML
	private MenuItem miResume;
	@FXML
	private MenuItem miPrevious;
	@FXML
	private MenuItem miNext;

	@FXML
	private HalfDynamicMenu menuBundle;
	@FXML
	private MenuItem miStartNewBundle;

	@FXML
	private HalfDynamicMenu menuPlaylist;
	@FXML
	private MenuItem miStartNewPlaylist;
	@FXML
	private MenuItem miSaveThisPlaylistAs;

	@FXML
	private HalfDynamicMenu menuTrack;
	@FXML
	private MenuItem miAppendCustomTrack;

	private GuiComplexActionsPerformer actions;

	public MainMenuController() {
		super();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeBindings();

	}
	/////////////////////////////////////////////////////////////////////////////////////////

	private void initializeBindings() {
		//menu.jmopProperty().addListener((observable, oldVal, newVal) -> jmopChanged(newVal));

	}

	private void jmopChanged(JMOPPlayer jmop) {
		this.actions = new GuiComplexActionsPerformer(jmop);

		initializeBindings(jmop);
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	private void initializeBindings(JMOPPlayer jmop) {
		final BooleanBinding hasNoPlaylist = jmop.getData().hasActiveBundleAndPlaylistProperty().not();

		miStop.disableProperty().bind(jmop.getData().stoppedProperty().or(hasNoPlaylist));
		miPlay.disableProperty().bind(jmop.getData().stoppedProperty().not().or(hasNoPlaylist));

		miPause.disableProperty().bind( //
				jmop.getData().pausedProperty() //
						.or(jmop.getData().stoppedProperty()) //
						.or(hasNoPlaylist));
		miResume.disableProperty().bind( //
				jmop.getData().pausedProperty().not() //
						.or(jmop.getData().stoppedProperty()) //
						.or(hasNoPlaylist));

		miPrevious.disableProperty().bind(hasNoPlaylist.or(jmop.getData().hasPreviousProperty().not()));
		miNext.disableProperty().bind(hasNoPlaylist.or(jmop.getData().hasNextProperty().not()));

		menuPlaylist.disableProperty().bind(hasNoPlaylist);
		menuTrack.disableProperty().bind(hasNoPlaylist);
	}

	public void onBundleMenuShowing() {
		List<String> bundleNames = actions.listBundles();
		addDynamicItems(menuBundle, bundleNames, (bn) -> new MenuItem(bn));
	}

	public void onPlaylistMenuShowing() {
		List<String> playlistNames = actions.listPlaylists();
		addDynamicItems(menuPlaylist, playlistNames, (pn) -> new MenuItem(pn));
	}

	public void onTrackMenuShowing() {
		List<Track> tracks = actions.listTracks();
		addDynamicItems(menuTrack, tracks, (t) -> new MenuItem(t.getTitle()));

	}

	/////////////////////////////////////////////////////////////////////////////////////////

	public void onBundleNameAction(ActionEvent event) {
		MenuItem bundleMenuItem = (MenuItem) event.getSource();
		String bundleName = bundleMenuItem.getText();
		actions.startBundle(bundleName);
	}

	public void onPlaylistNameAction(ActionEvent event) {
		MenuItem playlistMenuItem = (MenuItem) event.getSource();
		String playlistName = playlistMenuItem.getText();
		actions.startPlaylist(playlistName);
	}

	public void onTrackTitleAction() {
		// System.out.println("play track");
		// TODO play selected track
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public void onPlayAction() {
		actions.playButtAction();
	}

	public void onStopAction() {
		actions.stopButtAction();
	}

	public void onPauseAction() {
		actions.pauseButtAction();
	}

	public void onResumeAction() {
		actions.resumeButtAction();
	}

	public void onPreviousAction() {
		actions.prevButtAction();
	}

	public void onNextAction() {
		actions.nextButtAction();
	}

	public void onExitAction() {
		actions.exit();
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	public void onStartNewBundleAction() {
		actions.startNewBundle();
	}

	public void onStartNewPlaylistAction() {
		actions.newPlaylist();
	}

	public void onSavePlaylistAsAction() {
		actions.savePlaylist();
	}

	public void onAppendCustomTrackAction() {
		actions.addTrack();
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	private static <E> void addDynamicItems(HalfDynamicMenu menu, List<E> items, Function<E, MenuItem> mapper) {

		List<MenuItem> menuItems = items.stream().map(mapper).collect(Collectors.toList());
		menu.setDynamicItems(menuItems);
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	public void onHelpAction() {
		//TODO help
	}
	
	public void onCheckConfigurationAction() {
		actions.checkConfiguration();
	}

	public void onAboutAction() {
		actions.showAboutBox();
	}

}
