package cz.martlin.jmop.common.testing.extensions;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.musicbase.BaseMusicbase;
import cz.martlin.jmop.common.musicbase.BaseMusicbaseModifing;
import cz.martlin.jmop.common.musicbase.persistent.BaseInMemoryMusicbase;
import cz.martlin.jmop.common.musicbase.persistent.BaseMusicbaseStorage;
import cz.martlin.jmop.common.testing.testdata.AbstractTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.SimpleTestingMusicdata;
import cz.martlin.jmop.common.testing.testdata.TestingMusicdataWithMusicbase;
import cz.martlin.jmop.common.testing.testdata.TestingMusicdataWithStorage;
import cz.martlin.jmop.common.testing.testdata.TestingMusicdataWithStorageAndMusicbase;

/**
 * An junit extension encapsulating the {@link AbstractTestingMusicdata}.
 * 
 * @author martin
 *
 */
public class TestingMusicdataExtension implements Extension, BeforeEachCallback, AfterEachCallback {

	private final Logger LOG = LoggerFactory.getLogger(getClass());
	
	private final Supplier<BaseMusicbaseModifing> musicbaseSupplier;
	private final Function<BaseMusicbaseModifing, BaseMusicbaseStorage> storageSupplier;
	private final boolean filesExisting;
	
	public AbstractTestingMusicdata tmd;

	private BaseMusicbaseModifing musicbase;
	private BaseMusicbaseStorage storage;

	private TestingMusicdataExtension(Supplier<BaseMusicbaseModifing> musicbaseSupplier,
			Function<BaseMusicbaseModifing, BaseMusicbaseStorage> storageSupplier, boolean filesExisting) {
		super();
		this.musicbaseSupplier = musicbaseSupplier;
		this.storageSupplier = storageSupplier;
		this.filesExisting = filesExisting;
	}

	public BaseMusicbaseModifing getMusicbase() {
		Objects.requireNonNull(musicbase, "This extenstion has no musicbase");
		return musicbase;
	}
	
	public BaseMusicbaseStorage getStorage() {
		Objects.requireNonNull(storage, "This extenstion has no storage");
		return storage;
	}
	
	///////////////////////////////////////////////////////////////////////////

	
	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		LOG.info("Preparing the test environment ...");
		
		
		this.tmd = buildTestingMusicbase();
		
		LOG.info("Prepared the test environment!");
		
	}


	@Override
	public void afterEach(ExtensionContext context) throws Exception {
		LOG.info("Terminating the test environment ...");
		
//		this.tmd.close();
		this.tmd = null;
		
		LOG.info("Terminated the test environment!");
	}

	///////////////////////////////////////////////////////////////////////////

	
	private AbstractTestingMusicdata buildTestingMusicbase() {
		if (musicbaseSupplier == null && storageSupplier == null) {
			return new SimpleTestingMusicdata();
		}
		
		if (musicbaseSupplier != null && storageSupplier == null) {
			musicbase = musicbaseSupplier.get();
			return new TestingMusicdataWithMusicbase(musicbase, filesExisting);
		}
		
		if (musicbaseSupplier == null && storageSupplier != null) {
			LOG.warn("This is deprecated, use the extension with storage and musicbase too");
			storage = storageSupplier.apply(null);
			return new TestingMusicdataWithStorage(storage, filesExisting);
		}
		
		if (musicbaseSupplier != null && storageSupplier != null) {
			musicbase = musicbaseSupplier.get();
			storage = storageSupplier.apply(musicbase);
			return new TestingMusicdataWithStorageAndMusicbase(musicbase, storage, filesExisting);
		}
		
		return null; // never happen
	}

	
	
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * Creates the rule for the testing musicbase created just as a fields.
	 * @param filesExisting
	 * @return
	 */
	public static TestingMusicdataExtension simple(boolean filesExisting) {
		return new TestingMusicdataExtension(null, null, filesExisting);
	}
	
	/**
	 * Creates the rule for the testing musicbase created in/by the given musicbase.
	 * @param musicbase
	 * @param filesExisting
	 * @return
	 */
	public static TestingMusicdataExtension withMusicbase(Supplier<BaseMusicbaseModifing> musicbase, boolean filesExisting) {
		return new TestingMusicdataExtension(musicbase, null, filesExisting);
	}
	
	/**
	 * @deprecated use {@link #withStorageAndMusicbase(Supplier, Function, boolean)} whenever possible
	 */
	@Deprecated
	public static TestingMusicdataExtension withStorage(Supplier<BaseMusicbaseStorage> storage, boolean filesExisting) {
		Function<BaseMusicbaseModifing, BaseMusicbaseStorage> storageFunction = (mb) -> storage.get();
		return new TestingMusicdataExtension(null, storageFunction, filesExisting);
	}
	
	/**
	 * Creates the rule for the testing musicbase created in/by the given musicbase and the storage.
	 * @param musicbase
	 * @param storage
	 * @param filesExisting
	 * @return
	 */
	public static TestingMusicdataExtension withStorageAndMusicbase(Supplier<BaseInMemoryMusicbase> musicbase, Function<BaseInMemoryMusicbase, BaseMusicbaseStorage> storage, boolean filesExisting) {
		Supplier<BaseMusicbaseModifing> musicbaseSupplier = () -> musicbase.get();
		Function<BaseMusicbaseModifing, BaseMusicbaseStorage> storageFunction = (mb) -> storage.apply((BaseInMemoryMusicbase) mb);
		return new TestingMusicdataExtension(musicbaseSupplier, storageFunction, filesExisting);
	}
///////////////////////////////////////////////////////////////////////////

	@Override
	public String toString() {
		return "TestingMusicdataExtension [tmd=" + tmd + "]";
	}

	
}
