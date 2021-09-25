package cz.martlin.xspf.playlist.base;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XSPFException;

/**
 * The top abstract superclass of the xspf-playlist. All the elements or nodes
 * may (directly or indirecly, i.e. via the {@link XSPFElement}) subclass this
 * class.
 * 
 * This class encapsulates any XML node and provides the utility instance and
 * also some usefull helpfull methods for the nodes manipulation.
 * 
 * @author martin
 *
 */
public abstract class XSPFNode {

	/**
	 * An instance of the utility.
	 */
	protected static final XMLDocumentUtility UTIL = new XMLDocumentUtility(null, "http://xspf.org/ns/0/");

	/**
	 * Creates instance.
	 */
	public XSPFNode() {
		super();
	}

	/**
	 * Returns the node this instance represents.
	 * 
	 * @return
	 */
	public abstract Node getNode();

/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Creates brand new element of the given name and by given factory.
	 * 
	 * @param <E>
	 * @param name
	 * @param factory
	 * @return
	 * @throws XSPFException 
	 */
	protected <E extends XSPFElement> E createOne(String name, XSPFElementFactory<E> factory) throws XSPFException {

		Node context = getNode();
		Element elem = UTIL.createNewElement(context, name);
		return factory.create(elem);
	}

	/**
	 * Returns the (clone of) the one child element of the given name and by given
	 * factory.
	 * 
	 * @param <E>
	 * @param name
	 * @param factory
	 * @return
	 * @throws XSPFException
	 */
	protected <E extends XSPFElement> E getOne(String name, XSPFElementFactory<E> factory) throws XSPFException {

		Node owner = getNode();
		Element child = UTIL.getChildElemClone(owner, name);
		if (child == null) {
			return null;
		}
		return factory.create(child);
	}

	/**
	 * Returns the (view) the one child element of the given name and by given
	 * factory.
	 * 
	 * @param <E>
	 * @param name
	 * @param factory
	 * @return
	 * @throws XSPFException
	 */
	protected <E extends XSPFElement> E one(String name, XSPFElementFactory<E> factory) throws XSPFException {

		Node owner = getNode();
		Element child = UTIL.getOrCreateChildElem(owner, name);
		if (child == null) {
			return null;
		}
		return factory.create(child);
	}

	/**
	 * Sets (replaces) the (clone of) the one child element of the given name.
	 * 
	 * @param <T>
	 * @param name
	 * @param value
	 * @throws XSPFException
	 */
	protected <T extends XSPFElement> void setOne(String name, T value) throws XSPFException {

		Node owner = getNode();
		if (value != null) {
			Element newElem = value.getElement();
			UTIL.replaceChildElementByClone(owner, name, newElem);
		} else {
			UTIL.removeChildElement(owner, name);
		}
	}

/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * An factory for creating the {@link XSPFElement}s for the element. In the most
	 * of cases, just directly the constructor of the particular element class.
	 * 
	 * @author martin
	 *
	 * @param <E>
	 */
	@FunctionalInterface
	public static interface XSPFElementFactory<E extends XSPFElement> {
		public E create(Element elem);
	}

}