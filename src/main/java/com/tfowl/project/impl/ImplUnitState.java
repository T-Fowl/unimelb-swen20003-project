package com.tfowl.project.impl;

import com.tfowl.project.unit.IUnitState;
import com.tfowl.project.unit.Unit;

public class ImplUnitState extends ImplState<Unit> implements IUnitState {

	public ImplUnitState(Unit block) {
		super(block);
	}

	@Override
	public Unit getUnit() {
		return super.getObject();
	}
}
