package families;

import superclasses.Entity;

public class Family extends Entity {
	int age;
	
	public Family(int id, String familyName, int age) {
    	super(id, familyName);
    	this.age = age;
    }
	
    public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
    public String toString() {
        return getName();
    }
}
