package superclasses.actions;

import java.util.ArrayList;

import main.Constants;
import superclasses.Entity;
import superclasses.TableModel;

public class ActionTableModel extends TableModel implements Constants {
	private static final long serialVersionUID = 1L;
	private static final String[] headers = {"ID", "Action", "Period (s)", "H", "F", "W", "N", "J"};
    
    public ActionTableModel(ArrayList<Entity> actions) {
    	super(actions, headers);
    }
    
    @Override
    public Object getValueAt(int row, int col) {
    	Action act = (Action) super.getValueAt(row, col);
        switch (col) {
            case 0:
                return act.getId();
            case 1:
            	return act.getName();
            case 2:
                return act.getPeriod();
            case 3:
            	return act.getLevel(HEALTH);
            case 4:
            	return act.getLevel(FOOD);
            case 5:
            	return act.getLevel(WAKE);
            case 6:
            	return act.getLevel(NEAT);
            default:
            	return act.getLevel(JOY);
        }
    }
}