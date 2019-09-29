package cz.martlin.jmop.core.sources.locals.electronic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XSPFDocumentUtility {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");

	private XSPFDocumentUtility() {
	}
///////////////////////////////////////////////////////////////////////////

	public static Element createElementWithText(Document document, Element owner, String elemNS, String elemName,
			String value) {
		Element element = createElement(document, owner, elemNS, elemName);

		element.setTextContent(value);

		return element;
	}

	public static String getElementText(Element owner, String elemNS, String elemName) {
		Element element = getChildOr(owner, elemNS, elemName, //
				() -> fail(owner, elemName));

		return element.getNodeValue();
	}

	public static Element getChildOrFail(Element element, String elemNS, String elemName) {
		return getChildOr(element, elemNS, elemName, //
				() -> fail(element, elemName));
	}

///////////////////////////////////////////////////////////////////////////
	public static void iterateOverChildren(Element element, Consumer<Element> doWithChildElem) {
		NodeList childNodes = element.getChildNodes();

		for (int i = 0; i < childNodes.getLength(); i++) {
			Node child = childNodes.item(i);

			if (!(child instanceof Element)) {
				continue;
			}

			Element childElem = (Element) child;
			doWithChildElem.accept(childElem);
		}
	}

	public static void iterateOverChildrenWithCheck(Element element, String childNS, String childElemName,
			Consumer<Element> ifValidChild, Consumer<Element> ifInvalid) {
		iterateOverChildren(element, //
				(e) -> {
					if (isElement(e, childNS, childElemName)) {
						ifValidChild.accept(e);
					} else {
						ifInvalid.accept(e);
					}
				});
	}

	public static void iterateOverChildrenOrFail(Element element, String childNS, String childElemName,
			Consumer<Element> todo) {
		iterateOverChildrenWithCheck(element, childNS, childElemName, todo, //
				(e) -> fail(element, childElemName));
	}

///////////////////////////////////////////////////////////////////////////

	public static String getExtensionValue(Element element, String jmopExtensionElementName, String attrName) {
		Element jmopExtension = getJMOPExtensionElementOrFail(element, jmopExtensionElementName);

		String namespacedAttrName = /* JMOP_NAMESPACE_PREFIX + */ attrName;
		return jmopExtension.getAttributeNS(XSPFPlaylistFilesLoaderStorer.JMOP_NAMESPACE, namespacedAttrName);
	}

	private static Element getJMOPExtensionElementOrFail(Element element, String jmopExtensionElementName) {
		Element xspfExtension = getXspfExtensionElementOrFail(element);
		String namespacedElemName = XSPFPlaylistFilesLoaderStorer.JMOP_PREFIX + ":" + jmopExtensionElementName;

		return getChildOr(xspfExtension, //
				XSPFPlaylistFilesLoaderStorer.JMOP_NAMESPACE, namespacedElemName, //
				() -> fail(xspfExtension, namespacedElemName)); //
	}

	private static Element getXspfExtensionElementOrFail(Element element) {
		return getChildOr(element, //
				XSPFPlaylistFilesLoaderStorer.XSPF_NAMESPACE, "xspf:extension", //
				XSPFPlaylistFilesLoaderStorer.XSPF_NAMESPACE, "aplication",
				XSPFPlaylistFilesLoaderStorer.APPLICATION_URL, //
				() -> fail(element, "extension"));
	}

	///////////////////////////////////////////////////////////////////////////
	public static void setExtensionValue(Document document, Element element, String jmopExtensionElementName,
			String attrName, String attrValue) {
		Element jmopExtension = getJMOPExtensionElementOrCreate(document, element, jmopExtensionElementName);

		String namespacedAttrName = XSPFPlaylistFilesLoaderStorer.JMOP_PREFIX + ":" + attrName;
		jmopExtension.setAttributeNS(XSPFPlaylistFilesLoaderStorer.JMOP_NAMESPACE, namespacedAttrName, attrValue);
	}

	private static Element getJMOPExtensionElementOrCreate(Document document, Element element,
			String jmopExtensionElementName) {

		Element xspfExtension = getXspfExtensionElementOrCreate(document, element);
		String namespacedElemName = XSPFPlaylistFilesLoaderStorer.JMOP_PREFIX + ":" + jmopExtensionElementName;

		return getChildOr(xspfExtension, //
				XSPFPlaylistFilesLoaderStorer.JMOP_NAMESPACE, namespacedElemName, //
				() -> createElement(document, xspfExtension, //
						XSPFPlaylistFilesLoaderStorer.JMOP_NAMESPACE, namespacedElemName)); //
	}

	private static Element getXspfExtensionElementOrCreate(Document document, Element element) {
		return getChildOr(element, //
				XSPFPlaylistFilesLoaderStorer.XSPF_NAMESPACE, "xspf:extension", //
				XSPFPlaylistFilesLoaderStorer.XSPF_NAMESPACE, "aplication", //
				XSPFPlaylistFilesLoaderStorer.APPLICATION_URL, //
				() -> createElementWithAttr(document, element, //
						XSPFPlaylistFilesLoaderStorer.XSPF_NAMESPACE, "xspf:extension", //
						XSPFPlaylistFilesLoaderStorer.XSPF_NAMESPACE, "aplication", //
						XSPFPlaylistFilesLoaderStorer.APPLICATION_URL)); //
	}
///////////////////////////////////////////////////////////////////////////

//	@Deprecated
//	public static Element getChildOrFail(Element element, String elemNS, String elemName) {
//		return getChildOr(element, //
//				(e) -> isElement(e, elemNS, elemName), //
//				() -> fail(element, elemName)); //
//	}
//
//	@Deprecated
//	public static Element getChildOrFail(Element element, String elemNS, String elemName, String hasAttrNS,
//			String hasAttrName, String hasAttrValue) {
//		return getChildOr(element, //
//				(e) -> isElementWithAttr(e, elemNS, elemName, hasAttrNS, hasAttrName, hasAttrValue), //
//				() -> fail(element, elemName)); //
//	}
//
	public static Element getChildOrCreate(Document document, Element element, String elemNS, String elemName) {
		return getChildOr(element, //
				(e) -> isElement(e, elemNS, elemName), //
				() -> createElement(document, element, elemNS, elemName));
	}

	public static Element getChildOrCreate(Document document, Element element, String elemNS, String elemName,
			String hasAttrNS, String hasAttrName, String hasAttrValue) {
		return getChildOr(element, //
				(e) -> isElementWithAttr(e, elemNS, elemName, hasAttrNS, hasAttrName, hasAttrValue), //
				() -> createElementWithAttr(document, element, elemNS, elemName, hasAttrNS, hasAttrName, hasAttrValue));
	}

	public static Element getChildOr(Element element, String elemNS, String elemName, Supplier<Element> ifNot) {

		return getChildOr(element, //
				(e) -> isElement(e, elemNS, elemName), //
				ifNot);
	}

	public static Element getChildOr(Element element, String elemNS, String elemName, String hasAttrNS,
			String hasAttrName, String hasAttrValue, Supplier<Element> ifNot) {

		return getChildOr(element, //
				(e) -> isElementWithAttr(e, elemNS, elemName, hasAttrNS, hasAttrName, hasAttrValue), //
				ifNot);
	}

	///////////////////////////////////////////////////////////////////////////
	private static boolean isElement(Element element, String elemNS, String elemName) {

		return elemNS.equals(element.getNamespaceURI()) //
				&& elemName.equals(element.getNodeName());
	}

	private static boolean isElementWithAttr(Element element, String elemNS, String elemName, String hasAttrNS,
			String hasAttrName, String hasAttrValue) {

		return elemNS.equals(element.getNamespaceURI()) //
				&& elemName.equals(element.getNodeName()) //
				&& hasAttrValue.equals(element.getAttributeNS(hasAttrNS, hasAttrName));
	}

	private static Element createElement(Document document, Element owner, String elemNS, String elemName) {
		Element element = document.createElementNS(elemNS, elemName);
		owner.appendChild(element);
		return element;
	}

	private static Element createElementWithAttr(Document document, Element owner, String elemNS, String elemName,
			String attrNS, String attrName, String attrValue) {

		Element element = document.createElementNS(elemNS, elemName);
		element.setAttributeNS(attrNS, attrName, attrValue);

		owner.appendChild(element);
		return element;
	}

	private static Element fail(Element element, String elemName) {
		throw new IllegalArgumentException("Missing " + elemName + " inside of " + element.getNodeName());
	}

	///////////////////////////////////////////////////////////////////////////

	private static Element getChildOr(Element element, Predicate<Element> filter, Supplier<Element> ifNot) {
		Element child = getChildMatching(element, filter);

		if (child == null) {
			return ifNot.get();
		}

		return child;
	}

	private static Element getChildMatching(Element element, Predicate<Element> filter) {
		for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
			if (node instanceof Element) {
				Element childElement = (Element) node;

				boolean matches = filter.test(childElement);

				if (matches) {
					return childElement;
				}
			}
		}

		return null;
	}

	///////////////////////////////////////////////////////////////////////////

	public static Calendar parseDatetime(String str) {
		Calendar calendar = Calendar.getInstance();

		Date date;
		try {
			date = DATE_FORMAT.parse(str);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid datetime", e);
		}

		calendar.setTime(date);
		return calendar;
	}

	public static String stringifyDatetime(Calendar calendar) {
		Date date = calendar.getTime();
		return DATE_FORMAT.format(date);
	}

}
