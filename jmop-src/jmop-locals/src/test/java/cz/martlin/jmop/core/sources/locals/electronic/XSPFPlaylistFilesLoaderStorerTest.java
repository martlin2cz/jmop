package cz.martlin.jmop.core.sources.locals.electronic;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.Test;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Metadata;
import cz.martlin.jmop.core.data.Tracklist;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.misc.TestingPrinter;
import cz.martlin.jmop.core.sources.SourceKind;

public class XSPFPlaylistFilesLoaderStorerTest {

	@Test
	public void testBundleFile() throws IOException, JMOPSourceException {
		Bundle bundle = createTestingBundle();
		
		File file = File.createTempFile("bundle-file", ".xspf");
		
		XSPFPlaylistFilesLoaderStorer xpfls = new XSPFPlaylistFilesLoaderStorer();
		
		xpfls.saveBundle(bundle, file);
		
		System.out.println(file.getAbsolutePath());
	}
	
	//////////////////////////////////////////////////////////////////////////////////////

	private Bundle createTestingBundle() {
		Bundle bundle = createEmptyTestingBundle();

		bundle.createTrack("1234", "foo bar", "Lorem Ipsum", //
				DurationUtilities.createDuration(0, 3, 15), //
				metadata(3, 3, 3, 29, 9)); //
		
		return bundle;
	}

	private Bundle createEmptyTestingBundle() {
		String name = "box";
		SourceKind kind = SourceKind.YOUTUBE;
		Metadata metadata = metadata(5, 2, 2, 29, 9);
		Bundle bundle = new Bundle(kind, name, metadata);
		return bundle;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	

	private Metadata metadata(int numberOfPlays, int createdDay, int createdMonth, int lastPlayedDay,
			int lastPlayedMonth) {
		Calendar lastPlayed = datetime(lastPlayedDay, lastPlayedMonth);
		Calendar created = datetime(createdDay, createdMonth);
		Metadata metadata = Metadata.createExisting(created, lastPlayed, numberOfPlays);
		return metadata;
	}

	private Calendar datetime(int dayAndMinute, int monthAndHour) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(2019, monthAndHour, dayAndMinute, monthAndHour, dayAndMinute, 30);
		
		return calendar;
	}

}
