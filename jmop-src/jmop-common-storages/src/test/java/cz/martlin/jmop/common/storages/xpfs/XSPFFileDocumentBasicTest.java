package cz.martlin.jmop.common.storages.xpfs;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.xspf.util.XMLFileLoaderStorer;

@Deprecated
public class XSPFFileDocumentBasicTest {

	@Test
	public void testGetCreateOrFail() throws JMOPPersistenceException {
		Document document = createDocument();
		Element root = createRoot(document);

		print(document);
		testGetOrCreate(document, root);
		print(document);

		testGet(document, root);
	}

	private void testGet(Document document, Element root) {
		Element lorem = _old_XSPFDocumentUtility.getChildOr(root, //
				XSPFDocumentNamespaces.XSPF, "lorem", //
				() -> null);
		assertNotNull(lorem);

		Element ipsum = _old_XSPFDocumentUtility.getChildOr(root, //
				XSPFDocumentNamespaces.JMOP, "ipsum", //
				XSPFDocumentNamespaces.JMOP, "ipsum-val", "42", //
				() -> null);
		assertNotNull(ipsum);

		assertEquals("42", ipsum.getAttribute(XSPFDocumentNamespaces.JMOP.namify("ipsum-val")));
	}

	private void testGetOrCreate(Document document, Element root) {
		_old_XSPFDocumentUtility.getChildOrCreate(document, root, //
				XSPFDocumentNamespaces.XSPF, "lorem");

		_old_XSPFDocumentUtility.getChildOrCreate(document, root, //
				XSPFDocumentNamespaces.JMOP, "ipsum", //
				XSPFDocumentNamespaces.JMOP, "ipsum-val", "42");

		_old_XSPFDocumentUtility.getChildOrCreate(document, root, //
				XSPFDocumentNamespaces.JMOP, "ipsum", //
				XSPFDocumentNamespaces.JMOP, "ipsum-val", "99");
	}

///////////////////////////////////////////////////////////////////////////
	@Test
	public void testExtensionSetAndGet() throws JMOPPersistenceException {
		Document document = createDocument();
		Element root = createRoot(document);

		print(document);

		_old_XSPFDocumentUtility.setExtensionValue(document, root, "karel", "width", "42");
		_old_XSPFDocumentUtility.setExtensionValue(document, root, "karel", "height", "99");

		_old_XSPFDocumentUtility.setExtensionValue(document, root, "jirka", "name", "jura");

		print(document);

		String karelWidth = _old_XSPFDocumentUtility.getExtensionValue(root, "karel", "width");
		assertEquals("42", karelWidth);

		String karelHeight = _old_XSPFDocumentUtility.getExtensionValue(root, "karel", "height");
		assertEquals("99", karelHeight);

		String jirkaName = _old_XSPFDocumentUtility.getExtensionValue(root, "jirka", "name");
		assertEquals("jura", jirkaName);

	}
///////////////////////////////////////////////////////////////////////////

	private Document createDocument() throws JMOPPersistenceException {
		throw new UnsupportedOperationException();
//		XMLFileLoaderStorer xml = new XMLFileLoaderStorer();
//		Document document = xml.createEmptyDocument();
//		return document;
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
