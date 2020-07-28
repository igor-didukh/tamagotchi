package pets.actions;

import families.actions.FamilyAction;
import superclasses.actions.Action;

public class PetAction extends Action {
	public PetAction(int id, String actionName, int period, int healthLevel, int foodLevel, int wakeLevel, int neatLevel, int joyLevel) {
		super(id, actionName, period, healthLevel, foodLevel, wakeLevel, neatLevel, joyLevel);
	}
	
	public PetAction(FamilyAction fa) {
		super(0, fa.getName(), fa.getPeriod(), fa.getLevel(HEALTH), fa.getLevel(FOOD), fa.getLevel(WAKE), fa.getLevel(NEAT), fa.getLevel(JOY));
	}
}