package cz.martlin.jmop.common.storages.playlists;

import java.time.LocalDateTime;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.core.exceptions.JMOPPersistenceException;
import javafx.util.Duration;

/**
 * An tool, specifiing how the JMOP meta values may get stored into, or loaded
 * from the generic extended playlist.
 * 
 * @author martin
 *
 * @param <PT>
 */
public interface BasePlaylistMetaInfoManager<PT> {

	public enum MetaKind {
		BUNDLE, PLAYLIST, TRACK;

	}

	public LocalDateTime getDateMetaValue(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException;

	public int getCountMetaValue(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException;

	public Duration getDurationMetaValue(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException;

	public TrackIndex getIndexMeta(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException;

	public String getStrMetaValue(PT xcontext, MetaKind kind, String name) throws JMOPPersistenceException;

	public void setDateMetaInfo(PT xcontext, MetaKind kind, String name, LocalDateTime date)
			throws JMOPPersistenceException;

	public void setCountMetaInfo(PT xcontext, MetaKind kind, String name, int count) throws JMOPPersistenceException;

	public void setDurationMetaInfo(PT xcontext, MetaKind kind, String name, Duration duration)
			throws JMOPPersistenceException;

	public void setIndexMetaInfo(PT xcontext, MetaKind kind, String name, TrackIndex index)
			throws JMOPPersistenceException;

	public void setStrMetaInfo(PT xcontext, MetaKind kind, String name, String value) throws JMOPPersistenceException;
}
