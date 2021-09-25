package cz.martlin.jmop.common.storages.playlists;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jmop.common.data.misc.TrackIndex;
import cz.martlin.jmop.core.misc.BaseErrorReporter;
import cz.martlin.jmop.core.misc.DurationUtilities;
import javafx.util.Duration;

/**
 * An delegating meta info manager, which just delegates the calls to provided
 * delegee, but handles (and reports) all the caught errors.
 * 
 * @author martin
 *
 * @param <PT>
 */
public class FailsavePlaylistMetaInfoManager<PT> implements BasePlaylistMetaInfoManager<PT> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final BasePlaylistMetaInfoManager<PT> delegee;
	private final BaseErrorReporter reporter;

	public FailsavePlaylistMetaInfoManager(BasePlaylistMetaInfoManager<PT> delegee, BaseErrorReporter reporter) {
		super();
		this.delegee = delegee;
		this.reporter = reporter;
	}

	@Override
	public LocalDateTime getDateMetaValue(PT xcontext, MetaKind kind, String name) {
		try {
			return delegee.getDateMetaValue(xcontext, kind, name);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "get", "date");
			return LocalDateTime.now();
		}
	}

	@Override
	public int getCountMetaValue(PT xcontext, MetaKind kind, String name) {
		try {
			return delegee.getCountMetaValue(xcontext, kind, name);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "get", "date");
			return 0;
		}
	}

	@Override
	public Duration getDurationMetaValue(PT xcontext, MetaKind kind, String name) {
		try {
			return delegee.getDurationMetaValue(xcontext, kind, name);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "get", "date");
			return DurationUtilities.createDuration(0, 0, 0);
		}
	}

	@Override
	public TrackIndex getIndexMeta(PT xcontext, MetaKind kind, String name) {
		try {
			return delegee.getIndexMeta(xcontext, kind, name);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "get", "date");
			return TrackIndex.ofHuman(1);
		}
	}

	@Override
	public String getStrMetaValue(PT xcontext, MetaKind kind, String name) {
		try {
			return delegee.getStrMetaValue(xcontext, kind, name);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "get", "date");
			return "";
		}
	}

	@Override
	public void setDateMetaInfo(PT xcontext, MetaKind kind, String name, LocalDateTime date) {
		try {
			delegee.setDateMetaInfo(xcontext, kind, name, date);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "set", "date");
		}
	}

	@Override
	public void setCountMetaInfo(PT xcontext, MetaKind kind, String name, int count) {
		try {
			delegee.setCountMetaInfo(xcontext, kind, name, count);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "set", "date");
		}
	}

	@Override
	public void setDurationMetaInfo(PT xcontext, MetaKind kind, String name, Duration duration) {
		try {
			delegee.setDurationMetaInfo(xcontext, kind, name, duration);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "set", "date");
		}
	}

	@Override
	public void setIndexMetaInfo(PT xcontext, MetaKind kind, String name, TrackIndex index) {
		try {
			delegee.setIndexMetaInfo(xcontext, kind, name, index);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "set", "date");
		}
	}

	@Override
	public void setStrMetaInfo(PT xcontext, MetaKind kind, String name, String value) {
		try {
			delegee.setStrMetaInfo(xcontext, kind, name, value);
		} catch (Exception e) {
			report(e, xcontext, kind, name, "set", "date");
		}
	}

	private void report(Exception ex, PT xcontext, MetaKind kind, String metaName, String getOrSetSpecifier,
			String type) {

		LOG.error("Could not {} value of the {} field of the {} kind of type {} of the context {}, becausse of {}", //
				getOrSetSpecifier, metaName, kind, type, xcontext, ex);
		// LOG.error("Because of the exception", e);

		String message = "Could not " + getOrSetSpecifier + " value of " + metaName;
		reporter.report(message, ex);

	}

}
