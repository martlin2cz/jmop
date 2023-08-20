package cz.martlin.jmop.sourcery.picocli.misc;

import picocli.CommandLine;
import picocli.CommandLine.ITypeConverter;

/**
 * 
 * @author martin
 * @deprecated replaced by {@link CommandLine#setCaseInsensitiveEnumValuesAllowed(boolean)}
 */
public class ServiceConverter implements ITypeConverter<Service> {

	@Override
	public Service convert(String value) throws Exception {
		return Service.valueOf(value.toUpperCase());
	}

}
