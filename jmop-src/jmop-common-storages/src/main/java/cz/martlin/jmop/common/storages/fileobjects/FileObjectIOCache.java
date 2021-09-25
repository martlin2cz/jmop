package cz.martlin.jmop.common.storages.fileobjects;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;

/**
 * An implementation of the file object IO, which just keeps cached (all) the
 * file object instances loaded or created.
 * 
 * TODO: add disposal
 * 
 * @author martin
 *
 * @param <PT>
 */
public class FileObjectIOCache<PT> implements BaseFileObjectIO<PT> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BaseFileObjectIO<PT> delegee;
	private final Map<File, PT> cache;

	public FileObjectIOCache(BaseFileObjectIO<PT> delegee) {
		super();
		this.delegee = delegee;
		this.cache = new HashMap<>(2);
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public PT tryLoadOrCreate(File file) throws JMOPPersistenceException {
		if (cache.containsKey(file)) {
			LOG.debug("The " + file + " is cached");

			return cache.get(file);
		} else {
			LOG.info("Loading/creating and caching " + file);

			PT pt = delegee.tryLoadOrCreate(file);
			cache.put(file, pt);
			return pt;
		}
	}

	@Override
	public PT load(File file) throws JMOPPersistenceException {
		if (cache.containsKey(file)) {
			LOG.debug("The " + file + " is cached");

			return cache.get(file);
		} else {
			LOG.info("Loading and caching " + file);

			PT pt = delegee.load(file);
			cache.put(file, pt);
			return pt;
		}
	}

	@Override
	public void save(PT xfile, File file) throws JMOPPersistenceException {
		delegee.save(xfile, file);
	}

/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "PlaylistFileIOCache [" + cache + "]";
	}

}
