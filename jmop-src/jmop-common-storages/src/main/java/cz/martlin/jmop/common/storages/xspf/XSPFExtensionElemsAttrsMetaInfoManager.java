package cz.martlin.jmop.common.storages.xspf;

import java.net.URI;

import org.w3c.dom.Element;

import cz.martlin.jmop.common.storages.playlists.BaseValueToAndFromStringConverters;
import cz.martlin.jmop.common.storages.playlists.ValueToAndFromStringMetaInfoManager;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.xspf.playlist.base.XSPFCommon;
import cz.martlin.xspf.playlist.collections.XSPFExtensions;
import cz.martlin.xspf.playlist.elements.XSPFExtension;
import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.TextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.ValueToTextMapper;
import cz.martlin.xspf.util.XSPFException;
import cz.martlin.xspf.util.XSPFRuntimeException;

/**
 * An meta info manager, which stores the meta informations as the XSPF
 * extensions's sub-elements attributes.
 * 
 * @author martin
 *
 */
public class XSPFExtensionElemsAttrsMetaInfoManager extends ValueToAndFromStringMetaInfoManager<XSPFCommon> {

	private static final String JMOP_NS_PREFIX = "jmop";
	private static final String JMOP_NS_URI = "https://github.com/martlin2cz/jmop";
	public static final String JMOP_APLICATION_URL = "https://github.com/martlin2cz/jmop";

	public XSPFExtensionElemsAttrsMetaInfoManager(BaseValueToAndFromStringConverters converters) {
		super(converters);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected <T> void setMetaValue(XSPFCommon xelement, MetaKind kind, String metaName, T value,
			ValueToTextMapper<T> mapper) throws JMOPPersistenceException {
		try {
			XSPFExtension extension = getOrCreateJMOPExtension(xelement);
			Element extensionElem = extension.getElement();
			XMLDocumentUtility util = extension.getUtility(JMOP_NS_PREFIX, JMOP_NS_URI);

			String metaElemName = metaElemName(kind);
			Element subExtElem = util.getOrCreateChildElem(extensionElem, metaElemName);
			util.setElementAttr(subExtElem, metaName, value, mapper);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException(
					"Cannot set the value " + value + " of " + kind + "'s " + metaName + " of " + xelement, e);
		}

	}

	@Override
	public <T> T getMetaValue(XSPFCommon xelement, MetaKind kind, String metaName, TextToValueMapper<T> mapper)
			throws JMOPPersistenceException {
		try {
			XSPFExtension extension = getOrCreateJMOPExtension(xelement);
			Element extensionElem = extension.getElement();
			XMLDocumentUtility util = extension.getUtility(JMOP_NS_PREFIX, JMOP_NS_URI);

			String metaElemName = metaElemName(kind);
			Element subExtElem = util.getChildElemOrNull(extensionElem, metaElemName);
			if (subExtElem == null) {
				return null;
			}
			return util.getElementAttrOrNull(subExtElem, metaName, mapper);
		} catch (XSPFException | XSPFRuntimeException e) {
			throw new JMOPPersistenceException(
					"Cannot get the value of " + kind + "'s " + metaName + " of " + xelement, e);
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private String metaElemName(MetaKind kind) {
		return kind.name().toLowerCase();
	}

	private XSPFExtension getOrCreateJMOPExtension(XSPFCommon element) throws XSPFException {
		URI application = URI.create(JMOP_APLICATION_URL);

		XSPFExtensions extensions = element.extensions();

		XSPFExtension extension = extensions.extension(application);
		if (extension == null) {
			extension = extensions.createExtension(application);
			extensions.add(extension);
		}

		return extension;
	}

}