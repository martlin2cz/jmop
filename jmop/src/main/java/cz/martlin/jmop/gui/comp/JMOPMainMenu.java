package cz.martlin.jmop.gui.comp;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import cz.martlin.jmop.core.data.Playlist;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.ObservableListenerBinding;
import cz.martlin.jmop.core.wrappers.JMOPPlayer;
import cz.martlin.jmop.gui.control.RequiresJMOP;
import cz.martlin.jmop.gui.util.GuiComplexActionsPerformer;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class JMOPMainMenu extends MenuBar implements Initializable, RequiresJMOP {
	// @FXML
	// JMOPMainMenu menu;

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
	private MenuItem miLockUnlockThisPlaylist;
	@FXML
	private MenuItem miRemoveRemaining;

	@FXML
	private HalfDynamicMenu menuTrack;
	@FXML
	private MenuItem miAppendCustomTrack;

	private JMOPPlayer jmop;
	private GuiComplexActionsPerformer actions;

	private final ObservableListenerBinding<Playlist> playlistBinding;

	public JMOPMainMenu() throws IOException {
		super();

		this.playlistBinding = new ObservableListenerBinding<>();

		load();
	}

	private void load() throws IOException {
		loadFXML();
	}

	private void loadFXML() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cz/martlin/jmop/gui/fx/MainMenu.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.load();
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// we need to wait for the JMOP
	}

	@Override
	public void setupJMOP(JMOPPlayer jmop, GuiComplexActionsPerformer actions) {
		this.jmop = jmop;
		this.actions = actions;

		initializeBindings();
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	private void initializeBindings() {
		final BooleanBinding hasNoPlaylist = jmop.getData().inPlayModeProperty().not();

		miStop.disableProperty().bind(//
				jmop.getData().stoppedProperty() //
						.or(hasNoPlaylist));
		miPlay.disableProperty().bind(//
				jmop.getData().stoppedProperty().not() //
						.or(jmop.getData().hasSomeTrackProperty().not()) //
						.or(hasNoPlaylist));

		miPause.disableProperty().bind( //
				jmop.getData().pausedProperty() //
						.or(jmop.getData().stoppedProperty()) //
						.or(hasNoPlaylist));
		miResume.disableProperty().bind( //
				jmop.getData().pausedProperty().not() //
						.or(jmop.getData().stoppedProperty()) //
						.or(hasNoPlaylist));

		miPrevious.disableProperty().bind( //
				jmop.getData().hasPreviousProperty().not()//
						.or(hasNoPlaylist));
		miNext.disableProperty().bind(//
				jmop.getData().hasNextProperty().not() //
						.or(hasNoPlaylist));

		menuPlaylist.disableProperty().bind(hasNoPlaylist);
		menuTrack.disableProperty().bind(hasNoPlaylist);

		jmop.getData().playlistProperty()
				.addListener((observable, oldVal, newVal) -> playlistPropertyChanged(oldVal, newVal));
	}

	/////////////////////////////////////////////////////////////////////////////////////////

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
		int currentIndex = actions.inferCurrentTrackIndex();
		ToggleGroup group = new ToggleGroup();
		AtomicInteger index = new AtomicInteger(0);
		addDynamicItems(menuTrack, tracks, (t) -> trackMenuItem(t, index, group, currentIndex));

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

	public void onTrackTitleAction(ActionEvent event) {
		MenuItem playlistMenuItem = (MenuItem) event.getSource();
		int index = menuTrack.getDynamicItems().indexOf(playlistMenuItem);
		actions.playTrack(index);
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

	public void onLockUnlockPlaylistAction() {
		actions.lockUnlockPlaylist();
	}

	public void onClearRemainingAction() {
		actions.clearRemaining();
	}

	public void onAppendCustomTrackAction() {
		actions.addTrack();
	} /////////////////////////////////////////////////////////////////////////////////////////

	public void onHelpAction() {
		actions.openHelp();
	}

	public void onCheckConfigurationAction() {
		actions.checkConfiguration();
	}

	public void onAboutAction() {
		actions.showAboutBox();
	}
	/////////////////////////////////////////////////////////////////////////////////////////

	private MenuItem trackMenuItem(Track track, AtomicInteger index, ToggleGroup group, int currentlyPlayedIndex) {
		String title = track.getTitle();
		RadioMenuItem mi = new RadioMenuItem(title);

		mi.setToggleGroup(group);

		boolean isCurrentlyPlayed = (index.get() == currentlyPlayedIndex);
		mi.setSelected(isCurrentlyPlayed);

		index.incrementAndGet();
		return mi;
	}

	private static <E> void addDynamicItems(HalfDynamicMenu menu, List<E> items, Function<E, MenuItem> mapper) {

		List<MenuItem> menuItems = items.stream().map(mapper).collect(Collectors.toList());
		menu.setDynamicItems(menuItems);
	}

	/////////////////////////////////////////////////////////////////////////////////////////

	private void playlistPropertyChanged(Playlist oldPlaylist, Playlist newPlaylist) {
		playlistBinding.rebind(oldPlaylist, newPlaylist, (p) -> playlistChanged((Playlist) p));

		playlistChanged(newPlaylist);
	}

	private void playlistChanged(Playlist playlist) {

		if (playlist.isLocked()) {
			miLockUnlockThisPlaylist.setText("Un_lock this playlist");
			changeImage("/cz/martlin/jmop/gui/img/unlock-playlist.svg");
		} else {
			miLockUnlockThisPlaylist.setText("_Lock this playlist");
			changeImage("/cz/martlin/jmop/gui/img/lock-playlist.svg");
		}
	}

	private void changeImage(String url) {
		ImageView graphic = (ImageView) miLockUnlockThisPlaylist.getGraphic();
		Image image = new Image(url);

		graphic.setImage(image);
	}

}
