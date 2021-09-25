package cz.martlin.xspf.playlist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URI;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;

import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.playlist.elements.XSPFFile;
import cz.martlin.xspf.playlist.elements.XSPFPlaylist;
import cz.martlin.xspf.util.TestingFiles;
import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper;
import cz.martlin.xspf.util.XSPFException;

/**
 * Just an separate test dedicated to test the manipulation with the extensions.
 * Since they combine the xspf and plain DOM, any code gets little more verbose.
 * 
 * @author martin
 *
 */
public class WorkingWithExtensionsTest {

	/**
	 * Just loads testing playlist and reads testing extension's element value. Then
	 * sets new one.
	 * 
	 * @throws XSPFException
	 */
	@Test
	void testGetAndSetExtensionValue() throws XSPFException {
		System.out.println("Getting and setting extension value:");

		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "lorem-ipsum.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.playlist();

		XSPFExtensions extensions = playlist.extensions();
		XSPFExtension extension = extensions.find(URI.create("lorem-ipsum-generator.com"));

		XMLDocumentUtility util = extension.getUtility("generator", "lorem-ipsum-generator.com");
		Element extensionElem = extension.getElement();

		Element infoElem = util.getChildElemOrNull(extensionElem, "info");
		String infoValue = util.getElementAttrOrNull(infoElem, "notice",
				XMLDocumentUtilityHelper.TextToValueMapper.TEXT_TO_STRING);

		assertEquals("This file was NOT generated by any lorem ipsum generator.", infoValue);

		util.setElementAttr(infoElem, "further-notice", "But THIS was added by xspf-playlist.",
				XMLDocumentUtilityHelper.ValueToTextMapper.STRING_TO_TEXT);

		File fileToWrite = TestingFiles.fileToWriteAssumed("lorem-ipsum.xspf");
		file.save(fileToWrite);
	}

	/**
	 * Creates brand new extension and removes the original one.
	 * 
	 * @throws XSPFException
	 */
	@Test
	void testRemoveAndCreateExtension() throws XSPFException {
		System.out.println("Creating and removing whole extension:");

		File fileToRead = TestingFiles.fileToReadAssumed("playlist", "lorem-ipsum.xspf");
		XSPFFile file = XSPFFile.load(fileToRead);
		XSPFPlaylist playlist = file.playlist();

		XSPFExtensions extensions = playlist.extensions();
		XSPFExtension originalExtension = extensions.find(URI.create("lorem-ipsum-generator.com"));
		extensions.remove(originalExtension);

		XSPFExtension newExtension = extensions.createExtension(URI.create("jmp-app.cz"));
		Element element = newExtension.getElement();
		XMLDocumentUtility util = newExtension.getUtility("jmp", "jmp-app.cz/xmlns");

		util.setElementAttr(element, "done", LocalDateTime.now(), (v) -> v.toString());
		util.setChildElementValue(element, "inception", file, (f) -> f.toString());

		extensions.add(newExtension);

		File fileToWrite = TestingFiles.fileToWriteAssumed("lorem-ipsum.xspf");
		file.save(fileToWrite);
	}
}
