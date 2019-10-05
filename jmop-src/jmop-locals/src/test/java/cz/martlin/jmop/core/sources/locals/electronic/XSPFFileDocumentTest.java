package cz.martlin.jmop.core.sources.locals.electronic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jmop.core.sources.locals.util.XMLFileLoaderStorer;

public class XSPFFileDocumentTest {

	private static final String NSFOO = "foo";
	private static final String NSBAR = "bar";

	@Test
	public void testGetCreateOrFail() {
		Document document = createDocument();
		Element root = createRoot(document);

		print(document);
		testGetOrCreate(document, root);
		print(document);

		testGet(document, root);
	}

	private void testGet(Document document, Element root) {
		Element lorem = XSPFDocumentUtility.getChildOr(root, NSFOO, "lorem", () -> null);
		assertNotNull(lorem);

		Element ipsum = XSPFDocumentUtility.getChildOr(root, NSBAR, "ipsum", NSBAR, "ipsum-val", "42", () -> null);
		assertNotNull(ipsum);
		assertEquals("42", ipsum.getAttribute/*NS*/(/*NSBAR, */"ipsum-val"));
	}

	private void testGetOrCreate(Document document, Element root) {
		XSPFDocumentUtility.getChildOrCreate(document, root, NSFOO, "lorem");

		XSPFDocumentUtility.getChildOrCreate(document, root, NSBAR, "ipsum", NSBAR, "ipsum-val", "42");
		XSPFDocumentUtility.getChildOrCreate(document, root, NSBAR, "ipsum", NSBAR, "ipsum-val", "99");
	}

///////////////////////////////////////////////////////////////////////////
	@Test
	public void testExtensionSetAndGet() {
		Document document = createDocument();
		Element root = createRoot(document);

		print(document);

		XSPFDocumentUtility.setExtensionValue(document, root, "karel", "width", "42");
		XSPFDocumentUtility.setExtensionValue(document, root, "karel", "height", "99");
		
		XSPFDocumentUtility.setExtensionValue(document, root, "jirka", "name", "jura");

		print(document);
		
		String karelWidth = XSPFDocumentUtility.getExtensionValue(root, "karel", "width");
		assertEquals("42", karelWidth);

		String karelHeight = XSPFDocumentUtility.getExtensionValue(root, "karel", "height");
		assertEquals("99", karelHeight);

		String jirkaName = XSPFDocumentUtility.getExtensionValue(root, "jirka", "name");
		assertEquals("jura", jirkaName);

	}
///////////////////////////////////////////////////////////////////////////

	private Document createDocument() {
		XMLFileLoaderStorer xml = new XMLFileLoaderStorer();
		Document document = xml.createEmptyDocument();
		return document;
	}

	private Element createRoot(Document document) {
		Element root = document.createElement/*NS*/(/*NSFOO, */"root");
		
		root.setAttribute/*NS*/(/*XSPFPlaylistFilesLoaderStorer.XMLNS_NAMESPACE, */"xmlns:foo", NSFOO);
		root.setAttribute/*NS*/(/*XSPFPlaylistFilesLoaderStorer.XMLNS_NAMESPACE, */"xmlns:bar", NSBAR);
		
		root.setAttribute/*NS*/(/*XSPFPlaylistFilesLoaderStorer.XMLNS_NAMESPACE, */"xmlns", XSPFPlaylistFilesLoaderStorer.XSPF_NAMESPACE);
		root.setAttribute/*NS*/(/*XSPFPlaylistFilesLoaderStorer.XMLNS_NAMESPACE, */"xmlns:jmop", XSPFPlaylistFilesLoaderStorer.JMOP_NAMESPACE);
		
		document.appendChild(root);
		return root;
	}

	private static void print(Document doc) {
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");

			StreamResult result = new StreamResult(new StringWriter());
			DOMSource source = new DOMSource(doc);
			transformer.transform(source, result);

			String xmlString = result.getWriter().toString();
			System.out.println(xmlString);
			System.out.println();
		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
