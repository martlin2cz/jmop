package cz.martlin.jmop.core.wrappers;

import cz.martlin.jmop.core.misc.ProgressListener;
import cz.martlin.jmop.gui.DownloadGuiReporter;
import cz.martlin.jmop.gui.util.MediaPlayerGuiReporter;
@Deprecated
public interface GuiDescriptor {

	ProgressListener getProgressListener();

	MediaPlayerGuiReporter getMediaPlayerGuiReporter();

	DownloadGuiReporter getDownloadGuiReporter();

}
