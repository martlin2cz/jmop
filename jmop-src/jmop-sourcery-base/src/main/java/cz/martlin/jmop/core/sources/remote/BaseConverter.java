package cz.martlin.jmop.core.sources.remote;

import java.io.File;

import cz.martlin.jmop.common.data.model.Track;

public interface BaseConverter {

	void convert(Track track, File from, File to) throws JMOPSourceryException;

}
