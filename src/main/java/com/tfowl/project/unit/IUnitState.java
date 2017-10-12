package com.tfowl.project.unit;

import com.tfowl.project.states.IState;

/**
 * The state of a {@link Unit} in the world. This will hold properties for a given unit and its type.
 */
public interface IUnitState extends IState<IUnitState> {

	/**
	 * @return The type of this unit
	 */
	public Unit getUnit();
}
