package cz.martlin.jmop.common.data.model;

import java.io.File;
import java.net.URI;

import cz.martlin.jmop.common.data.misc.HasMetadata;
import cz.martlin.jmop.common.data.misc.WithPlayedMarker;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * The data structure for track. Track contains reference to bundle,
 * idenfitifer, title, description length (duration). Instance of this class is
 * mutable.
 * 
 * @author martin
 *
 */
public class Track implements Comparable<Track>, HasMetadata, WithPlayedMarker {
	/**
	 * The bundle this track belongs to.
	 */
	private Bundle bundle;
	
	/**
	 * The source location (where it was downloaded or copied from). Can be null.
	 */
	private URI source; 
	
	/**
	 * The track title. Never null.
	 */
	private String title;
	
	/**
	 * The track description. Optional, can be null.
	 */
	private String description;
	
	/**
	 * The track time, length, duration. Never null.
	 */
	private Duration duration;
	
	/**
	 * The track file. Can be null or non-existing file.
	 */
	private File file;

	/**
	 * The track metadata. Never null.
	 */
	private Metadata metadata;

	public Track(Bundle bundle, String title, String description, Duration duration, URI source, File file,
			Metadata metadata) {
		super();
		this.bundle = bundle;
		this.title = title;
		this.description = description;
		this.duration = duration;
		this.source = source;
		this.file = file;
		this.metadata = metadata;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}
	
	public URI getSource() {
		return source;
	}
	
	public void setSource(URI source) {
		this.source = source;
	}
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int compareTo(Track another) {
		int titleCmp = this.title.compareToIgnoreCase(another.title);
		if (titleCmp != 0) {
			return titleCmp;
		}
		
		int bundleCmp = this.bundle.compareTo(another.bundle);
		return bundleCmp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bundle == null) ? 0 : bundle.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Track other = (Track) obj;
		if (bundle == null) {
			if (other.bundle != null)
				return false;
		} else if (!bundle.getName().equals(other.bundle.getName()))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Track [bundle=" + bundle.getName() + /*", identifier=" + identifier + */ ", title=" + title //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ ", description=" + "..." + ", file=" + file + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	public String toHumanString() {
		return title + " (" + DurationUtilities.toHumanString(duration) + ")";
	}
	
	@Override
	public void played(Duration time) {
		metadata = metadata.played(time);
	}
}
