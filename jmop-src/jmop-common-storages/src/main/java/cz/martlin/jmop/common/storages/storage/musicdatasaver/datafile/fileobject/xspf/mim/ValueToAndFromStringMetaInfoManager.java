package cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim;

import java.time.LocalDateTime;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.JMOPtoXSFPAdapter;
import cz.martlin.jmop.common.storages.storage.musicdatasaver.datafile.fileobject.xspf.mim.conv.BaseValueToAndFromStringConverters;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableTextToValueMapper;
import cz.martlin.xspf.util.XMLDocumentUtilityHelper.NullableValueToTextMapper;
import javafx.util.Duration;

/**
 * An abstract meta info manager, which converts each of such value to the text
 * (and back) by {@link BaseValueToAndFromStringConverters} and delegates to subclass's get/set
 * textual meta.
 * 
 * Component of {@link JMOPtoXSFPAdapter}.
 * 
 * @author martin
 *
 * @param <PT>
 */
public abstract class ValueToAndFromStringMetaInfoManager<PT> implements BasePlaylistMetaInfoManager<PT> {

	private final BaseValueToAndFromStringConverters converters;

	public ValueToAndFromStringMetaInfoManager(BaseValueToAndFromStringConverters converters) {
		super();
		this.converters = converters;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	public LocalDateTime getDateMetaValue(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException {
		return getMetaValue(xcontext, kind, name, converters.textToDateMapper());
	}

	@Override
	public int getCountMetaValue(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException {
		Integer value = getMetaValue(xcontext, kind, name, converters.textToNumberMapper());
		if (value == null) {
			throw new JMOPPersistenceException("No count meta value of " + name);
		} else {
			return value.intValue();
		}
	}

	@Override
	public Duration getDurationMetaValue(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException {
		return getMetaValue(xcontext, kind, name, converters.textToDurationMapper());
	}

	@Override
	public TrackIndex getIndexMeta(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException {
		return getMetaValue(xcontext, kind, name, converters.textToTrackIndexMapper());
	}

	@Override
	public String getStrMetaValue(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException {
		return getMetaValue(xcontext, kind, name, NullableTextToValueMapper.TEXT_TO_STRING);
	}

	@Override
	public void setDateMetaInfo(PT xcontext, MetaKind kind, String name, LocalDateTime date)
			throws JMOPPersistenceException {
		setMetaValue(xcontext, kind, name, date, converters.dateToTextMapper());
	}

	@Override
	public void setCountMetaInfo(PT xcontext, MetaKind kind, String name, int count) throws JMOPPersistenceException {
		setMetaValue(xcontext, kind, name, count, converters.numberToTextMapper());
	}

	@Override
	public void setDurationMetaInfo(PT xcontext, MetaKind kind, String name, Duration duration)
			throws JMOPPersistenceException {
		setMetaValue(xcontext, kind, name, duration, converters.durationToTextMapper());
	}

	@Override
	public void setIndexMetaInfo(PT xcontext, MetaKind kind, String name, TrackIndex index)
			throws JMOPPersistenceException {
		setMetaValue(xcontext, kind, name, index, converters.trackIndexToTextMapper());
	}

	@Override
	public void setStrMetaInfo(PT xcontext, MetaKind kind, String name, String value) throws JMOPPersistenceException {
		setMetaValue(xcontext, kind, name, value, NullableValueToTextMapper.STRING_TO_TEXT);
	}
	/////////////////////////////////////////////////////////////////////////////////////

	protected abstract <T> void setMetaValue(PT xcontextent, MetaKind kind, String metaName, T value,
			NullableValueToTextMapper<T> mapper) throws JMOPPersistenceException;

	protected abstract <T> T getMetaValue(PT xcontextent, MetaKind kind, String metaName, NullableTextToValueMapper<T> mapper)
			throws JMOPPersistenceException;

	/////////////////////////////////////////////////////////////////////////////////////

}