package cz.martlin.jmop.common.storages.xpfs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XSPFDocumentUtility {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
	private static final String NEVER = "(never)";

	private XSPFDocumentUtility() {
	}

///////////////////////////////////////////////////////////////////////////
	public static void createCommentWithText(Document document, Element owner, String value) {
		Comment comment = document.createComment(value);
		owner.appendChild(comment);
	}

	public static Element createElementWithText(Document document, Element owner, XSPFDocumentNamespaces elemNS,
			String elemName, String value) {
		Element element = createElement(document, owner, elemNS, elemName);

		element.setTextContent(value);

		return element;
	}

	public static String getElementText(Element owner, XSPFDocumentNamespaces elemNS, String elemName) {
		Element element = getChildOr(owner, elemNS, elemName, //
				() -> fail(owner, elemNS, elemName));

		return element.getTextContent();
	}

	public static Element getChildOrFail(Element element, XSPFDocumentNamespaces elemNS, String elemName) {
		return getChildOr(element, elemNS, elemName, //
				() -> fail(element, elemNS, elemName));
	}

///////////////////////////////////////////////////////////////////////////
	public static void iterateOverChildren(Element element, XSPFDocumentNamespaces childElemNS, String childElemName,
			Consumer<Element> doWithChildElem) {
		NodeList children = element.getElementsByTagName(/* childElemNS, */ childElemName);

		for (int i = 0; i < children.getLength(); i++) {
			Node childNode = children.item(i);
			Element childElement = (Element) childNode;

			doWithChildElem.accept(childElement);
		}
	}

//	public static void iterateOverChildrenWithCheck(Element element, XSPFDocumentNamespaces childNS,
//			String childElemName, Consumer<Element> ifValidChild, Consumer<Element> ifInvalid) {
//		iterateOverChildren(element, childNS, childElemName, //
//				(e) -> {
//					// FIXME haack
////					if (isElement(e, childNS, childElemName)) {
//					ifValidChild.accept(e);
////					} else {
////						ifInvalid.accept(e);
////					}
//				});
//	}

	public static void iterateOverChildrenOrFail(Element element, XSPFDocumentNamespaces childNS, String childElemName,
			Consumer<Element> todo) {
		iterateOverChildren(element, childNS, childElemName, todo);
	}

///////////////////////////////////////////////////////////////////////////
	
	public static String getExtensionValue(Element element, String jmopExtensionElementName, String attrName) {
		Element jmopExtension = getJMOPExtensionElementOrFail(element, jmopExtensionElementName);

		return jmopExtension.getAttribute(attrName);
	}

	private static Element getJMOPExtensionElementOrFail(Element element, String jmopExtensionElementName) {
		Element xspfExtension = getXspfExtensionElementOrFail(element);

		return getChildOr(xspfExtension, //
				XSPFDocumentNamespaces.JMOP, jmopExtensionElementName, //
				() -> fail(xspfExtension, XSPFDocumentNamespaces.JMOP, jmopExtensionElementName)); //
	}

	private static Element getXspfExtensionElementOrFail(Element element) {
		return getChildOr(element, //
				XSPFDocumentNamespaces.XSPF, "extension", //
				XSPFDocumentNamespaces.XSPF, "aplication", //
				XSPFFilesManipulator.APPLICATION_URL, //
				() -> fail(element, XSPFDocumentNamespaces.XSPF, "extension"));
	}

	///////////////////////////////////////////////////////////////////////////
	public static void setExtensionValue(Document document, Element element, String jmopExtensionElementName,
			String attrName, String attrValue) {
		Element jmopExtension = getJMOPExtensionElementOrCreate(document, element, jmopExtensionElementName);

		jmopExtension.setAttribute(attrName, attrValue);
	}

	private static Element getJMOPExtensionElementOrCreate(Document document, Element element,
			String jmopExtensionElementName) {

		Element xspfExtension = getXspfExtensionElementOrCreate(document, element);

		return getChildOr(xspfExtension, //
				XSPFDocumentNamespaces.JMOP, jmopExtensionElementName, //
				() -> createElement(document, xspfExtension, //
						XSPFDocumentNamespaces.JMOP, jmopExtensionElementName)); //
	}

	private static Element getXspfExtensionElementOrCreate(Document document, Element element) {
		return getChildOr(element, //
				XSPFDocumentNamespaces.XSPF, "extension", //
				XSPFDocumentNamespaces.XSPF, "aplication", //
				XSPFFilesManipulator.APPLICATION_URL, //
				() -> createElementWithAttr(document, element, //
						XSPFDocumentNamespaces.XSPF, "extension", //
						XSPFDocumentNamespaces.XSPF, "aplication", //
						XSPFFilesManipulator.APPLICATION_URL)); //
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
	public static Element getChildOrCreate(Document document, Element element, XSPFDocumentNamespaces elemNS,
			String elemName) {
		return getChildOr(element, elemNS, elemName, //
				() -> createElement(document, element, elemNS, elemName));
	}

	public static Element getChildOrCreate(Document document, Element element, XSPFDocumentNamespaces elemNS,
			String elemName, XSPFDocumentNamespaces hasAttrNS, String hasAttrName, String hasAttrValue) {
		return getChildOr(element, elemNS, elemName, //
				(e) -> isElementWithAttr(e, hasAttrNS, hasAttrName, hasAttrValue), //
				() -> createElementWithAttr(document, element, elemNS, elemName, hasAttrNS, hasAttrName, hasAttrValue));
	}

	public static Element getChildOr(Element element, XSPFDocumentNamespaces elemNS, String elemName,
			Supplier<Element> ifNot) {

		return getChildOr(element, elemNS, elemName, //
				(e) -> true, //
				ifNot);
	}

	public static Element getChildOr(Element element, XSPFDocumentNamespaces elemNS, String elemName,
			XSPFDocumentNamespaces hasAttrNS, String hasAttrName, String hasAttrValue, Supplier<Element> ifNot) {

		return getChildOr(element, elemNS, elemName, //
				(e) -> isElementWithAttr(e, hasAttrNS, hasAttrName, hasAttrValue), //
				ifNot);
	}

	///////////////////////////////////////////////////////////////////////////
//	private static boolean isElement(Element element, String elemNS, String elemName) {
//
//		return elemNS.equals(element.getNamespaceURI()) //
//				&& elemName.equals(element.getNodeName());
//	}

	private static boolean isElementWithAttr(Element element, XSPFDocumentNamespaces hasAttrNS, String hasAttrName,
			String hasAttrValue) {

		String nsHasAttrName = hasAttrNS.namify(hasAttrName);
		String value = element.getAttribute(nsHasAttrName);
		return hasAttrValue.equals(value);
	}

	protected static Element createElement(Document document, Element owner, XSPFDocumentNamespaces elemNS,
			String elemName) {

		String nsElemName = elemNS.namify(elemName);
		Element element = document.createElement(nsElemName);

		owner.appendChild(element);
		return element;
	}

	protected static Element createElementWithAttr(Document document, Element owner, XSPFDocumentNamespaces elemNS,
			String elemName, XSPFDocumentNamespaces attrNS, String attrName, String attrValue) {

		String nsElemName = elemNS.namify(elemName);
		Element element = document.createElement(nsElemName);

		String nsAttrName = attrNS.namify(attrName);
		element.setAttribute(nsAttrName, attrValue);

		owner.appendChild(element);
		return element;
	}

	private static Element fail(Element element, XSPFDocumentNamespaces elemNS, String elemName) {
		throw new IllegalArgumentException(
				"Missing '" + elemName + "' inside of '" + element.getNodeName() + "' (NS: " + elemNS + ")");
	}

	///////////////////////////////////////////////////////////////////////////

	private static Element getChildOr(Element element, XSPFDocumentNamespaces childElemNS, String childElemName,
			Predicate<Element> filter, Supplier<Element> ifNot) {
		Element child = getChildMatching(element, childElemNS, childElemName, filter);

		if (child == null) {
			return ifNot.get();
		}

		return child;
	}

	private static Element getChildMatching(Element element, XSPFDocumentNamespaces childElemNS, String childElemName,
			Predicate<Element> filter) {

		String nsChildElemName = childElemNS.namify(childElemName);
		NodeList children = element.getElementsByTagName(nsChildElemName);

		for (int i = 0; i < children.getLength(); i++) {
			Node childNode = children.item(i);
			Element childElement = (Element) childNode;

			boolean matches = filter.test(childElement);

			if (matches) {
				return childElement;
			}
		}

		return null;

	}

	///////////////////////////////////////////////////////////////////////////

	public static Calendar parseDatetime(String str) {
		if (NEVER.equals(str)) {
			return null;
		}
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
		if (calendar == null) {
			return NEVER;
		}
		
		Date date = calendar.getTime();
		return DATE_FORMAT.format(date);
	}

}
