package com.tfowl.project.unit;

import com.tfowl.project.reference.Resources;

/**
 * Special unit class used for holding the player unit
 * Created by Thomas on 6/09/2017.
 */
public class UnitPlayer extends Unit {

	public UnitPlayer() {
		setName(Resources.Units.PLAYER_NAME);
		setCanPushBlocks(true);
	}
}
