package com.tfowl.shadowblocks.states;

import com.tfowl.shadowblocks.states.properties.IProperty;

/**
 * Parent interface for difference kinds of states.
 * <p>
 * Items in the game world are uniquely identifier by their position and their state (which includes its type)
 *
 * @param <S> The subclass
 */
public interface IState<S extends IState<S>> {

	/**
	 * Get a property value
	 *
	 * @param property Property to get the value of
	 * @param <T>      The type of the value
	 * @return The value for the passed property
	 */
	public <T> T getValue(IProperty<T> property);

	/**
	 * Set the value for a property
	 *
	 * @param property Property to set the value for
	 * @param value    The value to set
	 * @param <T>      The type of the value
	 */
	public <T> void setValue(IProperty<T> property, T value);

	/**
	 * @return A deep copy of this state. Note that any mutable property values
	 * are not copied.
	 */
	public IState<S> deepCopy();
}
