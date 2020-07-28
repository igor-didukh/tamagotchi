package superclasses.actions;

import main.Constants;
import superclasses.Entity;

public abstract class Action extends Entity implements Constants {
	private int period;
	
	private int[] levels = new int[PARAMS + 1];
	
	public Action(int id, String actionName, int period, int healthLevel, int foodLevel, int wakeLevel, int neatLevel, int joyLevel) {
    	super(id, actionName);
    	
    	this.period = period;
    	
    	this.levels[HEALTH] = healthLevel;
		this.levels[FOOD] = foodLevel;
		this.levels[WAKE] = wakeLevel;
		this.levels[NEAT] = neatLevel;
		this.levels[JOY] = joyLevel;
    }
	
	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}
	
	public int getLevel(int param) {
		return levels[param];
	}
	
	public void setLevel(int param, int value) {
		levels[param] = value;
	}

	@Override
    public String toString() {
        return " [ action: id=" + getId() + ", " + getName() + " ]";
    }
}