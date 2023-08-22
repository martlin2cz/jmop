package cz.martlin.jmop.sourcery.picocli.mixins;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.model.Bundle;
import cz.martlin.jmop.sourcery.fascade.JMOPSourceryMusicbase;
import cz.martlin.jmop.sourcery.picocli.misc.JMOPSourceryProvider;
import picocli.CommandLine.Option;

/**
 * The add-to-(new/existing)-bundle arguments group.
 * 
 * @author martin
 *
 */
public class CreateOrUseBundleGroup {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreateOrAddToPlaylistGroup.class);

	@Option(names = { "--bundle", "-b" }, required = true, //
		description = "Adds into the bundle. Creates if doesn't exist.")
	private String pickOrCreateBundle;

	@Option(names = { "--use-bundle", "-ub" }, required = true, //
		description = "Adds into the bundle. Bundle must exist.")
	private String useBundle;

	@Option(names = { "--create-bundle", "-B" }, required = true, //
		description = "Adds into the bundle. Bundle gets created.")
	private String createBundle;

	public CreateOrUseBundleGroup() {
		super();
	}

	/**
	 * Returns the bundle, either picking existing or creating new.
	 * 
	 * @return
	 */
	public Bundle getBundle() {
		JMOPSourceryMusicbase musicbase = JMOPSourceryProvider.get().getSourcery().musicbase();

		if (pickOrCreateBundle != null) {
			LOGGER.debug("Will pick or create bundle {}", pickOrCreateBundle);
			if (musicbase.bundleOfName(pickOrCreateBundle) != null) {
				return getBundle(pickOrCreateBundle, musicbase);
			} else {
				return musicbase.createNewBundle(pickOrCreateBundle);
			}
		}

		if (useBundle != null) {
			LOGGER.debug("Will use bundle {} (if exists)", useBundle);
			return getBundle(useBundle, musicbase);
		}

		if (createBundle != null) {
			LOGGER.debug("Will create bundle {}", createBundle);
			return musicbase.createNewBundle(createBundle);
		}

		throw new IllegalStateException("May never happen");
	}

	private Bundle getBundle(String name, JMOPSourceryMusicbase musicbase) {
		return Objects.requireNonNull( //
				musicbase.bundleOfName(name),
				"There is no " + name + " bundle");
	}

	@Override
	public String toString() {
		return "CreateOrUseBundleGroup [pickOrCreateBundle=" + pickOrCreateBundle + ", useBundle=" + useBundle
				+ ", createBundle=" + createBundle + "]";
	}

}
