package pets;

import main.Constants;
import superclasses.Entity;

import java.util.Timer;
import java.util.TimerTask;

public class Pet extends Entity implements Constants {
	
	class TimerAction extends TimerTask {
	    public void run() {
	    	int ind = (int)(5 * Math.random()) + 1;
	    	if (levels[ind] > -10) 
	    		levels[ind]--;
	    	
	    	int en = 0;
	    	for (int i = 1; i <= PARAMS; i++) 
	    		en += weights[i] * levels[i];
	    	
	    	energy += en / PET_ENERGY_DIV;
	    	energy = Math.max(energy, 0);
	    	energy = Math.min(energy, 100);
	    	
	    	age++;
	    }
	}
	
	private int familyId;
	private String familyName;
	private int familyAge;
	private int userId;
	private String userName;
	private String imageResource;
	private int[] weights = new int[PARAMS + 1];
	private int[] levels = new int[PARAMS + 1];
	private int age;
	private int energy;
	
	private Timer timer;
	
	public Pet(
			int id, String petName, 
			int familyId, String familyName, int familyAge, 
			int userId, String userName, 
			String imageResource, 
			int healthWeight, int foodWeight, int wakeWeight, int neatWeight, int joyWeight,
			int healthLevel, int foodLevel, int wakeLevel, int neatLevel, int joyLevel, 
			int age, int energy
		) {
		
		super(id, petName);
		
		this.familyId = familyId;
		this.familyName = familyName;
		this.familyAge = familyAge;
		this.userId = userId;
		this.userName = userName;
		this.imageResource = imageResource;
		
		this.weights[HEALTH] = healthWeight;
		this.weights[FOOD] = foodWeight;
		this.weights[WAKE] = wakeWeight;
		this.weights[NEAT] = neatWeight;
		this.weights[JOY] = joyWeight;
		
		this.levels[HEALTH] = healthLevel;
		this.levels[FOOD] = foodLevel;
		this.levels[WAKE] = wakeLevel;
		this.levels[NEAT] = neatLevel;
		this.levels[JOY] = joyLevel;
		
		this.age = age;
		this.energy = energy;
		
		this.timer = null;
	}

	public int getFamilyId() {
		return familyId;
	}
	
	public String getFamilyName() {
		return familyName;
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getImageResource() {
		return imageResource;
	}

	public void setImageResource(String imageResource) {
		this.imageResource = imageResource;
	}
	
	public int getWeight(int param) {
		return weights[param];
	}
	
	public void setWeight(int param, int value) {
		weights[param] = value;
	}
	
	public int getLevel(int param) {
		return levels[param];
	}
	
	public void setLevel(int param, int value) {
		levels[param] = value;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public int getAgePercents() {
		if (familyAge != 0)
			return (int) ((double) age / (double) familyAge * 100); 
		else
			return 0;
	}

	public boolean isOld() {
		if (getAgePercents() >= OLD_PERCENT) 
			return true;
		else
			return false;
	}
	
	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}
	
	public int getFamilyAge() {
		return familyAge;
	}
	
	public void startTimer() {
		timer = new Timer();
		timer.schedule(new TimerAction(), PET_TIMETICK, PET_TIMETICK);
	}
	
	public void stopTimer() {
		if (timer != null)
			timer.cancel();
	}
	
	public boolean isTimer() {
		return (timer != null);
	}

	@Override
    public String toString() {
        return getName();
    }
}
