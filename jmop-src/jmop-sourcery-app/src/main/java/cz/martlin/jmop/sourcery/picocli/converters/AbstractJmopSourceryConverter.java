package cz.martlin.jmop.sourcery.picocli.converters;

import picocli.CommandLine.ITypeConverter;

/**
 * Temporary abstract common superclas for all the sourcery picocli converters
 * (until any generic common-app solution will be implemneted)
 * 
 * @author martin
 *
 */
public abstract class AbstractJmopSourceryConverter<T> implements ITypeConverter<T> {

	public AbstractJmopSourceryConverter() {
		super();
	}

}