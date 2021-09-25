package cz.martlin.jmop.common.testing.resources;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.google.common.io.Files;

import cz.martlin.jmop.core.sources.local.TrackFileFormat;

/**
 * An utility which loads the testing resource file of the given name, located
 * in the same package as the target object.
 * 
 * @author martin
 *
 */
public class TestingResources {

	public static InputStream loadSampleTrack(Object target, TrackFileFormat format) {
		String path="cz/martlin/jmop/common/testing/resources/sample." + format.fileExtension();
		return getResource(target, path);
	}
	
	public static InputStream loadResource(Object target, String name) {
		Class<?> clazz = target.getClass();
		String dirs = clazz.getPackageName().replace('.', '/');
		String path = dirs + "/" + name;

		return getResource(target, path);
	}

	private static InputStream getResource(Object target, String path) {
		Class<?> clazz = target.getClass();
		InputStream ins = clazz.getClassLoader().getResourceAsStream(path);
		assumeTrue(ins != null, "The resource " + path + " does not exist");
		return ins;
	}

	public static File prepareResource(Object target, String name) throws IOException {
		File file = File.createTempFile("jmop-", "-" + name);

		InputStream ins = loadResource(target, name);
		byte[] bytes = read(ins);
		Files.write(bytes, file);

		System.out.println("Resource file ready: " + file);
		return file;
	}

	private static byte[] read(InputStream ins) throws IOException {
		return org.apache.commons.io.IOUtils.readFully(ins, ins.available());
	}
}