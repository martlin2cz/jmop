package cz.martlin.jmop.common.storages.xpfs;

import java.time.LocalDateTime;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.xspf.playlist.BaseXSPFElement;
import cz.martlin.xspf.playlist.XSPFPlaylist;
import cz.martlin.xspf.util.XMLDocumentUtility;
import cz.martlin.xspf.util.XMLDocumentUtility.TextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtility.ValueToTextMapper;
import cz.martlin.xspf.util.XSPFException;
import javafx.util.Duration;

public class JMOPXSPFPlaylistExtender {
	public static final String JMOP_APLICATION_URL = "https://github.com/martlin2cz/jmop";
	
	private static final XMLDocumentUtility util = new XMLDocumentUtility("jmop",
			JMOP_APLICATION_URL);
	
	private final BaseErrorReporter reporter;

	public JMOPXSPFPlaylistExtender(BaseErrorReporter reporter) {
		this.reporter = reporter;
	}

	public void init(XSPFPlaylist playlist) throws JMOPPersistenceException {
		Document doc = playlist.getDocument();
		try {
			util.init(doc);
		} catch (XSPFException e) {
			throw new JMOPPersistenceException("Could not prepare the document", e);
		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////////

	public Element getExtension(BaseXSPFElement element, String name) {
		try {
			Element extension = element.getExtension();
			return util.getChildElem(extension, name);
		} catch (XSPFException e) {
			reporter.report("The " + name + " extension is missing", e);
			return null;
		}
	}

	public Element getOrCreateExtension(BaseXSPFElement element, String name) {
		try {
			Element extension = element.getExtension();
			Element ext = util.getOrCreateChildElem(extension, name);
			element.getUtil().setElementAttr(ext, "aplication", JMOP_APLICATION_URL);
			return ext;
		} catch (XSPFException e) {
			reporter.report("The " + name + " could not be obtained", e);
			return null;
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public void setAttr(Element element, String name, String value) {
		try {
			util.setElementAttr(element, name, value);
		} catch (XSPFException e) {
			reporter.report("The attribute " + name + " could not be set", e);
		}
	}

	public void setAttr(Element element, String name, LocalDateTime value) {
		setElementAttr(element, name, value, (v) -> dateToString(v));
	}

	public void setAttr(Element element, String name, TrackIndex value) {
		setElementAttr(element, name, value, (v) -> trackIndexToString(v));
	}

	public void setAttr(Element element, String name, int value) {
		setElementAttr(element, name, value, (v) -> numberToString(v));
	}

	public void setAttr(Element element, String name, Duration value) {
		setElementAttr(element, name, value, (v) -> durationToString(v));
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	public String getStrAttr(Element element, String name) {
		try {
			return util.getElementAttr(element, name);
		} catch (XSPFException e) {
			reporter.report("The attribute " + name + " could not be read", e);
			return null;
		}
	}

	public LocalDateTime getDateAttr(Element element, String name) {
		return getElementAttr(element, name, (v) -> stringToDate(v), LocalDateTime.now()); //TODO: now or null?
	}

	public TrackIndex getIndexAttr(Element element, String name) {
		return getElementAttr(element, name, (v) -> stringToTrackIndex(v), TrackIndex.ofIndex(0));
	}

	public int getNumAttr(Element element, String name) {
		return getElementAttr(element, name, (v) -> stringToNumber(v), 0);
	}

	public Duration getDurationAttr(Element element, String name) {
		return getElementAttr(element, name, (v) -> stringToDuration(v), Duration.ZERO);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////

	private <T> void setElementAttr(Element element, String name, T value, ValueToTextMapper<T> mapper) {
		try {
			util.setElementAttr(element, name, value, mapper);
		} catch (XSPFException e) {
			reporter.report("The value (" + value + ") of attribute " + name + " could not be set", e);
		}
	}

	private <T> T getElementAttr(Element element, String name, TextToValueMapper<T> mapper, T fallbackValue) {
		try {
			return util.getElementAttr(element, name, mapper);
		} catch (XSPFException e) {
			reporter.report("The value of attribute " + name + " could not be get", e);
			return fallbackValue;
		}
	}

///////////////////////////////////////////////////////////////////////////////////////////////

	private static String dateToString(LocalDateTime value) {
		if (value == null) {
			return "(never)";
		}
		return value.toString();
	}

	private static String trackIndexToString(TrackIndex value) {
		return Integer.toString(value.getHuman());
	}

	private static String numberToString(int value) {
		return Integer.toString(value);
	}

	private static String durationToString(Duration value) {
		return DurationUtilities.toHumanString(value);
	}

	private LocalDateTime stringToDate(String text) {
		if (text.equals("(never)")) {
			return null;
		}
		return LocalDateTime.parse(text);
	}

	private TrackIndex stringToTrackIndex(String text) {
		int value = Integer.parseInt(text);
		return TrackIndex.ofHuman(value);
	}

	private int stringToNumber(String text) {
		int value = Integer.parseInt(text);
		if (value < 0 ) {
			throw new IllegalArgumentException("The " + value + " is negative.");
		}
		return value;
	}

	private Duration stringToDuration(String text) {
		return DurationUtilities.parseHumanDuration(text);
	}


}
