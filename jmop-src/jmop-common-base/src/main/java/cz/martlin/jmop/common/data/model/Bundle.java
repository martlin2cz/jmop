package cz.martlin.jmop.common.data.model;

import java.util.Objects;

import cz.martlin.jmop.common.data.misc.HasMetadata;
import cz.martlin.jmop.common.data.misc.WithPlayedMarker;
import cz.martlin.jmop.core.misc.ObservableObject;
import javafx.util.Duration;

/**
 * The data structure representing bundle. Contains its name and metadata.
 * 
 * @author martin
 *
 */
public class Bundle extends ObservableObject<Bundle> implements Comparable<Bundle>, HasMetadata, WithPlayedMarker {

	/**
	 * The bundle name.
	 */
	private String name;
	
	/**
	 * The bundle metadata.
	 */
	private Metadata metadata;

	/**
	 * Creates the bundle.
	 * 
	 * @param name
	 * @param metadata
	 */
	public Bundle(String name, Metadata metadata) {
		this.name = name;
		this.metadata = metadata;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	@Override
	public void played(Duration time) {
		metadata = metadata.played(time);
	}

	///////////////////////////////////////////////////////////////////////////

	@Override
	public int compareTo(Bundle another) {
		return this.name.compareToIgnoreCase(another.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(metadata, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bundle other = (Bundle) obj;
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "Bundle [name=" + name + "]"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
