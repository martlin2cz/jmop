package cz.martlin.jmop.core.sources.locals.electronic.xspf;

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

import cz.martlin.jmop.common.storages.xpfs.XSPFDocumentNamespaces;
import cz.martlin.jmop.common.storages.xpfs.XSPFDocumentUtility;
import cz.martlin.jmop.core.sources.local.util.xml.XMLFileLoaderStorer;

public class XSPFFileDocumentTest {

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
		Element lorem = XSPFDocumentUtility.getChildOr(root, //
				XSPFDocumentNamespaces.XSPF, "lorem", //
				() -> null);
		assertNotNull(lorem);

		Element ipsum = XSPFDocumentUtility.getChildOr(root, //
				XSPFDocumentNamespaces.JMOP, "ipsum", //
				XSPFDocumentNamespaces.JMOP, "ipsum-val", "42", //
				() -> null);
		assertNotNull(ipsum);

		assertEquals("42", ipsum.getAttribute(XSPFDocumentNamespaces.JMOP.namify("ipsum-val")));
	}

	private void testGetOrCreate(Document document, Element root) {
		XSPFDocumentUtility.getChildOrCreate(document, root, //
				XSPFDocumentNamespaces.XSPF, "lorem");

		XSPFDocumentUtility.getChildOrCreate(document, root, //
				XSPFDocumentNamespaces.JMOP, "ipsum", //
				XSPFDocumentNamespaces.JMOP, "ipsum-val", "42");

		XSPFDocumentUtility.getChildOrCreate(document, root, //
				XSPFDocumentNamespaces.JMOP, "ipsum", //
				XSPFDocumentNamespaces.JMOP, "ipsum-val", "99");
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
		Element root = document.createElement(XSPFDocumentNamespaces.XSPF.namify("root"));

		for (XSPFDocumentNamespaces namespace : XSPFDocumentNamespaces.values()) {
			addNSattributes(root, namespace);
		}
		document.appendChild(root);
		return root;
	}

	private void addNSattributes(Element root, XSPFDocumentNamespaces namespace) {
		String attrName = namespace.namifyXMLNS();
		String atrrValue = namespace.getUrl();

		root.setAttribute(attrName, atrrValue);
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
