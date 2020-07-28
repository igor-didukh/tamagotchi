package families;

import java.util.ArrayList;

import superclasses.Entity;
import superclasses.TableModel;

class FamilyTableModel extends TableModel {
	private static final long serialVersionUID = 1L;
	private static final String[] headers = {"ID", "Family", "Max.age (s)"};
    
    public FamilyTableModel(ArrayList<Entity> families) {
    	super(families, headers);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
    	Family family = (Family) super.getValueAt(row, col);
        switch (col) {
            case 0:
                return family.getId();
            case 1:
                return family.getName();
            default:
                return family.getAge();
        }
    }
}