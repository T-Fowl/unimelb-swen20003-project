package com.tfowl.project.unit;

import com.tfowl.project.states.IState;

public interface IUnitState extends IState<IUnitState> {

	public Unit getUnit();
}
