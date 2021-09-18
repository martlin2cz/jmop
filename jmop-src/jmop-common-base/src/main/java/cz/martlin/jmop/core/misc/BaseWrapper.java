package cz.martlin.jmop.core.misc;

import javafx.beans.property.Property;

/**
 * Just simple indicator for all wrapper classes. Wrapper class is in the most
 * cases class, which references some {@link ObservableObject}(s), listens for
 * its/their change(s) and if so, particularry updates {@link Property}(ies).
 * 
 * @author martin
 * @deprecated The wrapper is not good idea. Let's (ping TODO) instead of
 *             wrapping objects into another layer simply create standalone
 *             object, which just handles the Properties stuff and possibly the
 *             events handling.
 * 
 *             Convert "Wrapper delegates calls to object, object changes,
 *             delegate updates its properties" approach to "object works
 *             standalone, the so-called wrapper just listens its changes and
 *             propagates them via properties"
 * 
 * 
 * @param <T>
 */
@Deprecated
public interface BaseWrapper<T> {
	/**
	 * Initializes bindings between the observable objects and properties.
	 * 
	 * Note: Keep in mind to properly call this method from the constructor.
	 */
	public void initBindings();
}