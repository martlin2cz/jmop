package cz.martlin.jmop.sourcery.locals.playlists;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Objects;

/**
 * A basic tool providing access to the sample testing playlist file.
 * 
 * @author martin
 *
 */
public class SamplePlaylistFileHandler {
	
	
	public File getSamplePlaylistFile() {
		String path = "/cz/martlin/jmop/sourcery/locals/playlists/sample-discovery.xspf";
		
		try {
			URL url = getClass().getResource(path);
			Objects.requireNonNull(url, "Testing playlist not found");
			File file = new File(url.toURI());
			if (!file.isFile()) {
				throw new FileNotFoundException();
			}
			return file;
		} catch (Exception e) {
			fail("Cannot find testing playlist file: " + path, e);
			return null;
		}
	}
}
