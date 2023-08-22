package cz.martlin.jmop.sourcery.picocli.misc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import cz.martlin.jmop.sourcery.fascade.JMOPSourcery;
import picocli.CommandLine.IFactory;

/**
 * The commands factor.y
 * 
 * @author martin
 *
 */
public class SourceryCommandsFactory implements IFactory {

	private final IFactory delegee;
	private final JMOPSourcery jmopSourcery;

	public SourceryCommandsFactory(IFactory delegee, JMOPSourcery jmopSourcery) {
		super();
		this.delegee = delegee;
		this.jmopSourcery = jmopSourcery;
	}

	@Override
	public <K> K create(Class<K> cls) throws Exception {
		if (!cls.getPackage().getName().startsWith("cz.martlin.jmop"))  {
			return delegee.create(cls);
		}
		
		try {
			Constructor<K> ctor = cls.getConstructor();
			return ctor.newInstance();
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// go on
		}
		
		try {
			Constructor<K> ctor = cls.getConstructor(JMOPSourcery.class);
			return ctor.newInstance(jmopSourcery);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// go on
		}

		throw new IllegalArgumentException("No compatible constructor for the " + cls);

	}

}
