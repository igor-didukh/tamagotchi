package families.actions;

import superclasses.actions.Action;

public class FamilyAction extends Action {
	public FamilyAction(int id, String actionName, int period, int healthLevel, int foodLevel, int wakeLevel, int neatLevel, int joyLevel) {
		super(id, actionName, period, healthLevel, foodLevel, wakeLevel, neatLevel, joyLevel);
	}
}