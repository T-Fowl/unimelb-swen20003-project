package com.tfowl.shadowblocks.states.properties;

/**
 * A typesafe property identification key to be used with {@link com.tfowl.shadowblocks.states.IState}s.
 *
 * @param <T> Type of the value associated with this property
 */
public interface IProperty<T> {

	/**
	 * @return Name of this property, or key
	 */
	public String getName();

	/**
	 * @return Class of the value
	 */
	public Class<T> getValueClass();
}
