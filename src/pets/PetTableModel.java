package pets;

import java.util.ArrayList;

import superclasses.Entity;
import superclasses.TableModel;

class PetTableModel extends TableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] headers = {"ID", "Nick", "Family", "Owner", "Age (s)", "of", "Energy"};
    
    public PetTableModel(ArrayList<Entity> pets) {
    	super(pets, headers);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
    	Pet pet = (Pet) super.getValueAt(row, col);
    	switch (col) {
        case 0:
            return pet.getId();
        case 1:
        	String name = pet.getName();
        	if (pet.isOld()) 
        		name += " (OLD)";
            return name;
        case 2:
        	return pet.getFamilyName();
        case 3:
        	return pet.getUserName();
        case 4:
        	return pet.getAge();
        case 5:
        	return pet.getFamilyAge();
        default:
        	if (pet.getEnergy() > 0)
        		return pet.getEnergy();
        	else
        		return "DEAD";
    	}
    }
}