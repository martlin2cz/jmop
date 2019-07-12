package cz.martlin.jmop.core.misc;

import javafx.beans.property.Property;

/**
 * Just simple indicator for all wrapper classes. Wrapper class is in the most
 * cases class, which references some {@link ObservableObject}(s), listens for
 * its/their change(s) and if so, particularry updates {@link Property}(ies).
 * 
 * @author martin
 *
 * @param <T>
 */
public interface BaseWrapper<T> {
	/**
	 * Initializes bindings between the observable objects and properties.
	 * 
	 * Note: Keep in mind to properly call this method from the constructor.
	 */
	public void initBindings();
}
